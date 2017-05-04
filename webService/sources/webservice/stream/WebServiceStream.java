package webservice.stream;

import javax.jws.WebService;

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
        String clientID = findClientID(requete);
        String action = findAction(requete);
        String musique = findMusique(requete);

        return doActionOnServeur(clientID, action, musique);
    }

    private String findClientID(String requete) {
        return "123";
    }

    private String findAction(String requete) {
        return "ecouter";
    }

    private String findMusique(String requete) {
        return "dazzle.mp3";
    }

    private String doActionOnServeur(String clientID, String action, String musique) {
        return "http://127.0.0.1:8090/" + clientID + "/" + musique;
    }
}
