package client;

import java.io.*;
import java.net.*;

import javazoom.jl.player.advanced.*;

/**
 * class pour lire la musique utilisant la lib jl1.0.1.jar
 */
public class Sound {

    /**
     *
     */
    private boolean isPlaying = false;

    /**
     *
     */
    private AdvancedPlayer player = null;

    /**
     * @param path
     * @throws Exception
     */
    public Sound(String path) throws Exception {
        InputStream in = new URL(path).openStream();
        player = new AdvancedPlayer(in);
    }

    /**
     * @throws Exception
     */
    public void play() throws Exception {
        if (player != null) {
            isPlaying = true;
            player.play();
        }
    }

    /**
     * @throws Exception
     */
    public void stop() throws Exception {
        if (player != null) {
            isPlaying = false;
            player.stop();
        }
    }

    /**
     * @return
     */
    public boolean isPlaying() {
        return isPlaying;
    }
}
