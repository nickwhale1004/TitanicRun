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
    public float size, toSize, speed;
    public SizeChangeObject(Vector2 position, Animation animation) {
        super(animation, position);
        size = 10;
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
        this.speed = speed;
        this.toSize = toSize;
        if(toSize > size)
            isToBeBig=true;
        else
            isToBeBig=false;
    }
    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(animation.getTexture(),position.x, position.y,
                (animation.getTexture().getWidth()/100.f)*size, (animation.getTexture().getHeight()/100.0f)*size);
        Gdx.app.log("asd ",position.x +" "+position.y+" size="+size);
    }
}
