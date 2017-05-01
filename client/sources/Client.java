import Ice.*;
import IceStorm.*;

import java.util.*;

/**
 * Classe Client.
 */
public class Client {
    /**
     * @param args
     */
    public static void main(String[] args) {
        String idClient = java.util.UUID.randomUUID().toString();

        Thread t = new Thread(() -> {
            Subscriber sub = new Subscriber();
            sub.setId(idClient);
            sub.main(
                    "Subscriber",
                    args,
                    "/config/config.sub"
            );
        });
        t.start();

        try {
            Thread.currentThread();
            Thread.sleep(1000);
        } catch (java.lang.InterruptedException e) {
            e.printStackTrace();
        }

        Publisher pub = new Publisher();
        pub.setId(idClient);
        int status = pub.publish();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
