#ifndef SERVEUR_ESCLAVE_I_H
#define SERVEUR_ESCLAVE_I_H

#include <Ice/Ice.h>
#include <IceUtil/IceUtil.h>
#include <IceStorm/IceStorm.h>
#include "../generated/MetaServeur.h"

using namespace std;
using namespace MetaServeur;

class ServeurEsclaveI : public ServeurEsclavePublisher {
public:

    virtual void ajouterMusique(const string &id, const string &nomMusique, const Ice::Current &);

    virtual void supprimerMusique(const string &id, const string &nomMusique, const Ice::Current &);
};

#endif
