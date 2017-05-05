package webservice.stream;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        List<String> separated = Arrays.asList(requete.split("."));

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

    private String findMusique(List<String> requete) {
        return "dazzle.mp3";
    }

    private String doActionOnServeur(String clientID, String action, String musique) {
        return "http://127.0.0.1:8090/" + clientID + "/" + musique;
    }
}
