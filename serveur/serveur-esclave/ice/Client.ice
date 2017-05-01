module Client {

    sequence<string> seqString;

    interface MetaServeurPublisher {
        void ListeMusique(seqString liste);
    };

    interface ServeurEsclavePublisher {
        void jouerMusique(string ip);
    };
};
