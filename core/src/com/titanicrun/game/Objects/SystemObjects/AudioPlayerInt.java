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
    public boolean isPlayMusic;
    public boolean isPlaySounds;
    public Map<String, Music> dictionary;
    public Map<String, Sound> soundDictionary;
    @Override
    public void create() {
        dictionary = new HashMap<String, Music>();
        soundDictionary = new HashMap<String, Sound>();
        dictionary.put("BGM", Gdx.audio.newMusic(Gdx.files.internal("sound/gameplayTitanicRun.mp3")));
        dictionary.get("BGM").setLooping(true);
        dictionary.get("BGM").setVolume(0.1f);
        dictionary.put("Water", Gdx.audio.newMusic(Gdx.files.internal("sound/water2.mp3")));
        dictionary.get("Water").setLooping(true);
        dictionary.get("Water").setVolume(0.1f);
        dictionary.put("run", Gdx.audio.newMusic(Gdx.files.internal("sound/run.wav")));
        dictionary.get("run").setLooping(true);
        dictionary.get("run").setVolume(0.1f);
        soundDictionary.put("Death", Gdx.audio.newSound(Gdx.files.internal("sound/death.mp3")));
        soundDictionary.get("Death").setVolume(1, 2f);
        soundDictionary.put("DeathWater", Gdx.audio.newSound(Gdx.files.internal("sound/deathWater.mp3")));
        soundDictionary.get("DeathWater").setVolume(2,2f);
        soundDictionary.put("Button", Gdx.audio.newSound(Gdx.files.internal("sound/button.mp3")));
        soundDictionary.get("Button").setVolume(3,2f);
        soundDictionary.put("getObject", Gdx.audio.newSound(Gdx.files.internal("sound/getObject.wav")));
        soundDictionary.get("getObject").setVolume(4,0.1f);
        soundDictionary.put("getShark", Gdx.audio.newSound(Gdx.files.internal("sound/getShark.wav")));
        soundDictionary.get("getShark").setVolume(5,0.2f);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
    }

    public void playMusic(String sound) {
        if (isPlayMusic) {
            dictionary.get(sound).play();
        }
    }

    public void playSound(String sound) {
        if (isPlaySounds) {
            soundDictionary.get(sound).play();
        }
    }
    public void pauseAudio(String sound) {
        if(dictionary.containsKey(sound))
            dictionary.get(sound).stop();
        else
            soundDictionary.get(sound).stop();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        for(String s : dictionary.keySet()) {
            dictionary.get(s).dispose();
        }
        for(String s : soundDictionary.keySet()) {
            soundDictionary.get(s).dispose();
        }
    }
}
