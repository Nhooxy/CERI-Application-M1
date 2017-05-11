module MetaServeur {

    interface ClientWS {
        string jouerMusique(string id, string nomMusique);
        bool serchWithName(string musique);
        void stopStreaming();
    };
};
