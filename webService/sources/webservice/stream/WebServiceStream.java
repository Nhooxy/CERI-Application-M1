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

        return "bonjour " + requete;
    }
}
