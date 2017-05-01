#ifndef SERVEUR_ESCLAVE_H
#define SERVEUR_ESCLAVE_H

#include <Ice/Ice.h>
#include <IceUtil/IceUtil.h>
#include <IceStorm/IceStorm.h>
#include "MetaServeurI.h"
#include "../generated/ServeurEsclave.h"

using namespace std;

class Publisher : public Ice::Application {
public:
    virtual int run(int, char *[]);
};

class Subscriber : public Ice::Application {
public:
    virtual int run(int, char *[]);
};

#endif
