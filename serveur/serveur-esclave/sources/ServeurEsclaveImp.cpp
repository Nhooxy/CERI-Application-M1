#include "ServeurEsclaveImp.h"

using namespace std;

int main(int argc, char *argv[]) {
    Subscriber MetaServeur;
    MetaServeur.main(argc, argv, "config/config.sub");
}

int
Subscriber::run(int argc, char *argv[]) {
    string topicNameMetaServeur = "ServeurEsclaveMetaServeur";
    string id;
    string retryCount;
    IceStorm::TopicManagerPrx manager = IceStorm::TopicManagerPrx::checkedCast(
            communicator()->propertyToProxy("TopicManager.Proxy"));
    IceStorm::TopicPrx topicMetaServeur;
    try {
        topicMetaServeur = manager->retrieve(topicNameMetaServeur);
    }
    catch (const IceStorm::NoSuchTopic &) {
        try {
            topicMetaServeur = manager->create(topicNameMetaServeur);
        }
        catch (const IceStorm::TopicExists &) {
            cerr << appName() << ": temporary failure. try again." << endl;
            return EXIT_FAILURE;
        }
    }
    Ice::ObjectAdapterPtr adapter = communicator()->createObjectAdapter("ServeurEsclave.Subscriber");
    Ice::Identity subIdMetaServeur;
    subIdMetaServeur.name = IceUtil::generateUUID();
    Ice::ObjectPrx subscriberMS = adapter->add(new MetaServeurI, subIdMetaServeur);
    adapter->activate();
    IceStorm::QoS qos;
    qos["retryCount"] = "-1";

    try {
        topicMetaServeur->subscribeAndGetPublisher(qos, subscriberMS);
    }
    catch (const IceStorm::AlreadySubscribed &) {
        if (id.empty()) {
            throw;
        }
        cout << "reactivating persistent subscriber" << endl;
    }
    shutdownOnInterrupt();
    communicator()->waitForShutdown();
    topicMetaServeur->unsubscribe(subscriberMS);

    return EXIT_SUCCESS;
}
