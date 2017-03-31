#include "Communication.h"
#include <string>
#include <vector>
#include <algorithm>
#include <cstdlib>
#include <iostream>
#include "../generated/Bibliotheque.h"

using namespace std;
using namespace Bibliotheque;

/**
 * comme Implémentation de explode PHP.
 *
 * @link http://php.net/manual/fr/function.explode.php
 * @param str
 * @param delimiter
 * @return
 */
vector <string> explode(string str, string delimiter) {
    vector <string> elements;
    size_t pos = 0;
    std::string token;
    while ((pos = str.find(delimiter)) != std::string::npos) {
        token = str.substr(0, pos);
        elements.push_back(token);
        str.erase(0, pos + delimiter.length());
    }

    return elements;
}


int Communication::run(int argc, char *argv[]) {
    Ice::CommunicatorPtr communicator;
    try {
        communicator = Ice::initialize(argc, argv);
        ManagementPrx object = ManagementPrx::checkedCast(communicator->stringToProxy("BibliothequeAdapter:default -p 10000"));
        object->streamOnURL("dazzle", "clientID");
        int i = 0;
        cin >> i;
        communicator->destroy();
    }
    catch (const Ice::Exception &ex1) {
        cerr << ex1 << endl;
    }
    return 0;
    /*string titre = this->searchMusique("dazzle");
    Bibliotheque::ManagementPrx manager;
    cout <<  manager->search()[0]<<endl;
    //manager.streamOnURL(titre, "clientID");*/
}

/**
 * Permet de déterminer l'action à réaliser et retourne le cas échant le serveur de stream.
 *
 * @param action
 * @return
 */
string Communication::searchAction(string action) {
    vector <string> lecture = {"lecture", "play", "jouer"};
    vector <string> pause = {"pause", "arreter", "stop"};
    vector <string> stringTab = explode(action, " ");

    for (vector<string>::iterator it = stringTab.begin(); it != stringTab.end(); ++it) {
        if (find(lecture.begin(), lecture.end(), *it) != lecture.end()) {
            //todo wip
            char *cstr = new char[action.length() + 1];
            strcpy(cstr, action.c_str());
            char* aze[] = {cstr};
            this->run(1, aze);
            // todo ICE appeler streamOnURL de titre et renvoie l'url client que lon return ici
            cout << "jouer" << endl;
        }
        if (find(pause.begin(), pause.end(), *it) != pause.end()) {
            // todo aucune idée.... (comment faire une pause ? )
            cout << "pause" << endl;
        }
    }

    return "not Implemented";
}

string Communication::searchMusique(string nom) {
    return nom;
}


/*
 * Méthode main ud serveur, permet de lancer la communication.
int main(int argc, char *argv[]) {
    int status = 0;
    Ice::CommunicatorPtr ic;
    ic = Ice::initialize(argc, argv);
    Ice::ObjectAdapterPtr adapter = ic->createObjectAdapterWithEndpoints("BibliothequeAdapter", "default -p 10000");
    Ice::ObjectPtr object = new BibliothequeI;
    adapter->add(object, ic->stringToIdentity("SimpleBibliotheque"));
    adapter->activate();
    ic->waitForShutdown();
    if (ic) {
        ic->destroy();
    }

    return status;
}
 */
