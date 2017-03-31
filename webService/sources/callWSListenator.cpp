#include "callWSListenator.h"
#include "Communication.h"
#include <string.h>
#include <cstdlib>

using namespace std;

/*!
 * Execute la commande envoyé par un client.
 * \brief	Permet de récupérer la date et l'heure à un instant t de l'éxécution du programme.
 *
 * \return 	Un char* qui correspond à la date et l'heure.
 */
char *HoroDate() {
    time_t l_time;
    struct tm *timeinfo;
    memset(g_strBuffHD, 0, 64);

    try {
        time(&l_time);
        timeinfo = localtime(&l_time);
        strftime(g_strBuffHD, 64, "[%d/%m/%Y %H:%M:%S]", timeinfo);
    }
    catch (exception &e) {
        cerr << HoroDate() << " Exception sur HoroDate" << e.what() << endl;
    }

    return g_strBuffHD;
}

SOAP_FMAC5 int SOAP_FMAC6

ns2__actionProcess(struct soap *, ns1__RequestType *Request, struct ns2__actionProcessResponse &_param_1) {
    Communication com;
    _param_1.return_ = new ns1__ResponseType();
    _param_1.return_->url = com.searchAction(Request->nomCommande);

    return 0;
}
