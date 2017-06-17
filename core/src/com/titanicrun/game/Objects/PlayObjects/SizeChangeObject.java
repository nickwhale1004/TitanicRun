package com.titanicrun.game.Objects.PlayObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.titanicrun.game.Objects.BaseObject;
import com.titanicrun.game.Objects.PlayObjects.Animation;

/**
 * Created by Никита on 15.04.2017.
 */
public class SizeChangeObject extends BaseObject {
    public boolean end, isToBeBig;
    public float size, toSize, speed, baseSize, baseToSize;
    public SizeChangeObject(Vector2 position, Animation animation, float size) {
        super(animation, position);
        this.size = size;
        this.baseSize = size;
        this.baseToSize = size;
        end = true;
    }
    @Override
    public void update() {
        if(!end) {
            if (isToBeBig) {
                size+=speed;
                if(size>=toSize) {
                    size = toSize;
                    end = true;
                }
            }
            else {
                size -= speed;
                if (size<=toSize) {
                    size = toSize;
                    end = true;
                }
            }
        }
    }
    public void changeTo(float toSize, float speed) {
        this.end = false;
        this.baseToSize = toSize;
        this.speed = speed;
        this.toSize = toSize;
        if(toSize > size)
            isToBeBig=true;
        else
            isToBeBig=false;
    }
    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(animation.getTexture(), position.x, position.y,
                (animation.getTexture().getWidth() / 100.f) * size, (animation.getTexture().getHeight() / 100.0f) * size);
    }
    public void reset() {
        size = baseSize;
        toSize = baseToSize;
        end = false;
        if(toSize > size)
            isToBeBig=true;
        else
            isToBeBig=false;
    }
}
