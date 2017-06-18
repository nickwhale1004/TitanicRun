package com.titanicrun.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.titanicrun.game.Objects.PlayObjects.MoveObject;
import com.titanicrun.game.Objects.SystemObjects.Balance;
import com.titanicrun.game.Objects.SystemObjects.GameTexturesLoader;

import javax.xml.transform.Result;

/**
 * Created by Никита on 14.06.2017.
 */

public class LoadingScreen extends Screen {
    GameTexturesLoader gtl;
    MoveObject back0, back1, back2;
    int process; //0 - in, 1 - work, 2 - out
    public LoadingScreen(GameScreenManager gameScreenManager, String name) {
        super(gameScreenManager, name);
        back0 = new MoveObject(anim("backs/loading/back0.png"), new Vector2(-480,0), new Vector2(0,0),15);
        back1 = new MoveObject(anim("backs/loading/back1.png"), new Vector2(-480,0), new Vector2(0,0),15);
        back2 = new MoveObject(anim("backs/loading/back3.png"), new Vector2(-480-308,0), new Vector2(-308,0),15);
        gtl = new GameTexturesLoader();
    }

    @Override
    public void update() {
        back0.update();
        back1.update();
        if(process == 0) {
            back2.update();
            if(back1.end)
                process = 1;
        }
        else if (process == 1) {
            if (back2.position.x !=0) {
                gtl.update();
                back2.position.x += 28;
            }
            else {
                process = 2;
                gtl.specialUpdate();
                Preferences sittings = Gdx.app.getPreferences("Balance");
                Balance balance= new Balance(sittings.getInteger("Balance"));
                gameScreenManager.addScreen(new SplashScreen(gameScreenManager,"SplashScreen"));
                gameScreenManager.addScreen(new GameScreen(gameScreenManager, balance,"GameScreen"));
                gameScreenManager.addScreen(new SettingsScreen(gameScreenManager, "SettingScreen"));
                gameScreenManager.addScreen(new SkinScreen(gameScreenManager, "SkinScreen"));
                gameScreenManager.addScreen(new EducationScreen(gameScreenManager, "EducationScreen"));
                gameScreenManager.addScreen(new MenuScreen(gameScreenManager, "MenuScreen"));
                gameScreenManager.setScreen("LoadingScreen");
                back0.reverse();
                back0.speed.x*=2;
                back1.reverse();
                back1.speed.x*=2;
                back2.reverse();
                back2.speed.x*=2;
            }
        }
        else if (process == 2){
            back2.update();
            if (Gdx.app.getPreferences("Score").getInteger("Score") > 0) {
                gameScreenManager.getScreen("MenuScreen").update();
                if (back0.end) {
                    gameScreenManager.setScreen("MenuScreen");
                }
            } else {
                gameScreenManager.getScreen("SplashScreen").update();
                if (back0.end) {
                    gameScreenManager.setScreen("SplashScreen");
                }
            }
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        if(process == 2) {
            if (Gdx.app.getPreferences("Score").getInteger("Score") > 0) {
                gameScreenManager.getScreen("MenuScreen").render(spriteBatch);
            } else {
                gameScreenManager.getScreen("SplashScreen").render(spriteBatch);
            }
        }
        back0.render(spriteBatch);
        back2.render(spriteBatch);
        back1.render(spriteBatch);
    }

    @Override
    public void reset() {

    }
}
