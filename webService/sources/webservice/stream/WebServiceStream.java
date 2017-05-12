package webservice.stream;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        for (String wordAction : separated) {
            Pattern patternPlay = Pattern.compile(Commande.PLAY.getRegex(), Pattern.CASE_INSENSITIVE);
            Matcher matcherPlay = patternPlay.matcher(wordAction);
            Pattern patternStop = Pattern.compile(Commande.STOP.getRegex(), Pattern.CASE_INSENSITIVE);
            Matcher matcherStop = patternStop.matcher(wordAction);
            if (matcherPlay.matches()) {
                return doActionOnServeur(separated.get(0), findMusique(separated));
            }
            if (matcherStop.matches()) {
                doActionStop();
                return "stop";
            }
        }

        return null;
    }

    /**
     * Doit trouver la musique au sein d'une bdd ? fichier ? puis renvoyer le titre.
     *
     * @param requete list de string
     * @return string titre de la chanson.
     */
    private String findMusique(List<String> requete) {
        Ice.Communicator ic = null;
        String musique = "california";
        try {
            String[] args = new String[]{};
            ic = Ice.Util.initialize(args);
            Ice.ObjectPrx base = ic.stringToProxy("SimpleBibliotheque:default -p 10000");
            ClientWSPrx manager = ClientWSPrxHelper.checkedCast(base);
            for (String aRequete : requete) {
                if (manager.serchWithName(aRequete)) {
                    musique = aRequete;
                }
            }
        } catch (Ice.Exception e) {
            e.printStackTrace();
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
        return musique;
    }

    /**
     * Appel avec ICE sur le meta serveur afin de lancer le stream, renvoie l'url du stream.
     *
     * @param clientID l'id du client.
     * @param musique  la musique a lire.
     * @return url string.
     */
    private String doActionOnServeur(String clientID, String musique) {
        String ip = null;
        Ice.Communicator ic = null;
        try {
            String[] args = new String[]{};
            ic = Ice.Util.initialize(args);
            Ice.ObjectPrx base = ic.stringToProxy("SimpleBibliotheque:default -p 10000");
            ClientWSPrx manager = ClientWSPrxHelper.checkedCast(base);
            ip = manager.jouerMusique(clientID, musique);
        } catch (Ice.Exception e) {
            e.printStackTrace();
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }

        return null != ip ? "http://" + ip + ":8090/" + clientID + "/" + musique + ".mp3" : null;
    }

    /**
     * Appel avec ICE sur le meta serveur afin de stopper le stream.
     */
    private void doActionStop() {
        Ice.Communicator ic = null;
        try {
            String[] args = new String[]{};
            ic = Ice.Util.initialize(args);
            Ice.ObjectPrx base = ic.stringToProxy("SimpleBibliotheque:default -p 10000");
            ClientWSPrx manager = ClientWSPrxHelper.checkedCast(base);
            manager.stopStreaming();
        } catch (Ice.Exception e) {
            e.printStackTrace();
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }
}
