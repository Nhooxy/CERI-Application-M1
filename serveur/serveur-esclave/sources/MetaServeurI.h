#ifndef META_SERVEUR_I_H
#define META_SERVEUR_I_H

#include <Ice/Ice.h>
#include <IceUtil/IceUtil.h>
#include <IceStorm/IceStorm.h>
#include "../generated/ServeurEsclave.h"
#include "../generated/Client.h"
#include <memory>
#include <fstream>
#include <vector>
#include <vlc/libvlc.h>
#include <vlc/libvlc_vlm.h>
#include <stdio.h>
#include <sys/types.h>
#include <dirent.h>
#include <pthread.h>

using namespace std;
using namespace ServeurEsclave;

class MetaServeurI : public MetaServeurPublisher {
public:
    virtual void jouerMusique(const string &id, const string &nomMusique, const Ice::Current &);
};

#endif