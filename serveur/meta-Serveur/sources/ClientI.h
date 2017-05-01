#ifndef CLIENT_I_H
#define CLIENT_I_H

#include <Ice/Ice.h>
#include <IceUtil/IceUtil.h>
#include <IceStorm/IceStorm.h>
#include "../generated/MetaServeur.h"

using namespace std;
using namespace MetaServeur;

class ClientI : public ClientPublisher {
public:

    virtual void ListeMusique(const string &id, const Ice::Current &);

    virtual void jouerMusique(const string &id, const string &nomMusique, const Ice::Current &);

    virtual void ajouterClient(const string &id, const Ice::Current &);

    virtual void supprimerClient(const string &id, const Ice::Current &);

};

#endif
