import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.IOException;
import java.util.UUID;

import Ice.*;

public class Client {

    /**
     * Affiche tous lesfichier present sur le serveur
     *
     * @param manager
     */
    public static void afficherFichier(Bibliotheque.ManagementPrx manager) {
        String listFile[] = manager.search();
        int i = 0;
        while (i < listFile.length) {
            System.out.println();
            System.out.println("Les fichiers disponible sont : ");
            System.out.println(listFile[i]);
            System.out.println();
            i++;
        }
        if (0 == i) {
            System.out.println("Pas de musique");
        }
    }

    /**
     * permet d'envoyer le fichier sur le serveur.
     *
     * @param manager
     */
    public static void copyFile(Bibliotheque.ManagementPrx manager) {
        String chemin;
        String musique;
        Scanner sc = new Scanner(System.in);
        System.out.println("Entrez le chemin aboslue du fichier avec son nom : ");
        chemin = sc.nextLine();
        System.out.println("Entrez le nom du fichier (avec le .mp3) : ");
        musique = sc.nextLine();
        chemin = chemin + musique;
        try {
            File file = new File(chemin);
            FileInputStream is = new FileInputStream(file);
            byte[] chunk = new byte[1024 * 250];
            int chunkLen = 0;
            int offset = 0;
            while ((chunkLen = is.read(chunk)) != -1) {
                manager.copyFile(musique, offset, chunk, chunkLen);
                offset += chunkLen;
            }
        } catch (FileNotFoundException fnfE) {
            fnfE.printStackTrace();
        } catch (IOException ioE) {
            ioE.printStackTrace();
        }
    }

    /**
     * permet de supprier un fichier sur le serveur
     *
     * @param manager
     */
    public static void removeFile(Bibliotheque.ManagementPrx manager) {
        String musique;
        Scanner sc = new Scanner(System.in);
        System.out.println("Entrez le nom du fichier (avec le .mp3) : ");
        musique = sc.nextLine();
        manager.removeFile(musique);
    }

    /**
     * permet de lire le fichier en streaing
     *
     * @param manager
     */
    public static void playFile(Bibliotheque.ManagementPrx manager) {
        String musique;
        Scanner sc = new Scanner(System.in);
        System.out.println("Entrez le nom du fichier : ");
        musique = sc.nextLine();
        musique = musique + ".mp3";
        String client = UUID.randomUUID() + "/";
        manager.streamOnURL(musique, client);

        try {
            System.out.println("Lecture de la musique en stream");
            System.out.println("http://127.0.0.1:8090/".concat(client).concat(musique));
            Sound test = new Sound("http://127.0.0.1:8090/".concat(client).concat(musique));
            test.play();
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Main du client, permet d'envoyer un fichier sur le servuer
     * Et de lire le flux stream
     *
     * @param args
     */
    public static void main(String[] args) {
        int status = 0;
        Ice.Communicator ic = null;
        try {
            ic = Ice.Util.initialize(args);
            Ice.ObjectPrx base = ic.stringToProxy("SimpleBibliotheque:default -p 10000");
            Bibliotheque.ManagementPrx manager = Bibliotheque.ManagementPrxHelper.checkedCast(base);
            if (null == manager) {
                throw new Error("Invalid proxy");
            }
            String entree = "0";
            Scanner sc = new Scanner(System.in);
            while (!entree.equals("5")) {
                System.out.println("Selectionnez une action");
                System.out.println("1. Rechercher toutes la musique");
                System.out.println("2. Ajouter un fichier");
                System.out.println("3. Supprimer un fichier");
                System.out.println("4. Lire un fichier");
                System.out.println("5. Quitter");
                entree = sc.nextLine();
                switch (entree) {
                    case "1":
                        afficherFichier(manager);
                        break;
                    case "2":
                        copyFile(manager);
                        break;
                    case "3":
                        removeFile(manager);
                        break;
                    case "4":
                        playFile(manager);
                        break;
                    case "5":
                        System.out.println("fin");
                        break;
                    default:
                        System.out.println("Mauvaise touche");
                        break;
                }
            }
        } catch (Ice.LocalException e) {
            e.printStackTrace();
            status = 1;
        } catch (Ice.Exception e) {
            System.err.println(e.getMessage());
            status = 1;
        }
        if (null != ic) {
            try {
                ic.destroy();
            } catch (Ice.Exception e) {
                System.err.println(e.getMessage());
                status = 1;
            }
        }
        System.exit(status);
    }
}
