package com.titanicrun.game.Objects.SystemObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.titanicrun.game.Objects.PlayObjects.Animation;
import com.titanicrun.game.Objects.BaseObject;
import com.titanicrun.game.TitanicClass;

/**
 * Created by Никита on 09.04.2016.
 */
public class Scroller extends BaseObject {
    public Putter items;
    private Vector2 touchPosition;
    private int upDownLimit;
    private int upPos, downPos;
    public Scroller(Putter items, Animation animation, int upDownLimit) {
        super(animation, new Vector2(TitanicClass.ScreenWidth-animation.getTexture().getWidth()-5, 0));
        this.items = items;
        this.upDownLimit = upDownLimit;
        this.upPos = (int)items.skins.get(0).position.y;
        this.downPos = (int)items.rectangle.getY() - (int)items.rectangle.getHeight() + 20;
        this.touchPosition = null;
    }

    @Override
    public void update() {
        animation.update();
        if (touchPosition == null)
            items.update();
        if(Gdx.input.isTouched()) {
            if(TitanicClass.getMouse().overlaps(getBound()) || touchPosition != null) {
                int prevScrollPosition =  animation.getTexture().getHeight()/2;
                position.y = TitanicClass.getMouse().getY() - prevScrollPosition;
                Vector2 touch = new Vector2(TitanicClass.getMouse().getX(), TitanicClass.getMouse().getY());
                if (touchPosition == null) {
                    touchPosition = touch;
                }
                else {
                    if (touch.y > prevScrollPosition &&
                            items.skins.get(items.skins.size()-1).position.y < upPos) {
                        for (int i = 0; i < items.skins.size(); i++) {
                            float speed = touch.y - prevScrollPosition;
                            speed/=20;
                            if(speed >= 10)
                                speed = 10;
                            items.skins.get(i).position.y += speed;
                        }
                    }
                    else if (touch.y < prevScrollPosition &&
                            items.skins.get(0).position.y > downPos) {
                        for (int i = 0; i < items.skins.size(); i++) {
                            float speed = prevScrollPosition - touch.y;
                            speed/=20;
                            if(speed >= 10)
                                speed = 10;
                            items.skins.get(i).position.y -= speed;
                        }
                    }
                }
            }
        }
        else
        {
            touchPosition = null;
            position.y = upDownLimit;
        }

        if(position.y + animation.getTexture().getHeight()/2 >= items.rectangle.getHeight() - 50 + upDownLimit)
            position.y = items.rectangle.getHeight() - 50 + upDownLimit - animation.getTexture().getHeight()/2;
        else if(position.y + animation.getTexture().getHeight()/2 <= items.rectangle.getY() - items.rectangle.getHeight()+ 50+ upDownLimit)
            position.y = 50 + upDownLimit -animation.getTexture().getHeight()/2;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(animation.getTexture(),position.x,position.y);
    }
}
