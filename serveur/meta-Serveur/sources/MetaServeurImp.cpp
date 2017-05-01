#include "MetaServeurImp.h"

using namespace std;
using namespace MetaServeur;

int main(int argc, char *argv[]) {
    Subscriber clientEtServeurEsclave;
    clientEtServeurEsclave.main(argc, argv, "config/config.sub");
}

int
Subscriber::run(int argc, char *argv[]) {
    string topicNameClient = "MetaServeurClient";
    string topicNameSE = "MetaServeurServeurEsclave";
    string id;
    string retryCount;
    IceStorm::TopicManagerPrx manager = IceStorm::TopicManagerPrx::checkedCast(
            communicator()->propertyToProxy("TopicManager.Proxy"));
    IceStorm::TopicPrx topicClient;
    IceStorm::TopicPrx topicSE;
    try {
        topicSE = manager->retrieve(topicNameSE);
    }
    catch (const IceStorm::NoSuchTopic &) {
        try {
            topicSE = manager->create(topicNameSE);
        }
        catch (const IceStorm::TopicExists &) {
            cerr << appName() << ": temporary failure. try again." << endl;
            return EXIT_FAILURE;
        }
    }

    try {
        topicClient = manager->retrieve(topicNameClient);
    }
    catch (const IceStorm::NoSuchTopic &) {
        try {
            topicClient = manager->create(topicNameClient);
        }
        catch (const IceStorm::TopicExists &) {
            cerr << appName() << ": temporary failure. try again." << endl;
            return EXIT_FAILURE;
        }
    }

    Ice::ObjectAdapterPtr adapter = communicator()->createObjectAdapter("MetaServeur.Subscriber");

    Ice::Identity subIdServeurEsclave;
    Ice::Identity subIdClient;

    subIdServeurEsclave.name = "METASERVEUR_PP_M1ILSEN_ALT_SERVEUR_ESCLAVE";
    subIdClient.name = "METASERVEUR_PP_M1ILSEN_ALT_CLIENT";

    Ice::ObjectPrx subscriberSEI = adapter->add(new ServeurEsclaveI, subIdServeurEsclave);
    Ice::ObjectPrx subscriberCI = adapter->add(new ClientI, subIdClient);

    adapter->activate();
    IceStorm::QoS qos;

    qos["retryCount"] = "-1";

    try {
        topicSE->subscribeAndGetPublisher(qos, subscriberSEI);
        topicClient->subscribeAndGetPublisher(qos, subscriberCI);
    }
    catch (const IceStorm::AlreadySubscribed &) {
        if (id.empty()) {
            throw;
        }
        cout << "reactivating persistent subscriber" << endl;
    }
    shutdownOnInterrupt();
    communicator()->waitForShutdown();

    topicSE->unsubscribe(subscriberSEI);
    topicClient->unsubscribe(subscriberCI);

    return EXIT_SUCCESS;
}
