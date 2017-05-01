#ifndef META_SERVEUR_H
#define META_SERVEUR_H

#include <Ice/Ice.h>
#include <IceUtil/IceUtil.h>
#include <IceStorm/IceStorm.h>
#include "../generated/MetaServeur.h"
#include "ClientI.h"
#include "ServeurEsclaveI.h"

using namespace std;
using namespace MetaServeur;

class Publisher : public Ice::Application {
public:
    virtual int run(int, char *[]);
};

class Subscriber : public Ice::Application {
public:
    virtual int run(int, char *[]);
};

#endif
