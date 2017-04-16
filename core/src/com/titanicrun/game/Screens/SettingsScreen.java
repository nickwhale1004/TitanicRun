package com.titanicrun.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.titanicrun.game.TitanicClass;
import com.titanicrun.game.Objects.PlayObjects.Animation;
import com.titanicrun.game.Objects.PlayObjects.MoveObject;
import com.titanicrun.game.Objects.PlayObjects.MovingSizeObject;
import com.titanicrun.game.Objects.SystemObjects.Button;

/**
 * Created by User on 16.04.2017.
 */

public class SettingsScreen extends  Screen {


    private Screen screen;
    private Texture background;
    private byte process;
    private  MoveObject slider;
    private Button historyButton;
    private Button menuButton;

    public SettingsScreen(GameScreenManager gameScreenManager) {
        super(gameScreenManager);
        slider = new MoveObject(anim("backs/runner.png"), new Vector2(TitanicClass.ScreenWidth, 0),
                new Vector2(0,0),20);
        background = new Texture("backs/background.png");
        historyButton = new Button(
                anim("buttons/historyButton.png"), anim("buttons/historyButton.png"),
                new Vector2(TitanicClass.ScreenWidth/2 - 100, TitanicClass.ScreenHeight/2 - 45));
        menuButton = new Button(anim("buttons/menuSmall.png"), anim("buttons/menuSmallTuched.png"),
                new Vector2(TitanicClass.ScreenWidth/2 - 100,
                        TitanicClass.ScreenHeight/2 - 150));
    }

    @Override
    public void update() {
        historyButton.update();
        menuButton.update();
        if (historyButton.isPressed()) {
            process = 1;
            screen = new SplashScreen(gameScreenManager);
            gameScreenManager.setScreen(screen);
        }
        if (menuButton.isPressed()) {
            process = 2;
        }
        if (process == 2) {
            slider.update();
            screen = new MenuScreen(gameScreenManager);
            if (slider.end) {
                process = 3;
                slider.change(new Vector2(-slider.getTexture().getWidth(), 0));
            }
        }
        if (process == 3) {
            slider.update();
            screen.update();
            if (slider.end) {
                gameScreenManager.setScreen(screen);
            }
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        if (process != 3) {
            spriteBatch.draw(background, 0, 0);
            menuButton.render(spriteBatch);
            historyButton.render(spriteBatch);
        } else {
            screen.render(spriteBatch);
        }
        slider.render(spriteBatch);
    }
}