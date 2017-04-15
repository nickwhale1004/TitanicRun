package com.titanicrun.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.titanicrun.game.Objects.PlayObjects.Animation;
import com.titanicrun.game.Objects.PlayObjects.BackgroundCreator;
import com.titanicrun.game.Objects.PlayObjects.EnemiesCreator;
import com.titanicrun.game.Objects.PlayObjects.FallObjectsCreator;
import com.titanicrun.game.Objects.PlayObjects.Player;
import com.titanicrun.game.Objects.PlayObjects.Score;
import com.titanicrun.game.Objects.PlayObjects.Shadow;
import com.titanicrun.game.Objects.PlayObjects.Water;
import com.titanicrun.game.Objects.SystemObjects.AudioPlayerInt;
import com.titanicrun.game.Objects.SystemObjects.Balance;
import com.titanicrun.game.Objects.SystemObjects.PlayerAnimation;

import java.util.ArrayList;

/**
 * Created by Никита on 15.04.2017.
 */
public class EducationScreen extends GameScreen {
    public EducationScreen(GameScreenManager gameScreenManager, Balance balance) {
        super(gameScreenManager, balance);
        Load();
    }

    @Override
    public void update() {

    }

    @Override
    public void render(SpriteBatch spriteBatch) {

    }
}
