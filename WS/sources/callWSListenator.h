#ifndef CALLWSLISTENATOR_H
#define CALLWSLISTENATOR_H

#include <stdio.h>
#include <stdlib.h>
#include <iostream>
#include <unistd.h>
#include "../generated/soapStub.h"

using namespace std;

char *HoroDate();

static char g_strBuffHD[64];
extern int g_iPort;

extern char *HoroDate();

#endif
