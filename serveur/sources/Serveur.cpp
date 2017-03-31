#include "BibliothequeI.h"

using namespace std;
using namespace Bibliotheque;

/**
 * Méthode main ud serveur, permet de lancer la communication.
 */
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
