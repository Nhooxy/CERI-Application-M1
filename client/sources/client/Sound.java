package client;

import java.io.*;
import java.net.*;

import javazoom.jl.player.advanced.*;

/**
 * class pour lire la musique utilisant la lib jl1.0.1.jar
 */
public class Sound {
    private boolean isPlaying = false;
    private AdvancedPlayer player = null;

    public Sound(String path) throws Exception {
        InputStream in = new URL(path).openStream();
        player = new AdvancedPlayer(in);
    }

    public void play() throws Exception {
        if (player != null) {
            isPlaying = true;
            player.play();
        }
    }

    public void stop() throws Exception {
        if (player != null) {
            isPlaying = false;
            player.stop();
        }
    }

    public boolean isPlaying() {
        return isPlaying;
    }
}
