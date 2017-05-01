module MetaServeur {

    interface ClientPublisher {
        void ListeMusique(string id);
        void jouerMusique(string id, string nomMusique);
        void ajouterClient(string id);
        void supprimerClient(string id);
    };

    interface ServeurEsclavePublisher {
        void ajouterMusique(string id, string nomMusique);
        void supprimerMusique(string id, string nomMusique);
    };
};
