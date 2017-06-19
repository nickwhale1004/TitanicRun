package com.titanicrun.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.titanicrun.game.Objects.SystemObjects.GameTexturesLoader;
import com.titanicrun.game.TitanicClass;
import com.titanicrun.game.Objects.PlayObjects.Animation;
import com.titanicrun.game.Objects.PlayObjects.MoveObject;
import com.titanicrun.game.Objects.PlayObjects.MovingSizeObject;
import com.titanicrun.game.Objects.SystemObjects.Button;

/**
 * Created by User on 16.04.2017.
 */

public class SettingsScreen extends  Screen {
    private Preferences prefsSound, prefsMusic;
    private boolean soundsTurn;
    private boolean musicTurn;
    private String screen;
    private Texture background;
    private byte process;
    private  MoveObject slider;
    private Button historyButton;
    private Button menuButton;
    private Button soundsButton;
    private Button musicButton;
    public SplashScreen splashScreen;

    public SettingsScreen(GameScreenManager gameScreenManager, String name) {
        super(gameScreenManager, name);
        if (!((Gdx.app.getPreferences("Score").getInteger("Score") > 0) || (Gdx.app.getPreferences("Balance").getInteger("Balance") != 0))) {
            Gdx.app.getPreferences("Sound").putBoolean("Sound", true);
            Gdx.app.getPreferences("Music").putBoolean("Music", true);
        }
        prefsSound = Gdx.app.getPreferences("Sound");
        prefsMusic = Gdx.app.getPreferences("Music");
        soundsTurn = prefsSound.getBoolean("Sound");
        musicTurn = prefsMusic.getBoolean("Music");
        slider = new MoveObject(GameTexturesLoader.get("backs/runner.png"), new Vector2(TitanicClass.ScreenWidth, 0),
                new Vector2(0,0),20);
        background = GameTexturesLoader.get("backs/background.png").getTexture();
        historyButton = new Button(
                GameTexturesLoader.get("buttons/historyButton.png"), GameTexturesLoader.get("buttons/historyButton.png"),
                new Vector2(TitanicClass.ScreenWidth/2 - 100, TitanicClass.ScreenHeight/2 - 45),0);
        menuButton = new Button(GameTexturesLoader.get("buttons/menuSmall.png"), GameTexturesLoader.get("buttons/menuSmallTuched.png"),
                new Vector2(TitanicClass.ScreenWidth/2 - 100,
                        TitanicClass.ScreenHeight/2 - 150),0);
        if (soundsTurn) {
            soundsButton = new Button(
                    GameTexturesLoader.get("buttons/sounds1.png"), GameTexturesLoader.get("buttons/sounds1.png"),
                    new Vector2(TitanicClass.ScreenWidth/2 - 110, TitanicClass.ScreenHeight/2 + 70),0);
        } else {
            soundsButton = new Button(
                    GameTexturesLoader.get("buttons/sounds3.png"), GameTexturesLoader.get("buttons/sounds3.png"),
                    new Vector2(TitanicClass.ScreenWidth/2 - 110, TitanicClass.ScreenHeight/2 + 70),0);
        }
        if (musicTurn) {
            musicButton = new Button(
                    GameTexturesLoader.get("buttons/sounds2.png"), GameTexturesLoader.get("buttons/sounds2.png"),
                    new Vector2(TitanicClass.ScreenWidth/2 + 10, TitanicClass.ScreenHeight/2 + 70),0);
        } else {
            musicButton = new Button(
                    GameTexturesLoader.get("buttons/sounds4.png"), GameTexturesLoader.get("buttons/sounds4.png"),
                    new Vector2(TitanicClass.ScreenWidth/2 + 10, TitanicClass.ScreenHeight/2 + 70),0);
        }
    }

    @Override
    public void update() {
        historyButton.update();
        menuButton.update();
        soundsButton.update();
        musicButton.update();
        if (soundsButton.isPressed()) {
            soundsTurn = !soundsTurn;
            if (soundsTurn) {
                soundsButton = new Button(
                        GameTexturesLoader.get("buttons/sounds1.png"), GameTexturesLoader.get("buttons/sounds1.png"),
                        new Vector2(TitanicClass.ScreenWidth/2 - 110, TitanicClass.ScreenHeight/2 + 70),0);
                soundsButton.update();
            } else {
                soundsButton = new Button(
                        GameTexturesLoader.get("buttons/sounds3.png"), GameTexturesLoader.get("buttons/sounds3.png"),
                        new Vector2(TitanicClass.ScreenWidth/2 - 110, TitanicClass.ScreenHeight/2 + 70),0);
                soundsButton.update();
            }
            prefsSound.putBoolean("Sound", soundsTurn);
            prefsSound.flush();
        }

        if (musicButton.isPressed()) {
            musicTurn = !musicTurn;
            if (musicTurn) {
                musicButton = new Button(
                        GameTexturesLoader.get("buttons/sounds2.png"), GameTexturesLoader.get("buttons/sounds2.png"),
                        new Vector2(TitanicClass.ScreenWidth/2 + 10, TitanicClass.ScreenHeight/2 + 70),0);
                musicButton.update();
            } else {
                musicButton = new Button(
                        GameTexturesLoader.get("buttons/sounds4.png"), GameTexturesLoader.get("buttons/sounds4.png"),
                        new Vector2(TitanicClass.ScreenWidth/2 + 10, TitanicClass.ScreenHeight/2 + 70),0);
                musicButton.update();
            }
            prefsMusic.putBoolean("Music", musicTurn);
            prefsMusic.flush();
        }

        if (historyButton.isPressed()) {
            process = 1;
            screen = "SplashScreen";
            process = 3;
            slider.change(new Vector2(-slider.getTexture().getWidth(), 0));
        }
        if (menuButton.isPressed()) {
            process = 2;
        }
        if (process == 2) {
            slider.update();
            if (slider.end) {
                process = 3;
                screen = "MenuScreen";
                slider.change(new Vector2(-slider.getTexture().getWidth(), 0));
            }
        }
        if (process == 3) {
            if(screen == "MenuScreen") {
                slider.update();
                gameScreenManager.getScreen(screen).update();
                if (slider.end) {
                    gameScreenManager.setScreen(screen);
                }
            }
            else {
                gameScreenManager.setScreen(screen);
            }
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        if (process != 3) {
            spriteBatch.draw(background, 0, 0);
            menuButton.render(spriteBatch);
            musicButton.render(spriteBatch);
            soundsButton.render(spriteBatch);
            historyButton.render(spriteBatch);
        } else {
            gameScreenManager.getScreen(screen).render(spriteBatch);
        }
        slider.render(spriteBatch);
    }

    @Override
    public void reset() {
        slider.reset();
        slider.change(new Vector2(0,0));
        menuButton.reset();
        soundsButton.reset();
        menuButton.reset();
        historyButton.reset();
        process=0;
    }
}