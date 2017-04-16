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
import com.titanicrun.game.TitanicClass;

/**
 * Created by Никита on 03.02.2016.
 */
public class MenuScreen extends Screen {
    private Screen screen;
    private Button playButton, skinButton, settingsButton;
    private Water waterUp, waterMid, waterDown, ship;
    private Texture back;
    private byte process; // 0 -wait, 1 - toScreen, 2 - outScreen
    private  MoveObject slider;
    private Preferences sittings;
    private Balance balance;
    public MenuScreen(GameScreenManager gameScreenManager) {
        super(gameScreenManager);
        Load();
    }
    public MenuScreen(GameScreenManager gameScreenManager, Balance balance) {
        super(gameScreenManager);
        Load();
        this.balance = balance;
    }
    public void Load() {
        Animation animation = anim("buttons/play.png");
        this.back = new Texture("backs/night.png");
        this.playButton = new Button(animation,
                anim("buttons/playTuched.png"), new Vector2(TitanicClass.ScreenWidth/2-animation.getTexture().getWidth()/2,
                TitanicClass.ScreenHeight/2-animation.getTexture().getHeight()/2));
        this.skinButton = new Button(anim("buttons/skin.png"), anim("buttons/skinTuched.png"),
                new Vector2(playButton.position.x, playButton.position.y - anim("buttons/skin.png").getTexture().getHeight()-40));
        this.settingsButton = new Button (anim("buttons/settings.png"), anim("buttons/settings.png"),
                new Vector2(TitanicClass.ScreenWidth - 74, TitanicClass.ScreenHeight - 74));
        float speed = 0.15f;
        this.waterUp = new Water(null,anim("menu/waterMenu.png"), new Vector2(0,-0.5f*TitanicClass.ScreenHeight/10),
                0, speed, speed);
        this.waterMid = new Water(null,anim("menu/waterMenu2.png"), new Vector2(0,-0.5f*2*TitanicClass.ScreenHeight/10),
                -0.5f*TitanicClass.ScreenHeight/10-10, speed*2.5f, 2.5f*speed);
        this.waterDown = new Water(null, anim("menu/waterMenu3.png"), new Vector2(0,-0.5f*4*TitanicClass.ScreenHeight/10),
                -0.5f*2*TitanicClass.ScreenHeight/10-10, speed*3f, speed*3f);
        Animation shipAnim = anim("menu/ship.png");
        this.ship = new Water(null, shipAnim, new Vector2(TitanicClass.ScreenWidth/2-shipAnim.getTexture().getWidth()/2,
                (TitanicClass.ScreenHeight/2-shipAnim.getTexture().getWidth()/2)*0.9f),
                (TitanicClass.ScreenHeight/2-shipAnim.getTexture().getWidth()/2),speed,speed);
        this.process = 0;
        this.slider = new MoveObject(anim("backs/runner.png"), new Vector2(TitanicClass.ScreenWidth, 0),
                new Vector2(0,0),20);
        this.sittings = Gdx.app.getPreferences("Balance");
        this.balance = new Balance(sittings.getInteger("Balance"),
                new Vector2(TitanicClass.ScreenWidth-5-TitanicClass.scoreABC[0].getWidth()*String.valueOf(balance).length(),
                        TitanicClass.ScreenHeight-5-TitanicClass.scoreABC[0].getHeight()-5));
        this.balance.setBalance(sittings.getInteger("Balance"));
    }
    @Override
    public void update() {
        if (process == 0) {
            if (playButton.isPressed()) {
                screen = new GameScreen(gameScreenManager, balance);
                process = 1;
            }
            if(skinButton.isPressed()) {
                balance.drawPosition = new Vector2(105,TitanicClass.ScreenHeight - TitanicClass.scoreABC[0].getHeight()/2 - 10);
                screen = new SkinScreen(gameScreenManager, balance);
                process = 1;
            }
            if (settingsButton.isPressed()) {
                screen = new SettingsScreen(gameScreenManager);
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
            screen.update();
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
            screen.render(spriteBatch);
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
}
