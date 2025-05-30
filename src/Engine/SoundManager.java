package Engine;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundManager {
    private Clip clip;

    /**
     * Ładuje plik WAV z klasypath (resources).
     * @param resourcePath ścieżka względna w resources, np. "/sounds/background.wav"
     */
    public void load(String resourcePath) {
        try {
            URL url = getClass().getResource(resourcePath);
            if (url == null) throw new IOException("Resource not found: " + resourcePath);

            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    /** Odtwórz od początku, raz */
    public void play() {
        if (clip == null) return;
        if (clip.isRunning()) clip.stop();
        clip.setFramePosition(0);
        clip.start();
    }

    /** Zapętl odtwarzanie bez przerwy */
    public void loop() {
        if (clip == null) return;
        if (clip.isRunning()) clip.stop();
        clip.setFramePosition(0);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /** Zatrzymaj odtwarzanie */
    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    /** Zwolnij zasoby */
    public void close() {
        if (clip != null) {
            clip.close();
        }
    }
}
