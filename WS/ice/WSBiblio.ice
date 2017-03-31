module WSBiblio {
    sequence<string> sstring;
    interface Management {
        sstring search();
        sstring streamOnURLFor(string musique, string clientID);
    };
};
