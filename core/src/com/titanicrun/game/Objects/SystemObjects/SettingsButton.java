package com.titanicrun.game.Objects.SystemObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.titanicrun.game.Objects.BaseObject;
import com.titanicrun.game.Objects.PlayObjects.Animation;
import com.titanicrun.game.Objects.PlayObjects.SizeChangeObject;
import com.titanicrun.game.TitanicClass;

/**
 * Created by Никита on 17.04.2017.
 */

public class SettingsButton extends BaseObject {
    private int process; //0-nothing 1-biger and rotate, 2-smaller and rotate
    private Animation current;
    private boolean wasPressed;
    private Rectangle mouse = new Rectangle();
    private boolean pressed;
    private float angle;
    private float rotateSpeed;
    private float size;
    Sprite sprite;
    public SettingsButton(Animation animation, Vector2 position){
        super(animation, position);
        this.process = 0;
        this.size=100;
        this.sprite = new Sprite(animation.getTexture());
        this.rotateSpeed = 0;
        this.current = animation;
    }
    @Override
    public void update() {
        position.x = TitanicClass.ScreenWidth-43-(animation.getTexture().getWidth()/100f)*size/2f;
        position.y = TitanicClass.ScreenHeight-43-(animation.getTexture().getHeight()/100f)*size/2f;
        angle+=rotateSpeed;
        sprite.setOrigin(((animation.getTexture().getWidth()/100f)*size)/2f,
                ((animation.getTexture().getHeight()/100f)*size)/2f);
        sprite.setTexture(animation.getTexture());
        sprite.setPosition(position.x, position.y);
        sprite.setSize((animation.getTexture().getWidth()/100f)*size, (animation.getTexture().getHeight()/100f)*size);
        sprite.setRotation(angle);
        if(process == 0) {
            current.update();
            if (Gdx.input.isTouched()) {
                mouse = TitanicClass.getMouse();
                if (mouse.overlaps(getBound())) {
                    wasPressed = true;
                } else {
                    current = animation;
                }
            } else if (wasPressed) {
                process = 1;
                rotateSpeed = 10;
                current = animation;
                wasPressed = false;
            } else {
                pressed = false;
            }
        }
        else if (process == 1) {
            if(rotateSpeed > 0) {
                rotateSpeed -= 0.13f;
            }
            size+=1.3;
            if(size >= 130) {
                size=130;
                process = 2;
            }
        }
        else if (process == 2) {
            if(rotateSpeed > 0) {
                rotateSpeed -= 0.13f;
            }

            if(size <= 100) {
                size=100;
                if(rotateSpeed <= 0) {
                    pressed = true;
                    process = 0;
                }
            }
            else {
                size-=1.3;
            }
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        sprite.draw(spriteBatch);
    }
    public boolean isPressed() {
        return pressed;
    }
    @Override
    public Rectangle getBound() {
         bound.set(position.x, position.y,
                    (current.getTexture().getWidth() / 100f) * size,
                    (current.getTexture().getHeight() / 100f) * size);

        return  bound;
    }
    public void reset() {
        wasPressed = false;
        pressed = false;
    }
}
