import Ice.*;
import IceStorm.*;
import Client.*;

import java.util.Scanner;

/**
 * Serveur Esclave Interface.
 */
public class ServeurEsclaveI extends _ServeurEsclavePublisherDisp {
    /**
     * permet de lire le fichier en streaing
     */
    @Override
    public void
    jouerMusique(String ip, Ice.Current current) {
        try {
            Sound test = new Sound(ip);
            test.play();
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }
}
