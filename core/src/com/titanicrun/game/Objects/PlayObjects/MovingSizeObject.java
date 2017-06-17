package com.titanicrun.game.Objects.PlayObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.titanicrun.game.Objects.BaseObject;

/**
 * Created by Никита on 15.04.2017.
 */
public class MovingSizeObject extends BaseObject{
    public boolean end;
    private SizeChangeObject obj;
    private int process; //0- zero to min, 1- min to max, 0- max to min, 2- die, 3- death
    public float maxSize, minSize, speed;
    public MovingSizeObject(Vector2 position, Animation animation, float maxSize, float minSize, float speed) {
        super(animation, position);
        this.maxSize = maxSize;
        this.minSize = minSize;
        this.process = 0;
        this.end = false;
        this.speed = speed;
        this.obj = new SizeChangeObject(position, animation, 0);
        this.obj.changeTo(minSize,speed);
    }
    @Override
    public void update() {
        obj.update();
        if(process == 0) {
            if(obj.end) {
                obj.changeTo(maxSize, speed);
                process = 1;
            }
        }
        else if(process == 1) {
            if(obj.end) {
                obj.changeTo(minSize,speed);
                process = 0;
            }
        }
        else if (process == 2) {
            if(obj.end) {
                obj.changeTo(0,2*speed);
                process = 3;
            }
        }
        else if (process == 3) {
            if(obj.end) {
                process = 4;
                end = true;
            }
        }
    }
    public void die() {
        process = 2;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        obj.render(spriteBatch);
    }
    public void reset() {
        obj.reset();
        process = 0;
        obj.changeTo(minSize,speed);
        end=false;
    }
}
