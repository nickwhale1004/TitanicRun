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
    int process; // 0 - begin game, 1 - game, 2 - press to top, 3 - pause, 4 - fallobjcts
    protected MovingSizeObject touchToPlay;
    public EducationScreen(GameScreenManager gameScreenManager, Balance balance) {
        super(gameScreenManager, balance);
        Load();
        touchToPlay = new MovingSizeObject(new Vector2(50,100), anim("splashes/touchtoplay.png"), 100, 140, 1.5f);
        process = 0;
    }
    @Override
    public void Load() {
        super.Load();
        touchToPlay= new MovingSizeObject(new Vector2(50,100), anim("splashes/touchtoplay.png"), 100, 140, 1.5f);
    }
    @Override
    public void update() {
       super.update();
        if(process == 0) {
            touchToPlay.update();
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(night, 0, 0);
        backFirstLvl.render(spriteBatch);
        backSecondLvl.render(spriteBatch);
        player.render(spriteBatch);
        enemiesCreator.render(spriteBatch);
        fallObj.render(spriteBatch);
        water.render(spriteBatch);
        if(score.getScore()>50)
            shadow.render(spriteBatch);
        score.render(spriteBatch);
        //playBallance.render(spriteBatch);
        gameScore.render(spriteBatch);
        touchToPlay.render(spriteBatch);
    }
}
