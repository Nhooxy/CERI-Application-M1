#ifndef COMMUNICATION_H
#define COMMUNICATION_H

#include <string>
#include <cstdlib>
#include <Ice/Ice.h>

using namespace std;

class Communication : public Ice::Application {
public:
    string url;

    virtual int run(int, char*[]);

    string searchAction(string action);

    string searchMusique(string nom);
};

#endif
