package com.titanicrun.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.titanicrun.game.Objects.PlayObjects.MoveObject;
import com.titanicrun.game.Objects.SystemObjects.Balance;
import com.titanicrun.game.Objects.SystemObjects.GameTexturesLoader;

import javax.xml.transform.Result;

/**
 * Created by Никита on 14.06.2017.
 */

public class LoadingScreen extends Screen {
    GameTexturesLoader gtl;
    Texture back0, back1, back2;
    int posX;
    public LoadingScreen(GameScreenManager gameScreenManager, String name) {
        super(gameScreenManager, name);
        gtl = new GameTexturesLoader();
        back0 = new Texture("backs/loading/back0.png");
        back1 = new Texture("backs/loading/back1.png");
        back2 = new Texture("backs/loading/back3.png");
        posX = -308;
    }

    @Override
    public void update() {
        if(posX!=0) {
            gtl.update();
            posX += 28;
        }
        else {
            gtl.specialUpdate();Preferences sittings = Gdx.app.getPreferences("Balance");
            Balance balance= new Balance(sittings.getInteger("Balance"));
            gameScreenManager.addScreen(new SplashScreen(gameScreenManager,"SplashScreen"));
            gameScreenManager.addScreen(new GameScreen(gameScreenManager, balance,"GameScreen"));
            gameScreenManager.addScreen(new SettingsScreen(gameScreenManager, "SettingScreen"));
            gameScreenManager.addScreen(new SkinScreen(gameScreenManager, "SkinScreen"));
            gameScreenManager.addScreen(new EducationScreen(gameScreenManager, "EducationScreen"));
            gameScreenManager.addScreen(new MenuScreen(gameScreenManager, "MenuScreen"));
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(back0,0,0);
        spriteBatch.draw(back2,posX,0);
        spriteBatch.draw(back1,0,0);
    }

    @Override
    public void reset() {

    }
}
