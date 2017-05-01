import Ice.*;
import IceStorm.*;

import java.util.*;

public class Publisher {
    private String idClient;

    public void setId(String id) {
        this.idClient = id;
    }

    /**
     * @return
     */
    public int publish() {
        String topicName = "MetaServeurClient";
        Ice.Communicator communicator = null;
        try {
            communicator = Ice.Util.initialize();
        } catch (Ice.Exception e) {
            e.printStackTrace();
        }
        ObjectPrx base = null;
        if (null != communicator) {
            base = communicator.stringToProxy("IceStormApp/TopicManager:default -h localhost -p 10000");
        }
        IceStorm.TopicManagerPrx manager = IceStorm.TopicManagerPrxHelper.checkedCast(base);
        if (null == manager) {
            System.err.println("invalid proxy");
            return 50;
        }

        IceStorm.TopicPrx topic;
        try {
            topic = manager.retrieve(topicName);
        } catch (IceStorm.NoSuchTopic e) {
            try {
                topic = manager.create(topicName);
            } catch (IceStorm.TopicExists ex) {
                return 31;
            }
        }
        Ice.ObjectPrx publisher = topic.getPublisher();
        MetaServeur.ClientPublisherPrx clientPublisher = MetaServeur.ClientPublisherPrxHelper.uncheckedCast(publisher);
        System.out.println("publishing tick events. Press ^C to terminate the application.");
        try {
            while (true) {
                clientPublisher.jouerMusique(this.idClient, "dazzle");

                try {
                    Thread.currentThread();
                    Thread.sleep(1000);
                } catch (java.lang.InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (Ice.CommunicatorDestroyedException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
