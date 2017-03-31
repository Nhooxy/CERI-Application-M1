#include <iostream>
#include <string>
#include <fstream>
#include <vlc/libvlc.h>
#include <vlc/libvlc_vlm.h>
#include <unistd.h>
#include <vector>
#include <stdio.h>
#include <dirent.h>
#include <sys/types.h>

using namespace std;

/**
 * Méthode main ud serveur, permet de lancer la communication.
 */
int main(int argc, char *argv[]) {
	const string &musique = "dazzle.mp3";
        libvlc_instance_t *vlc;
        const char *url;
        string tmp = "#transcode{acodec=mp3,ab=128,channels=2,samplerate=44100}:http{dst=:8090/dazzle.mp3}";
        const char *sout = tmp.c_str();
        const char *media_name = musique.c_str();

        vlc = libvlc_new(0, NULL);
        libvlc_vlm_add_broadcast(vlc, media_name, musique.c_str(), sout, 0, NULL, true, false);
        libvlc_vlm_play_media(vlc, media_name);

	while(true);
}

