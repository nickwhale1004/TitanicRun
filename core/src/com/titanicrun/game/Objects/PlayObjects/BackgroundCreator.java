package com.titanicrun.game.Objects.PlayObjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.titanicrun.game.Objects.BaseObject;
import com.titanicrun.game.Screens.GameScreen;
import com.titanicrun.game.TitanicClass;

import java.util.Random;

/**
 * Created by Никита on 29.01.2016.
 */
public class BackgroundCreator extends Creator {
    protected GameScreen currentGameScreen;
    protected Background preview;
    protected Background secondBack;
    protected Background firstBack;
    private byte process; //0 - preview + usuall start, 1 - usuall + previw end, 2 - usuall end
    static private int toPosX;
    public boolean pause;

    public BackgroundCreator(GameScreen gameScreen, Animation animation, Animation preview, boolean isPreview) {
        super(animation,100);
        this.preview = new Background(gameScreen, preview, new Vector2(0,TitanicClass.ScreenHeight));
        this.firstBack = new Background(gameScreen, animation, new Vector2(0,2*TitanicClass.ScreenHeight));
        this.secondBack = new Background(gameScreen, animation, new Vector2(0,3*TitanicClass.ScreenHeight));
        this.toPosX = 0;
        this.currentGameScreen = gameScreen;
        if(isPreview)
            this.process = 0;
        else
            this.process = 1;
        pause = true;

    }

    public void update() {
        if(!pause)
            time++;
        if (time >= interval) {
            tick();
            time = 0;
        }
        //движение
        if (process == 0) {
            preview.position.y--;
            firstBack.position.y--;
            secondBack.position.y--;
            if (preview.position.y + preview.animation.getTexture().getHeight() <= 0)
                process = 1;
        } else if (process == 1) {
            if (preview.position.y + preview.animation.getTexture().getHeight() > TitanicClass.ScreenHeight) {
                secondBack.position.y = firstBack.position.y;
                firstBack.position.y = preview.position.y;
                preview.position.y = 0;
            }

            firstBack.position.y--;
            secondBack.position.y--;
            preview.position.y--;
            if (firstBack.position.y + firstBack.animation.getTexture().getHeight() <= 0) {
                firstBack.position = new Vector2(firstBack.position.x, TitanicClass.ScreenHeight);
            }
            if (secondBack.position.y + secondBack.animation.getTexture().getHeight() <= 0) {
                secondBack.position = new Vector2(secondBack.position.x, TitanicClass.ScreenHeight);
            }
            if(preview.position.y <= -preview.animation.getTexture().getHeight()) {
                preview.position.y = -preview.animation.getTexture().getHeight();
            }
        }
        preview.update();
        secondBack.update();
        firstBack.update();
    }

    private void tick(){
        Random random = new Random();
        interval = 30+random.nextInt(100);
        toPosX = -20+random.nextInt(40);
        preview.toPosX = toPosX;
    }
    public void render(SpriteBatch spriteBatch) {
        preview.render(spriteBatch);
        firstBack.render(spriteBatch);
        secondBack.render(spriteBatch);
    }
    public void reset() {
        preview.reset(new Vector2(0,TitanicClass.ScreenHeight));
        firstBack.reset(new Vector2(0,2*TitanicClass.ScreenHeight));
        secondBack.reset(new Vector2(0,3*TitanicClass.ScreenHeight));
        time = 0;
    }

    static class Background extends BaseObject {
        public static int toPosX;
        public static float posX;
        public Background(GameScreen gameScreen, Animation animation, Vector2 position) {
            super(gameScreen, animation, position);
            posX = position.x;
        }

        @Override
        public void update() {
            animation.update();
            //шатание
            if (toPosX < 0) {
                if (posX > toPosX) {
                    posX -= 0.35f;
                } else {
                    toPosX = 0;
                }
            } else if (toPosX > 0) {
                if (posX < toPosX) {
                    posX += 0.35f;
                } else {
                    toPosX = 0;
                }
            }else {
                if (posX > 0) {
                    posX -= 0.35f;
                } else if (posX < 0) {
                    posX += 0.35f;
                }
            }
        }

        @Override
        public void render(SpriteBatch spriteBatch) {
            spriteBatch.draw(animation.getTexture(),posX, position.y);
        }
        public void reset(Vector2 position) {
            this.position = position;
            this.toPosX = 0;
        }
    }
}
