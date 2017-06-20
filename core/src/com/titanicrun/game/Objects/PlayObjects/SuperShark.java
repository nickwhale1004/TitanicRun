package com.titanicrun.game.Objects.PlayObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.titanicrun.game.Objects.BaseObject;
import com.titanicrun.game.TitanicClass;

import java.util.Random;

/**
 * Created by Ra0to on 14.06.2017.
 */

public class SuperShark extends BaseObject{
    private int process; // 1 - move; 0 - not move
    private int time;
    private int toPosY;
    private int predPos;
    private boolean moveTo;
    private boolean vector; // true - move to left, false - move to right
    private int timeToSwim;
    private Random generator;
    private Animation animBack;
    private boolean wasTouched;

    public SuperShark(Animation animation, Animation animationBack) {
        super(animation, new Vector2(0,0));
        this.generator = new Random(System.currentTimeMillis());
        this.vector = generator.nextBoolean();
        this.moveTo = true;
        this.wasTouched = false;
        this.predPos = (int)position.y;
        this.toPosY = (int)(generator.nextDouble() * 50);
        this.animBack = animationBack;
        if (vector) {
            position.x = TitanicClass.ScreenWidth;

        } else {
            position.x = -animation.getTexture().getWidth();
        }
        this.timeToSwim = (int)(generator.nextDouble()*10);
        this.time =0;
        this.process = 0;
    }
    @Override
    public void update() {
        if (!wasTouched) {
            if (process == 0) {
                time++;
                if (time >= timeToSwim) {
                    process = 1;
                }
            } else {
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
                        vector = generator.nextBoolean();
                        if (vector) {
                            position.x = TitanicClass.ScreenWidth;
                        } else {
                            position.x = -animation.getTexture().getWidth();
                        }
                        process = 0;
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
                        vector = generator.nextBoolean();
                        if (vector) {
                            position.x = TitanicClass.ScreenWidth;
                        } else {
                            position.x = -animation.getTexture().getWidth();
                        }
                        process = 0;
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
                }
            }
        }
    }

    public boolean isTouched () {
        for (int i = 0; i < 2; i++) {
            if (Gdx.input.isTouched() && this.getBound().overlaps(TitanicClass.getMouse(i))) {
                wasTouched = true;
                return true;
            }
        }
        return false;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        if (vector) {
            spriteBatch.draw(animation.getTexture(), position.x, position.y);
        } else {
            spriteBatch.draw(animBack.getTexture(), position.x, position.y);
        }
    }
    public void reset () {
        time = 0;
        wasTouched = false;
        process = 0;
        timeToSwim = (int)(generator.nextDouble()*10);
        if (vector) {
            position.x = TitanicClass.ScreenWidth;
        } else {
            position.x = -animation.getTexture().getWidth();
        }
        toPosY = (int)(generator.nextDouble() * 50);
        if (generator.nextBoolean()) {
            position.y = toPosY - 50;
        } else {
            position.y = toPosY + 50;
        }
        predPos = (int)position.y;
        moveTo = true;
    }
}
