package com.example.demo;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundManager {
    public static void playSound(String sound) {
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(SoundManager.class.getResource("/com/example/demo/sounds/" + sound).toExternalForm()));
        mediaPlayer.setVolume(0.1);
        mediaPlayer.play();
    }
}
