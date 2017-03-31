#ifndef MY_SERVEUR_H
#define MY_SERVEUR_H

#include <Ice/Ice.h>
#include "../generated/Bibliotheque.h"
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

using namespace std;
using namespace Bibliotheque;

class BibliothequeI : public Management {

public:

    static const string MUSIC_DIR;

    /**
     * Permet de recevoir un fichier du client (copie le fichier dans le repertoire courant du serveur).
     */
    void copyFile(const string &musique, Ice::Int offset, const sb &bytes, Ice::Int size, const Ice::Current &);

    /**
     * Permet de supprimer le fichier de musique voulu.
     */
    void removeFile(const string &musique, const Ice::Current &);

    /**
     * Permet de rechercher une musique.
     *
     * @return
     */
    sstring search(const Ice::Current &);

    /**
     * Methode permettant de stream un flux audio sur l'url local avec le port 8090
     * (127.0.0.1:8090/nomdufichier.mp3)
     */
    void streamOnURL(const string &musique, const string &client, const Ice::Current &);
};

#endif
