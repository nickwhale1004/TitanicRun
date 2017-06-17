package com.titanicrun.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.titanicrun.game.Objects.PlayObjects.Animation;
import com.titanicrun.game.Objects.PlayObjects.MoveObject;
import com.badlogic.gdx.graphics.Texture;
import com.titanicrun.game.Objects.SystemObjects.GameTexturesLoader;
import com.titanicrun.game.TitanicClass;

/**
 * Created by Никита on 06.04.2016.
 */
public class PauseScreen extends Screen {
    private byte process; //0 - start, 1 - count 3, 2 - count 2, 3 - count 1, 4 - run, 5 - to back
    private GameScreen gameScreen;
    private MoveObject pause, c3, c2, c1, pauseText, run;
    private Texture pauseTexture, big3, big2, big1;
    public PauseScreen(GameScreenManager gameScreenManager, GameScreen gameScreen, String name) {
        super(gameScreenManager, name);
        this.gameScreen = gameScreen;
        this.pauseTexture = GameTexturesLoader.get("numbers/pause.png").getTexture();
        this.big3 = GameTexturesLoader.get("numbers/big3.png").getTexture();
        this.big2 = GameTexturesLoader.get("numbers/big2.png").getTexture();
        this.big1 = GameTexturesLoader.get("numbers/big1.png").getTexture();
        this.pauseText = new MoveObject(new Animation(new Texture[]{pauseTexture},1),
                new Vector2(-TitanicClass.ScreenWidth,
                        TitanicClass.ScreenHeight/2 - pauseTexture.getHeight()/2),
                new Vector2(TitanicClass.ScreenWidth/2- pauseTexture.getWidth()/2,
                        TitanicClass.ScreenHeight/2 - pauseTexture.getHeight()/2), 10);
        this.pause = new MoveObject(GameTexturesLoader.get("backs/pause.png"),
                new Vector2(-TitanicClass.ScreenWidth,0), new Vector2(0,0), 10);
        this.c3 = new MoveObject(new Animation(new Texture[]{big3},1),
                new Vector2(TitanicClass.ScreenWidth/2 - big3.getWidth()/2, TitanicClass.ScreenHeight),
                new Vector2(TitanicClass.ScreenWidth/2 - big3.getWidth()/2, TitanicClass.ScreenHeight/2 -
                        big3.getHeight()/2), 8);
        this.c2 = new MoveObject(new Animation(new Texture[]{big2},1),
                new Vector2(TitanicClass.ScreenWidth/2 - big2.getWidth()/2, TitanicClass.ScreenHeight),
                new Vector2(TitanicClass.ScreenWidth/2 - big2.getWidth()/2, TitanicClass.ScreenHeight/2 -
                        big2.getHeight()/2), 8);
        this.c1 = new MoveObject(new Animation(new Texture[]{big1},1),
                new Vector2(TitanicClass.ScreenWidth/2 - big1.getWidth()/2, TitanicClass.ScreenHeight),
                new Vector2(TitanicClass.ScreenWidth/2 - big1.getWidth()/2, TitanicClass.ScreenHeight/2 -
                        big1.getHeight()/2), 8);
        this.run = new MoveObject(GameTexturesLoader.get("numbers/run.png"),
                new Vector2(TitanicClass.ScreenWidth/2 - GameTexturesLoader.get("numbers/run.png").getTexture().getWidth()/2, TitanicClass.ScreenHeight),
                new Vector2(TitanicClass.ScreenWidth/2 - GameTexturesLoader.get("numbers/run.png").getTexture().getWidth()/2,
                TitanicClass.ScreenHeight/2-GameTexturesLoader.get("numbers/run.png").getTexture().getHeight()/2),8);
    }

    @Override
    public void update() {
        if(process == 0) {
            pause.update();
            pauseText.update();
            if(pauseText.end && Gdx.input.isTouched()) {
                pauseText.change(new Vector2(TitanicClass.ScreenWidth/2-pauseTexture.getWidth()/2, -pauseTexture.getHeight()));
                process = 1;
            }
        }
        else if (process == 1) {
            pauseText.update();
            c3.update();
            if(c3.end) {
                c3.change(new Vector2(TitanicClass.ScreenWidth/2 - big3.getWidth()/2,
                        -big3.getHeight()));
                process = 2;
            }
        }
        else if (process == 2) {
            c3.update();
            c2.update();
            if(c2.end) {
                c2.change(new Vector2(TitanicClass.ScreenWidth/2 - big2.getWidth()/2, -big2.getHeight()));
                process = 3;
            }
        }
        else if (process == 3) {
            c2.update();
            c1.update();
            if(c1.end) {
                c1.change(new Vector2(TitanicClass.ScreenWidth / 2 - big1.getWidth() / 2, -big1.getHeight()));
                process = 4;
            }
        }
        else if (process == 4) {
            c1.update();
            run.update();
            if(run.end) {
                pause.reverse();
                run.change(new Vector2(-run.animation.getTexture().getWidth(),
                        TitanicClass.ScreenHeight/2-run.animation.getTexture().getHeight()/2));
                process = 5;
            }
        }
        else if (process == 5) {
            pause.update();
            run.update();
            gameScreen.update();
            if(pause.end) {
                gameScreen.pause = false;
                gameScreenManager.setScreen(gameScreen.name);
            }
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        gameScreen.render(spriteBatch);
        pause.render(spriteBatch);
        pauseText.render(spriteBatch);
        c3.render(spriteBatch);
        c2.render(spriteBatch);
        c1.render(spriteBatch);
        run.render(spriteBatch);
    }

    @Override
    public void reset() {

    }
}
