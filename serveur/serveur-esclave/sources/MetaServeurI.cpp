#include "MetaServeurI.h"

using namespace std;
using namespace MetaServeur;

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

string MetaServeurI::jouerMusique(const string &id, const string &nomMusique, const Ice::Current &) {
    string musiqueString = nomMusique;
    if (serchWithName(musiqueString)) {
        libvlc_instance_t *vlc;
        const char *url = musiqueString.c_str();
        const char *sout =
        "#transcode{acodec=mp3,ab=128,channels=2,samplerate=44100}:http{dst=:8090/file.mp3}";
        string musiqueM = "./bibliotheque/" + musiqueString;
        const char *media_musique = musiqueString.c_str();
        url = musiqueM.c_str();

        vlc = libvlc_new(0, NULL);
        libvlc_vlm_add_broadcast(vlc, media_musique, url, sout, 0, NULL, true, false);
        libvlc_vlm_play_media(vlc, media_musique);

        return ip;
    }

    return "Pas de musique";
}

/**
 * Méthode main ud serveur, permet de lancer la communication.
 */
int main(int argc, char *argv[])
{
    cout << "entrez votre ip" << endl;
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
