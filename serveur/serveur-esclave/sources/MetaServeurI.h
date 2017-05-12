#ifndef META_SERVEUR_I_H
#define META_SERVEUR_I_H

#include <Ice/Ice.h>
#include <IceUtil/IceUtil.h>
#include <IceStorm/IceStorm.h>
#include <memory>
#include <fstream>
#include <vector>
#include <vlc/libvlc.h>
#include <vlc/libvlc_vlm.h>
#include <stdio.h>
#include <sys/types.h>
#include <dirent.h>
#include <pthread.h>
#include <string.h>
#include "../generated/MetaServeur.h"

using namespace std;
using namespace MetaServeur;

/**
 * mon ip a entrer
 */
string ip;

/**
 * Classe MetaServeur
 */
class MetaServeurI : public ClientWS {
public:
    string media_name;
    libvlc_instance_t *vlc;

    /**
     * Permet de streamer la musique.
     * @param id
     * @param nomMusique
     * @return
     */
    virtual string jouerMusique(const string &id, const string &nomMusique, const Ice::Current &);

    /**
     * Permet de chercher le nom du fichier musical via ICE.
     * @param musique
     * @return
     */
    virtual bool serchWithName(const string &musique, const Ice::Current &);

    /**
     * Permet d'arreter le streaming.
     */
    virtual void stopStreaming(const Ice::Current &);
};

#endif