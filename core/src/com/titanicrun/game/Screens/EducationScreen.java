package com.titanicrun.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.titanicrun.game.Objects.PlayObjects.Animation;
import com.titanicrun.game.Objects.PlayObjects.MoveObject;
import com.titanicrun.game.Objects.PlayObjects.MovingSizeObject;
import com.titanicrun.game.Objects.SystemObjects.Balance;
import com.titanicrun.game.Objects.SystemObjects.GameTexturesLoader;
import com.titanicrun.game.TitanicClass;

/**
 * Created by Никита on 15.04.2017.
 */
public class EducationScreen extends GameScreen {
    int process; // 0 - begin game,1 - press to top, 2 - pause, 3 - fallobjcts
    protected MovingSizeObject touchToPlay, goTop, pressPause, catchFall;
    private MoveObject goodLuck, pauseField;
    public EducationScreen(GameScreenManager gameScreenManager, String name) {
        super(gameScreenManager, new Balance(Gdx.app.getPreferences("Balance").getInteger("Balance")), name);
        Load();
    }
    @Override
    public void Load() {
        super.Load();
        beginGame = false;
        Animation goodLAnim = GameTexturesLoader.get("splashes/goodluck.png");
        Animation pauseAnim = GameTexturesLoader.get("backs/pauseField.png");
        goodLuck = new MoveObject(goodLAnim,
                new Vector2(-goodLAnim.getTexture().getWidth(),
                        TitanicClass.ScreenHeight/2 - goodLAnim.getTexture().getHeight()/2 ),
                new Vector2(0, TitanicClass.ScreenHeight/2 - goodLAnim.getTexture().getHeight()/2), 10);
        pauseField = new MoveObject(pauseAnim,
                new Vector2(0, TitanicClass.ScreenHeight),
                new Vector2(0, TitanicClass.ScreenHeight - pauseAnim.getTexture().getHeight()), 3);
        touchToPlay= new MovingSizeObject(new Vector2(200,100), GameTexturesLoader.get("splashes/touchtoplay.png"), 100, 140, 2f);
        process = 0;
        goTop = new MovingSizeObject(new Vector2(200,100), GameTexturesLoader.get("splashes/pressscreen.png"), 100, 140, 2f);
        pressPause = new MovingSizeObject(new Vector2(200,100), GameTexturesLoader.get("splashes/presspause.png"), 100, 140, 2f);
        catchFall = new MovingSizeObject(new Vector2(200,100), GameTexturesLoader.get("splashes/catchit.png"), 100, 140, 2f);
    }
    @Override
    public void update() {
        touchToPlay.update();
        int moneybefore = playBallance.getBalance();
        if (process == 0) {
            super.update();
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
                backLvl.get(lvl).update();
            }
            if (player.position.y >= TitanicClass.ScreenHeight - player.animation.getTexture().getHeight()) {
                process = 11;
                goTop.die();
            }

        } else if(process == 11)  {
            goTop.update();
            backLvl.get(lvl).update();
            player.update();
            if (goTop.end) {
                process = 2;
            }
        } else if (process == 2) {
            pauseField.update();
            if(Gdx.input.justTouched()) {
                if (!pause && TitanicClass.getMouse().getY() >= TitanicClass.ScreenHeight - 125) {
                    pause = true;
                    gameScreenManager.addScreen(new PauseScreen(gameScreenManager, this, "Pause"));
                }
            }
            pressPause.update();
            if (pause) {
                pressPause.die();
                pauseField.reverse();
                fallObj.isFirst = false;
                process = 22;
            }

        } else if(process == 22) {
            pauseField.update();
            pressPause.update();
            if (pressPause.end) {
                process = 3;
            }
        } else if (process == 3) {
            pauseField.update();
            player.animation.update();
            fallObj.interval = 300;
            fallObj.update();
            gameScore.update();
            catchFall.update();
            if (moneybefore < playBallance.getBalance()) {
                catchFall.die();
            }
            if (catchFall.end) {
                process = 4;
            }
        }
        else if (process == 4) {
            pauseField.update();
            goodLuck.update();
            if (goodLuck.end) {
                if (Gdx.input.justTouched()) {
                    goodLuck.change(new Vector2(TitanicClass.ScreenWidth,
                            TitanicClass.ScreenHeight / 2 - goodLuck.animation.getTexture().getHeight() / 2));
                    process = 44;
                }
            }
        }
        else if (process == 44) {
            goodLuck.update();
            if(goodLuck.end) {
                fallObj.interval = 600;
                process = 5;
            }
        }
        else if (process == 5) {
            super.update();
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        super.render(spriteBatch);
        pauseField.render(spriteBatch);
        touchToPlay.render(spriteBatch);
        goTop.render(spriteBatch);
        pressPause.render(spriteBatch);
        catchFall.render(spriteBatch);
        goodLuck.render(spriteBatch);
    }
    @Override
    public void reset() {
        super.reset();
        fallObj.isFirst=true;
        beginGame = false;
        process = 0;
        pauseField.reset();
        touchToPlay.reset();
        goTop.reset();
        pressPause.reset();
        catchFall.reset();
    }
}
