package com.titanicrun.game.Objects.PlayObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.titanicrun.game.Objects.BaseObject;
import com.titanicrun.game.Objects.PlayObjects.Animation;
import com.titanicrun.game.TitanicClass;

/**
 * Created by Никита on 15.04.2017.
 */
public class SizeChangeObject extends BaseObject {
    public Sprite sprite;
    public boolean end, isToBeBig;
    public float prevSize, size, toSize, speed, baseSize, baseToSize;
    public SizeChangeObject(Vector2 position, Animation animation, float size) {
        super(animation, position);
        this.sprite = new Sprite(animation.getTexture());
        this.sprite.setPosition(position.x, position.y);
        this.sprite.setSize((animation.getTexture().getWidth()/100f)*size,
                (animation.getTexture().getHeight()/100f)*size);
        this.size = size;
        this.prevSize = size;
        this.baseSize = size;
        this.baseToSize = size;
        end = true;
    }
    @Override
    public void update() {
        sprite.setTexture(animation.getTexture());
        sprite.setPosition(position.x, position.y);
        sprite.setSize((animation.getTexture().getWidth()/100f)*size,
                (animation.getTexture().getHeight()/100f)*size);
        if(!end) {
            prevSize = size;
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
        sprite.draw(spriteBatch);
    }
    public void reset() {
        size = baseSize;
        prevSize = size;
        toSize = baseToSize;
        end = false;
        if(toSize > size)
            isToBeBig=true;
        else
            isToBeBig=false;
    }
}
