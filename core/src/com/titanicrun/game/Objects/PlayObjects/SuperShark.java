package com.titanicrun.game.Objects.PlayObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.titanicrun.game.Objects.BaseObject;
import com.titanicrun.game.Objects.SystemObjects.GameTexturesLoader;
import com.titanicrun.game.Screens.Screen;
import com.titanicrun.game.TitanicClass;

import java.util.Random;

/**
 * Created by Ra0to on 14.06.2017.
 */

public class SuperShark extends SizeChangeObject {
    private int process; // 1 - move; 0 - not move; 2 - bigger; 3 - smaller
    private int time;
    private int toPosY;
    private int predPos;
    private boolean moveTo;
    private boolean vector; // true - move to left, false - move to right
    private int timeToSwim;
    private Random generator;
    private Animation animBack, animNorm;
    private boolean wasTouched;
    private Vector2 basePos;

    public SuperShark(Animation animation, Animation animationBack) {
        super(new Vector2(0, 0), animation, 100);
        this.animNorm = new Animation(animation.getTextures(), 1);
        this.generator = new Random(System.currentTimeMillis());
        this.vector = generator.nextBoolean();
        this.moveTo = true;
        this.wasTouched = false;
        this.predPos = (int) position.y;
        this.toPosY = generator.nextInt() * 50;
        this.animBack = animationBack;
        if (vector) {
            this.animation = animBack;
            position.x = TitanicClass.ScreenWidth;
        } else {
            this.animation = animNorm;
            position.x = animation.getTexture().getWidth();
        }
        this.timeToSwim = generator.nextInt() * 10;
        this.time = 0;
        this.process = 0;
    }

    @Override
    public void update() {
        super.update();
        isTouched();
        Gdx.app.log("position", position.x + " " + position.y+" "+size);
        if (!wasTouched) {
            if (process == 0) {
                time++;
               // if (time >= timeToSwim) {
                    process = 1;
                // }
            } else if (process == 1) {
                time = 0;
                if (vector) {
                    if (position.x >= -animation.getTexture().getWidth()) {
                        position.x -= 5;
                        if (moveTo) {
                            position.y += (toPosY - position.y) / 50;
                        } else {
                            position.y += (predPos - position.y) / 50;
                        }
                        if ((int) position.y == toPosY) {
                            moveTo = false;
                        }
                    } else {
                        tick();
                    }
                } else {
                    if (position.x <= TitanicClass.ScreenWidth) {
                        position.x += 5;
                        if (moveTo) {
                            position.y += (toPosY - position.y) / 50;
                        } else {
                            position.y += (predPos - position.y) / 50;
                        }
                        if ((int) position.y == toPosY) {
                            moveTo = false;
                        }
                    } else {
                        tick();
                    }
                }
            }
        }
        else if (process == 2) {
            if(vector) {
                basePos .x-=500/(size*4);
            }
            else {
                basePos.x+=500/(size*4);
            }
            position.y = basePos.y -(animation.getTexture().getHeight()/100f*size-animation.getTexture().getHeight())/2f;
            position.x = basePos.x -(animation.getTexture().getWidth()/100f*size-animation.getTexture().getWidth())/2f;
            if (end) {
                animation = Screen.anim("fallObj/+10.png");
                position.y = basePos.y;
                position.x = basePos.x;
                size = 100;
                process = 3;
            }
        } else if (process == 3) {
            position.y--;
            if (position.y <= -animation.getTexture().getHeight()) {
                tick();
            }
        }
    }

    public boolean isTouched() {
        for (int i = 0; i < 2; i++) {
            if (Gdx.input.isTouched() && this.getBound().overlaps(TitanicClass.getMouse(i))) {
                wasTouched = true;
                process = 2;
                basePos = new Vector2(position.x, position.y);
                changeTo(140, 1);
                return true;
            }
        }
        return false;
    }
    private void tick() {
        process = 0;
        wasTouched = false;
        vector = generator.nextBoolean();
        if (vector) {
            position.x = TitanicClass.ScreenWidth;
            animation = animNorm;
        } else {
            position.x = -animation.getTexture().getWidth();
            animation = animBack;
        }
        timeToSwim = (int) (generator.nextDouble() * 10);
        toPosY = (int) (generator.nextDouble() * 50);
        if (generator.nextBoolean()) {
            position.y = toPosY - 50;
        } else {
            position.y = toPosY + 50;
        }
        predPos = (int) position.y;
        moveTo = true;
    }
    public void reset () {
        time = 0;
        wasTouched = false;
        process = 0;
        timeToSwim = generator.nextInt()*10;
        if (vector) {
            position.x = TitanicClass.ScreenWidth;
        } else {
            position.x = -animation.getTexture().getWidth();
        }
        toPosY = generator.nextInt() * 50;
        if (generator.nextBoolean()) {
            position.y = toPosY - 50;
        } else {
            position.y = toPosY + 50;
        }
        predPos = (int)position.y;
        moveTo = true;
    }
}
