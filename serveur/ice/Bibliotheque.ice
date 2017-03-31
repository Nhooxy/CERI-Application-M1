module Bibliotheque {
    sequence<byte> sb;
    sequence<string> sstring;
    interface Management {
        void copyFile(string musique, int offset, sb bytes, int size);
        void removeFile(string musique);
        sstring search();
        string streamOnURL(string musique, string clientID);
    };
};
