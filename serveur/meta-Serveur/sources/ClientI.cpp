#include "ClientI.h"
#include "../generated/ServeurEsclave.h"

using namespace std;
using namespace MetaServeur;
using namespace ServeurEsclave;

void ClientI::ListeMusique(const string &id, const Ice::Current &) {
    cout << "Liste Musique" << endl;
}

void ClientI::jouerMusique(const string &id, const string &nomMusique, const Ice::Current &) {
    string topicName = "ServeurEsclaveMetaServeur";
    Ice::CommunicatorPtr communicator;

    communicator = Ice::initialize();
    Ice::ObjectPrx obj = communicator->stringToProxy("IceStormApp/TopicManager:default -h localhost -p 10000");
    IceStorm::TopicManagerPrx manager = IceStorm::TopicManagerPrx::checkedCast(obj);

    IceStorm::TopicPrx topic;
    try {
        topic = manager->retrieve(topicName);
    } catch (const IceStorm::NoSuchTopic &) {
        try {
            topic = manager->create(topicName);
        } catch (const IceStorm::TopicExists &) {
            return;
        }
    }

    Ice::ObjectPrx publisher = topic->getPublisher();
    MetaServeurPublisherPrx publisherPrx = MetaServeurPublisherPrx::uncheckedCast(publisher);

    try {
        publisherPrx->jouerMusique(id, nomMusique);
    } catch (const Ice::CommunicatorDestroyedException &) {}

    return;
}

void ClientI::ajouterClient(const string &id, const Ice::Current &) {
    cout << "ajouterClient" << endl;
}

void ClientI::supprimerClient(const string &id, const Ice::Current &) {
    cout << "supprimerClient" << endl;
}
