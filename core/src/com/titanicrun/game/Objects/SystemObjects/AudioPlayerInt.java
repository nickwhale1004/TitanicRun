package com.titanicrun.game.Objects.SystemObjects;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by geniu on 23.04.2016.
 */
public class AudioPlayerInt implements ApplicationListener {

    public Map<String, Music> dictionary;
    //public Music playBGM;
    public Music waterSound;
    Sound boomSound;

    @Override
    public void create() {
        dictionary = new HashMap<String, Music>();
        dictionary.put("BGM", Gdx.audio.newMusic(Gdx.files.internal("sound/gameplayTitanicRun.mp3")));
        dictionary.get("BGM").setLooping(true);
        dictionary.get("BGM").setVolume(0.1f);
        dictionary.put("Water", Gdx.audio.newMusic(Gdx.files.internal("sound/water.mp3")));
        dictionary.get("Water").setLooping(true);
        dictionary.get("Water").setVolume(0.1f);
        dictionary.put("Death", Gdx.audio.newMusic(Gdx.files.internal("sound/death.mp3")));
        dictionary.get("Death").setVolume(2f);
        dictionary.put("DeathWater", Gdx.audio.newMusic(Gdx.files.internal("sound/deathWater.mp3")));
        dictionary.get("DeathWater").setVolume(2f);
        dictionary.put("Button", Gdx.audio.newMusic(Gdx.files.internal("sound/button.mp3")));
        dictionary.get("Button").setVolume(2f);
        dictionary.put("getObject", Gdx.audio.newMusic(Gdx.files.internal("sound/getObject.wav")));
        dictionary.get("getObject").setVolume(0.1f);
        //playBGM = Gdx.audio.newMusic(Gdx.files.internal("sound/gameplayTitanicRun.mp3"));
        //waterSound = Gdx.audio.newMusic(Gdx.files.internal("sound/water.mp3"));

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
    }

    public void playMusic(String sound) {
        if (Gdx.app.getPreferences("Music").getBoolean("Music")) {
            dictionary.get(sound).play();
        }
    }

    public void playSound(String sound) {
        if (Gdx.app.getPreferences("Sound").getBoolean("Sound")) {
            dictionary.get(sound).play();
        }
    }

    public void pauseAudio(String sound) {
        dictionary.get(sound).stop();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

        dictionary.get("BGM").dispose();
        waterSound.dispose();

    }
}
