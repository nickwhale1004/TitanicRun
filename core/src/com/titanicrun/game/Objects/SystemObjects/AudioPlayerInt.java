package com.titanicrun.game.Objects.SystemObjects;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by geniu on 23.04.2016.
 */
public class AudioPlayerInt implements ApplicationListener {

    public Music playBGM;
    public Music waterSound;
    Sound boomSound;

    @Override
    public void create() {

        playBGM = Gdx.audio.newMusic(Gdx.files.internal("sound/gameplayTitanicRun.mp3"));
        waterSound = Gdx.audio.newMusic(Gdx.files.internal("sound/water.mp3"));
        playBGM.setLooping(true);
        playBGM.setVolume(1f);
        waterSound.setLooping(true);
        waterSound.setVolume(1f);
        waterSound.play();
        playBGM.play();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

        playBGM.dispose();
        waterSound.dispose();

    }
}
