package com.titanicrun.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.titanicrun.game.Objects.PlayObjects.Animation;
import com.titanicrun.game.Objects.PlayObjects.BackgroundCreator;
import com.titanicrun.game.Objects.PlayObjects.EnemiesCreator;
import com.titanicrun.game.Objects.PlayObjects.FallObjectsCreator;
import com.titanicrun.game.Objects.PlayObjects.MoveObject;
import com.titanicrun.game.Objects.PlayObjects.MovingSizeObject;
import com.titanicrun.game.Objects.PlayObjects.Player;
import com.titanicrun.game.Objects.PlayObjects.Score;
import com.titanicrun.game.Objects.PlayObjects.Shadow;
import com.titanicrun.game.Objects.PlayObjects.Water;
import com.titanicrun.game.Objects.SystemObjects.AudioPlayerInt;
import com.titanicrun.game.Objects.SystemObjects.Balance;
import com.titanicrun.game.Objects.SystemObjects.PlayerAnimation;
import com.titanicrun.game.TitanicClass;

import java.util.ArrayList;

/**
 * Created by Никита on 15.04.2017.
 */
public class EducationScreen extends GameScreen {
    int process; // 0 - begin game,1 - press to top, 2 - pause, 3 - fallobjcts
    protected MovingSizeObject touchToPlay, goTop, pressPause, catchFall;
    public EducationScreen(GameScreenManager gameScreenManager, Balance balance) {
        super(gameScreenManager, balance);
        Load();
    }
    @Override
    public void Load() {
        super.Load();
        touchToPlay= new MovingSizeObject(new Vector2(50,100), anim("splashes/touchtoplay.png"), 100, 140, 2f);
        process = 0;
        goTop = new MovingSizeObject(new Vector2(50,100), anim("splashes/pressscreen.png"), 100, 140, 2f);
        pressPause = new MovingSizeObject(new Vector2(50,100), anim("splashes/presspause.png"), 100, 140, 2f);
        catchFall = new MovingSizeObject(new Vector2(50,100), anim("splashes/catchit.png"), 100, 140, 2f);
    }
    @Override
    public void update() {
        int moneybefore = playBallance.getBalance();
        if (process == 0) {
            super.update();
            touchToPlay.update();
            if (Gdx.input.justTouched()) {
                touchToPlay.die();
            }
            if (touchToPlay.end) {
                process = 1;
            }
        } else if (process == 1) {
            goTop.update();
            if(Gdx.input.isTouched()) {
                player.update();
                backFirstLvl.update();
            }
            if (player.position.y == TitanicClass.ScreenHeight - player.animation.getTexture().getHeight()) {
                goTop.die();
            }
            if (goTop.end) {
                process = 2;
            }
        } else if (process == 2) {
            pressPause.update();
            if (pause) {
                pressPause.die();
                fallObj.isFirst = false;
            }
            if (pressPause.end) {
                process = 3;
            }
        } else if (process == 3) {
            catchFall.update();
            if (moneybefore < playBallance.getBalance()) {
                catchFall.die();
            }
            if (catchFall.end) {
                process = 4;
            }
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        super.render(spriteBatch);
        touchToPlay.render(spriteBatch);
        goTop.render(spriteBatch);
        pressPause.render(spriteBatch);
        catchFall.render(spriteBatch);
    }
}
