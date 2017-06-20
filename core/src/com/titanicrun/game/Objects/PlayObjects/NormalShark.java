package com.titanicrun.game.Objects.PlayObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.particles.influencers.RegionInfluencer;
import com.badlogic.gdx.math.Vector2;
import com.titanicrun.game.Screens.Screen;
import com.titanicrun.game.TitanicClass;

import java.util.Random;

/**
 * Created by Tom on 20.06.2017.
 */

public class NormalShark extends SizeChangeObject {
    private Random random;
    private Vector2 basePos;
    private float yMax, yMin;
    private int time, timer;
    private int process; //0-wait, 1-move, 2-touched, 3-down
    private int upDownProcess; //0-up, 1-down
    private Animation animNorm, animBack;
    private boolean left;
    public NormalShark(Animation animation, Animation animBack) {
        super(new Vector2(-1000,100), new Animation(animation.getTextures(),1),100);
        this.animNorm = animation;
        this.animBack = animBack;
        this.process = 0;
        this.upDownProcess = 0;
        this.random = new Random(System.currentTimeMillis());
        this.timer = 500+random.nextInt(400);
    }
    public void tick() {
        boolean path = random.nextBoolean();
        if(path) {
            left = true;
            position.x = TitanicClass.ScreenWidth;
            animation = animNorm;
        }
        else {
            left = false;
            position.x = -animation.getTexture().getWidth();
            animation =animBack;
        }
        int y = random.nextInt(50);
        position.y = y;
        yMax = position.y+10;
        yMin = position.y-10;
        if(yMin <=0)
            yMin = 1;
        time = 0;
        timer = 500+random.nextInt(400);
    }
    @Override
    public void update() {
        super.update();
        if(process == 0) {
            time++;
            if(time >= timer) {
                tick();
                process = 1;
            }
        }
        else if(process == 1) {
            if(upDownProcess == 0) {
                position.y++;
                if(position.y >= yMax) {
                    upDownProcess = 1;
                }
            }
            else if(upDownProcess == 1) {
                position.y--;
                if(position.y <= yMin) {
                    upDownProcess = 0;
                }
            }
            if(left) {
                position.x-=5;
                if(position.x <= -animation.getTexture().getWidth()) {
                    process = 0;
                }
            }
            else {
                position.x+=5;
                if(position.x >= TitanicClass.ScreenWidth) {
                    process = 0;
                }
            }
            if(isTouched()) {
                process = 2;
                basePos = new Vector2(position.x, position.y);
                changeTo(140, 1);
            }
        }
        else if(process == 2) {
            position.y = basePos.y -(animation.getTexture().getHeight()/100f*size-animation.getTexture().getHeight())/2f;
            position.x = basePos.x -(animation.getTexture().getWidth()/100f*size-animation.getTexture().getWidth())/2f;
            if (end) {
                animation = Screen.anim("fallObj/+10.png");
                position.y = basePos.y;
                position.x = basePos.x;
                size = 100;
                process = 3;
            }
        }
        else if (process == 3) {
            position.y-=2;
            if(position.y <= -animation.getTexture().getHeight()) {
                position.y = 100;
                position.x = -1000;
                process = 0;
            }
        }
    }
    private boolean isTouched() {
        for (int i = 0; i < 2; i++)
            if (Gdx.input.isTouched() && this.getBound().overlaps(TitanicClass.getMouse(i)))
                return true;
        return false;
    }
    @Override
    public void render(SpriteBatch spriteBatch) {
        super.render(spriteBatch);
    }
    @Override
    public void reset() {
        Gdx.app.log("reset", "");
        position.x = -1000;
        position.y= 100;
        upDownProcess = 0;
        process = 0;
        super.reset();
        super.update();
    }
}
