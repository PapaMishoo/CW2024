package com.example.demo;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundManager {

    /**
     * Plays a sound effect from the specified file, used for audio feedback on actions or events.
     *
     * @param sound the name of the sound file to be played (e.g., "impactsound.wav").
     */
    public static void playSound(String sound) {
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(SoundManager.class.getResource("/com/example/demo/sounds/" + sound).toExternalForm()));
        mediaPlayer.setVolume(0.1);
        mediaPlayer.play();
    }
}
