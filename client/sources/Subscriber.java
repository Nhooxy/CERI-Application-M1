import Ice.*;
import IceStorm.*;

import java.util.*;

/**
 * Subsciber
 */
public class Subscriber extends Ice.Application {
    private String idClient;

    /**
     * setter id.
     *
     * @param id
     */
    public void setId(String id) {
        this.idClient = id;
    }

    /**
     * run.
     *
     * @param args
     * @return
     */
    @Override
    public int
    run(String[] args) {
        args = communicator().getProperties().parseCommandLineOptions("Client", args);
        String topicName = this.idClient;
        String id = null;
        IceStorm.TopicManagerPrx manager = IceStorm.TopicManagerPrxHelper.checkedCast(
                communicator().propertyToProxy("TopicManager.Proxy"));

        IceStorm.TopicPrx topic;
        try {
            topic = manager.retrieve(topicName);
        } catch (IceStorm.NoSuchTopic e) {
            try {
                topic = manager.create(topicName);
            } catch (IceStorm.TopicExists ex) {
                System.err.println(appName() + ": temporary failure, try again.");
                return 20;
            }
        }
        Ice.ObjectAdapter adapter = communicator().createObjectAdapter("Client.Subscriber");
        Ice.Identity subId = new Ice.Identity(id, "");
        subId.name = this.idClient;
        Ice.ObjectPrx subscriber = adapter.add(new ServeurEsclaveI(), subId);
        adapter.activate();
        Map<String, String> qos = new HashMap<String, String>();
        try {
            topic.subscribeAndGetPublisher(qos, subscriber);
        } catch (IceStorm.AlreadySubscribed e) {
            e.printStackTrace();
            return 14;
        } catch (IceStorm.InvalidSubscriber e) {
            e.printStackTrace();
            return 12;
        } catch (IceStorm.BadQoS e) {
            e.printStackTrace();
            e.printStackTrace();
            return 13;
        }
        shutdownOnInterrupt();
        communicator().waitForShutdown();
        topic.unsubscribe(subscriber);

        return 0;
    }
}
