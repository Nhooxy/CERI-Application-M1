#include "ServeurEsclaveI.h"

using namespace std;
using namespace MetaServeur;

void ServeurEsclaveI::ajouterMusique(const string &id, const string &nomMusique, const Ice::Current &) {
    cout << "ajouter Musique" << endl;
}

void ServeurEsclaveI::supprimerMusique(const string &id, const string &nomMusique, const Ice::Current &) {
    cout << "supprimer Musique" << endl;
}
