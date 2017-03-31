#include <Ice/Ice.h>
#include "../generated/Bibliotheque.h"
#include "BibliothequeI.h"
#include <iostream>
#include <string>
#include <fstream>
#include <vlc/libvlc.h>
#include <vlc/libvlc_vlm.h>
#include <unistd.h>
#include <vector>
#include <stdio.h>
#include <dirent.h>
#include <sys/types.h>
#include <thread>

using namespace std;
using namespace Bibliotheque;

const string BibliothequeI::MUSIC_DIR = "music";

/**
 * Permet de recevoir un fichier du client (copie le fichier dans le repertoire courant du serveur).
 */
void
BibliothequeI::copyFile(const string &musique, Ice::Int offset, const sb &bytes, Ice::Int size, const Ice::Current &) {
    cout << "network connection" << endl;
    ofstream myfile((MUSIC_DIR + "/" + musique).c_str(), ios::out | ios::app | ios::binary);
    myfile.seekp(offset, ios::beg);
    myfile.write((char *) (&bytes[0]), size);
    myfile.close();
}

/**
 * Permet de supprimer le fichier de musique voulu.
 */
void BibliothequeI::removeFile(const string &musique, const Ice::Current &) {
    system(("rm " + MUSIC_DIR + "/" + musique).c_str());
}

/**
 * Permet de rechercher une musique.
 *
 * @return
 */
sstring BibliothequeI::search(const Ice::Current &) {
    struct dirent *lecture;
    DIR *rep;
    rep = opendir(MUSIC_DIR.c_str());
    vector <string> list;
    while (lecture = readdir(rep)) {
        string tmp = lecture->d_name;
        if ("." != tmp && ".." != tmp) {
            tmp = tmp.erase(tmp.rfind('.'));
            list.push_back(tmp);
        }
    }
    closedir(rep);

    return list;
}

/**
 * Methode permettant de stream un flux audio sur l'url local avec le port 8090
 * (127.0.0.1:8090/nomdufichier.mp3)
 */
string BibliothequeI::streamOnURL(const string &musique, const string &client, const Ice::Current &) {
    libvlc_instance_t *vlc;
    const char *url;
    string tmp =
            "#transcode{acodec=mp3,ab=128,channels=2,samplerate=44100}:http{dst=:8090/" + client + musique + "}";
    const char *sout = tmp.c_str();
    const char *media_name = (musique).c_str();

    vlc = libvlc_new(0, NULL);
    libvlc_vlm_add_broadcast(
            vlc,
            media_name,
            (MUSIC_DIR + "/" + musique).c_str(),
            sout,
            0,
            NULL,
            true,
            false
    );
    thread t1(libvlc_vlm_play_media, vlc, media_name); // marche pas sans le t join mais c'etait l'interrer ... poursuivre 
    //libvlc_vlm_play_media(vlc, media_name);
    t1.join();
    cout << "127.0.0.1:8090/" + client + musique << endl;

    return "127.0.0.1:8090/" + client + musique;
}
