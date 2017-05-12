package webservice.stream;

/**
 * Permet d'enumerer en regex tous les pattern que je vais utiliser pour play / pause / stop.
 */
public enum Commande {

    PAUSE("pause", ".*pau.*|.*pause"),
    STOP("arret", ".*stop|.*sto.*|.*arr.t|.*arr.*"),
    PLAY("play", ".*play|.*lecture|.*lect.*|.*d.marr.*|.*d.ma.*|.*jouer|.*jou.*|.*.cout.*");

    private String regex;

    private String name;

    /**
     * Constructuer.
     *
     * @param name
     * @param regex
     */
    private Commande(String name, String regex) {
        this.regex = regex;
        this.name = name;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
