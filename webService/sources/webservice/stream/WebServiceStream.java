package webservice.stream;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Ice.*;
import MetaServeur.*;

@WebService(name = "WebServiceStream")
public class WebServiceStream implements IWebServiceStream {

    /**
     * Hello World.
     *
     * @param chaine Hello World
     * @return Hello World
     */
    public String bonjour(String chaine) {

        return "bonjour " + chaine;
    }

    /**
     * Doit prendre une string qui contient tous les element envoyer par le client
     * donc clientID , action et nom de musique eventuellement.
     * Doit retourné l'url du serveur de stream.
     * En meme temps elle doit lancer l'appel au meta serveur (icegrid) pour streamer.
     *
     * @param requete action du l'utilisateur.
     * @return string url serveur.
     */
    public String requeteClient(String requete) {
        List<String> separated = new ArrayList<>(Arrays.asList(requete.split("\\.")));

        String clientID = separated.get(0);
        String action = findAction(separated);
        String musique = findMusique(separated);
        return doActionOnServeur(clientID, action, musique);
    }

    /**
     * Permet de trouver l'action à réaliser.
     *
     * @param requete liste des mot envoyer par le client.
     * @return string action a faire.
     */
    private String findAction(List<String> requete) {
        List<String> action = giveAction();
        for (String wordAction : requete) {
            if (action.contains(wordAction)) {

                return wordAction;
            }
        }

        return "";
    }

    /**
     * Renvoie les actions disponible par le serveur.
     *
     * @return list string.
     */
    private List<String> giveAction() {
        List<String> action = new ArrayList<>();
        action.add("ecouter");
        action.add("lire");
        action.add("entendre");
        action.add("ecouté");
        action.add("pause");
        action.add("stop");
        action.add("arreter");
        action.add("arreté");

        return action;
    }

    /**
     * Doit trouver la musique au sein d'une bdd ? fichier ? puis renvoyer le titre.
     *
     * @param requete list de string
     * @return string titre de la chanson.
     */
    private String findMusique(List<String> requete) {
        return "dazzle.mp3";
    }

    /**
     * Appel avec ICE sur le meta serveur afin de lancer le stream, renvoie l'url du stream.
     *
     * @param clientID l'id du client.
     * @param action   l'action a realiser.
     * @param musique  la musique a lire.
     * @return url string.
     */
    private String doActionOnServeur(String clientID, String action, String musique) {
        // appel
        String ip = "127.0.0.1";
        /*try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }*/

        Ice.Communicator ic = null;
        try {
            String[] args = null;
            ic = Ice.Util.initialize(args);
            Ice.ObjectPrx base = ic.stringToProxy("SimpleBibliotheque:default -p 10000");
            ClientWSPrx manager = ClientWSPrxHelper.checkedCast(base);
            ip = manager.jouerMusique(clientID, musique);

        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }

        return "http://" + ip + ":8090/" + clientID + "/" + musique;
    }
}
