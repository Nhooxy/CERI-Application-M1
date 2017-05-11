#include "MetaServeurI.h"

using namespace std;
using namespace MetaServeur;

int port = 8090;

bool MetaServeurI::serchWithName(const string &musique, const Ice::Current &) {
    struct dirent *lecture;
    cout << musique << endl;
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
    cout << "pas trouver musique" << endl;
    return false;
}

bool serchWithNameLocal(string musique) {
    struct dirent *lecture;
    cout << musique << endl;
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
    cout << "pas trouver musique" << endl;
    return false;
}

string MetaServeurI::jouerMusique(const string &id, const string &nomMusique, const Ice::Current &) {
    media_name = nomMusique;
    if (serchWithNameLocal(media_name)) {
        libvlc_instance_t *vlc;
        const char *media_musique = media_name.c_str();
        string tmp =
        "#transcode{acodec=mp3,ab=128,channels=2,samplerate=44100}:http{dst=:8090/"+id+"/"+media_name+"}";
        const char *sout = tmp.c_str();
        string musiqueM = "./bibliotheque/" + media_name;
        const char *cheminFichier = musiqueM.c_str();

        vlc = libvlc_new(0, NULL);
        libvlc_vlm_add_broadcast(vlc, media_musique, cheminFichier, sout, 0, NULL, true, false);
        libvlc_vlm_play_media(vlc, media_musique);

        return ip;
    }

    return NULL;
}

void MetaServeurI::stopStreaming(const Ice::Current &) {
    libvlc_vlm_stop_media(vlc, media_name.c_str());
    libvlc_release(vlc);
    cout << "Stop streaming" << media_name << endl;
}

/**
 * Méthode main ud serveur, permet de lancer la communication.
 */
int main(int argc, char *argv[])
{
    cout << "Entrez l'ip du serveur." << endl;
    cin >> ip;

    int status = 0;
    Ice::CommunicatorPtr ic;
    ic = Ice::initialize(argc, argv);
    Ice::ObjectAdapterPtr adapter =
     ic->createObjectAdapterWithEndpoints("MetaServeurAdapter", "default -p 10000");
    Ice::ObjectPtr object = new MetaServeurI;
    adapter->add(object, ic->stringToIdentity("SimpleBibliotheque"));
    adapter->activate();
    ic->waitForShutdown();
    if (ic) {
        ic->destroy();
    }
    return status;
}
