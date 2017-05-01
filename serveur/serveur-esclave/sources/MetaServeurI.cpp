#include "MetaServeurI.h"

using namespace std;
using namespace ServeurEsclave;
using namespace Client;

void *launchStream(void *musique) {
    char *tmp = (char *) musique;
    string musiqueString = tmp;
    libvlc_instance_t *vlc;
    const char *url = musiqueString.c_str();
    const char *sout = "#transcode{acodec=mp3,ab=128,channels=2,samplerate=44100}:http{dst=:8090/file.mp3}";
    string musiqueM = "./bibliotheque/" + musiqueString;
    const char *media_musique = musiqueString.c_str();
    url = musiqueM.c_str();

    vlc = libvlc_new(0, NULL);
    libvlc_vlm_add_broadcast(vlc, media_musique, url, sout, 0, NULL, true, false);
    libvlc_vlm_play_media(vlc, media_musique);
}

bool serchWithName(string musique) {
    struct dirent *lecture;
    DIR *rep;
    rep = opendir("./bibliotheque");
    while ((lecture = readdir(rep))) {
        string tmp = lecture->d_name;
        if (tmp != musique) {
            closedir(rep);
            return true;
        }
    }
    closedir(rep);

    return false;
}

void sendMessageToClient(string id, string ip) {
    string topicName = id;
    Ice::CommunicatorPtr communicator;
    communicator = Ice::initialize();
    Ice::ObjectPrx obj = communicator->stringToProxy("IceStormApp/TopicManager:default -h localhost -p 10000");
    IceStorm::TopicManagerPrx manager = IceStorm::TopicManagerPrx::checkedCast(obj);
    IceStorm::TopicPrx topic;
    try {
        topic = manager->retrieve(topicName);
    } catch (const IceStorm::NoSuchTopic &) {
        try {
            topic = manager->create(topicName);
        } catch (const IceStorm::TopicExists &) {
            return;
        }
    }

    Ice::ObjectPrx publisher = topic->getPublisher();
    ServeurEsclavePublisherPrx publisherPrx = ServeurEsclavePublisherPrx::uncheckedCast(publisher);

    try {
        publisherPrx->jouerMusique(ip);
    } catch (const Ice::CommunicatorDestroyedException &) {}

}

void MetaServeurI::jouerMusique(const string &id, const string &nomMusique, const Ice::Current &) {
    sendMessageToClient(id, "127.0.0.1:8090/file.mp3");
}
