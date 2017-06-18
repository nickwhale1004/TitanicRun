package com.titanicrun.game.Objects.PlayObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.titanicrun.game.Objects.BaseObject;

/**
 * Created by Никита on 15.04.2017.
 */
public class MovingSizeObject extends BaseObject{
    public boolean end, begin;
    private SizeChangeObject obj;
    private int process; //0- zero to min, 1- min to max, 0- max to min, 2- die, 3- death
    public float maxSize, minSize, speed, prevSize;
    private Vector2 basePos;
    public MovingSizeObject(Vector2 position, Animation animation, float maxSize, float minSize, float speed) {
        super(animation, position);
        this.maxSize = maxSize;
        this.begin = true;
        this.basePos = new Vector2(position.x, position.y);
        this.minSize = minSize;
        this.process = 0;
        this.end = false;
        this.speed = speed;
        this.prevSize = minSize;
        this.obj = new SizeChangeObject(position, animation, 0);
        this.obj.changeTo(minSize,4*speed);
    }
    @Override
    public void update() {
        obj.update();
        position.x = basePos.x - (animation.getTexture().getWidth()/100f)*obj.size/2;
        position.y = basePos.y - (animation.getTexture().getHeight()/100f)*obj.size/2;
        obj.position.x += (prevSize - obj.size)/2f;
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
                obj.changeTo(0,4*speed);
                process = 3;
            }
        }
        else if (process == 3) {
            if(obj.end) {
                process = 4;
                end = true;
            }
        }
        prevSize = obj.size;
    }
    public void die() {
        process = 2;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        if(!end) obj.render(spriteBatch);
    }
    public void reset() {
        obj.reset();
        process = 0;
        obj.changeTo(minSize,speed);
        end=false;
        begin = true;
    }
}
