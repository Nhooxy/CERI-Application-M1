#include <cstdio>
#include <cstdlib>
#include <iostream>
#include <csignal>
#include "../generated/IWSPlayerbinding.nsmap"
#include "callWSListenator.h"

using namespace std;

bool g_iStopNotCalled;
string g_strVersion = "WSListenator 1.0.0";
int g_iPort;

void *callWSListenator(void *p_pSsoap) {
    pthread_detach(pthread_self());
    soap_serve((struct soap *) p_pSsoap);
    soap_destroy((struct soap *) p_pSsoap);
    soap_end((struct soap *) p_pSsoap);
    soap_free((struct soap *) p_pSsoap);

    return NULL;
}

int main(int argc, char *argv[]) {
    int m;
    int s;
    pthread_t tid;
    struct soap l_WSListenatorSoap;
    struct soap *l_WSListenatorSoapCopy;
    string l_strConfFile;
    int i = 0;

    if (argc < 2) {
        cout << "Pas de fichier de configuration détecter, configuration par défault établi." << endl;
        g_iPort = 7010;

    } else {
        l_strConfFile = argv[1];
        //TODO: Faut lire le fichier de conf
        g_iPort = 7010;

    }

    cout << HoroDate() << " Demarrage de " << g_strVersion.data() << endl;
    soap_init(&l_WSListenatorSoap);
    m = soap_bind(&l_WSListenatorSoap, NULL, g_iPort, 300);

    if (m < 0) {
        soap_print_fault(&l_WSListenatorSoap, stderr);
        cerr << HoroDate() << " Erreur gSoap, le programme est arrété." << endl;

        return -1;
    }

    cout << HoroDate() << " Connection réussi ! master socket : " << m << endl;
    g_iStopNotCalled = true;

    while (g_iStopNotCalled) {
        s = soap_accept(&l_WSListenatorSoap);
        i++;
        cout << HoroDate() << " Connection réussi ! socket esclave : " << s << endl;
        if (s < 0) {
            soap_print_fault(&l_WSListenatorSoap, stderr);
            break;
        }
        cout << HoroDate() << " Connection accepté" << endl;
        try {
            l_WSListenatorSoapCopy = soap_copy(&l_WSListenatorSoap);
            if (!l_WSListenatorSoapCopy) {
                break;
            }
            pthread_create(&tid, NULL, &callWSListenator, (void *) l_WSListenatorSoapCopy);
        }
        catch (exception &e) {
            cerr << HoroDate() << "Exeption sur pthread_create..." << endl;
        }
    }
    soap_done(&l_WSListenatorSoap);

    return 0;
}
