package com.titanicrun.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.titanicrun.game.Objects.PlayObjects.Animation;
import com.titanicrun.game.Objects.SystemObjects.Balance;
import com.titanicrun.game.Objects.SystemObjects.Button;
import com.titanicrun.game.Objects.PlayObjects.MoveObject;
import com.titanicrun.game.Objects.PlayObjects.Water;
import com.titanicrun.game.Objects.SystemObjects.GameTexturesLoader;
import com.titanicrun.game.Objects.SystemObjects.SettingsButton;
import com.titanicrun.game.TitanicClass;

/**
 * Created by Никита on 03.02.2016.
 */
public class MenuScreen extends Screen {
    private String screen;
    private SettingsButton settingsButton;
    private Button playButton, skinButton;
    private Water waterUp, waterMid, waterDown, ship;
    private Texture back;
    private byte process; // 0 -wait, 1 - toScreen, 2 - outScreen
    private  MoveObject slider;
    public MenuScreen(GameScreenManager gameScreenManager, String name) {
        super(gameScreenManager, name);
        Load();
    }
    public void Load() {
        Animation animation = GameTexturesLoader.get("buttons/play.png");
        this.back =  GameTexturesLoader.get("backs/night.png").getTexture();
        this.playButton = new Button(animation,
                 GameTexturesLoader.get("buttons/playTuched.png"), new Vector2(TitanicClass.ScreenWidth/2-animation.getTexture().getWidth()/2,
                TitanicClass.ScreenHeight/2-animation.getTexture().getHeight()/2),0);
        this.skinButton = new Button( GameTexturesLoader.get("buttons/skin.png"),  GameTexturesLoader.get("buttons/skinTuched.png"),
                new Vector2(playButton.position.x, playButton.position.y -  GameTexturesLoader.get("buttons/skin.png").getTexture().getHeight()-40),0);
        this.settingsButton = new SettingsButton ( GameTexturesLoader.get("buttons/settings.png"),
                new Vector2(TitanicClass.ScreenWidth - 74, TitanicClass.ScreenHeight - 74));
        float speed = 0.15f;
        this.waterUp = new Water(null, GameTexturesLoader.get("menu/waterMenu.png"), new Vector2(0,-0.5f*TitanicClass.ScreenHeight/10),
                0, speed, speed);
        this.waterMid = new Water(null, GameTexturesLoader.get("menu/waterMenu2.png"), new Vector2(0,-0.5f*2*TitanicClass.ScreenHeight/10),
                -0.5f*TitanicClass.ScreenHeight/10-10, speed*2.5f, 2.5f*speed);
        this.waterDown = new Water(null,  GameTexturesLoader.get("menu/waterMenu3.png"), new Vector2(0,-0.5f*4*TitanicClass.ScreenHeight/10),
                -0.5f*2*TitanicClass.ScreenHeight/10-10, speed*3f, speed*3f);
        Animation shipAnim =  GameTexturesLoader.get("menu/ship.png");
        this.ship = new Water(null, shipAnim, new Vector2(TitanicClass.ScreenWidth/2-shipAnim.getTexture().getWidth()/2,
                (TitanicClass.ScreenHeight/2-shipAnim.getTexture().getWidth()/2)*0.9f),
                (TitanicClass.ScreenHeight/2-shipAnim.getTexture().getWidth()/2),speed,speed);
        this.process = 0;
        this.slider = new MoveObject( GameTexturesLoader.get("backs/runner.png"), new Vector2(TitanicClass.ScreenWidth, 0),
                new Vector2(0,0),20);
    }
    @Override
    public void update() {
        if (process == 0) {
            if (playButton.isPressed()) {
                screen = "GameScreen";
                process = 1;
            }
            if(skinButton.isPressed()) {
                screen = "SkinScreen";
                process = 1;
            }
            if (settingsButton.isPressed()) {
                screen = "SettingScreen";
                process = 1;
            }
        } else if (process == 1) {
            slider.update();
            if (slider.end) {
                process = 2;
                slider.change(new Vector2(-slider.getTexture().getWidth(), 0));
            }
        } else if (process == 2) {
            slider.update();
            gameScreenManager.getScreen(screen).update();
            if (slider.end) {
                gameScreenManager.setScreen(screen);
            }
        }
        if (process != 2) {
            skinButton.update();
            playButton.update();
            settingsButton.update();
            waterDown.update();
            waterMid.update();
            waterUp.update();
            ship.update();
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        if(process == 2) {
            gameScreenManager.getScreen(screen).render(spriteBatch);
        }
        else {
            spriteBatch.draw(back, 0, 0);
            waterUp.render(spriteBatch);
            ship.render(spriteBatch);
            waterMid.render(spriteBatch);
            waterDown.render(spriteBatch);
            playButton.render(spriteBatch);
            skinButton.render(spriteBatch);
            settingsButton.render(spriteBatch);
        }
        slider.render(spriteBatch);
    }

    @Override
    public void reset() {
        process = 0;
        playButton.reset();
        skinButton.reset();
        settingsButton.reset();
        slider.reset();
        slider.change(new Vector2(0,0));
    }
}
