#include "Communication.h"
#include <string>
#include <vector>
#include <algorithm>

using namespace std;

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
        /*if (find(lecture.begin(), lecture.end(), it) != lecture.end()) {
            cout << "jouer" << endl;
        }
        if (find(pause.begin(), pause.end(), it) != pause.end()) {
            cout << "pause" << endl;
        }*/
    }

    return "not Implemented";
}

string Communication::searchMusique(string nom) {
    return "not Implemented";
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
