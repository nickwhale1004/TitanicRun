package com.titanicrun.game.Objects.SystemObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.titanicrun.game.Objects.BaseObject;
import com.titanicrun.game.Objects.PlayObjects.Animation;
import com.titanicrun.game.Objects.PlayObjects.SizeChangeObject;
import com.titanicrun.game.TitanicClass;

/**
 * Created by Никита on 16.04.2017.
 */

public class IncreasingButton extends BaseObject {
    private int process; //0- normal, 11 - smaler, 1- bigger, 2- big, 3- normaler
    private SizeChangeObject obj;
    private Animation baseAnimation;
    private Vector2 newPosition;
    private Animation tuched, current;
    private boolean wasPressed;
    private Rectangle mouse = new Rectangle();
    private Rectangle myBound;
    private boolean pressed;

    public IncreasingButton(Animation animation, Animation tuched, Vector2 position) {
        super(animation, new Vector2(position.x, position.y));
        this.tuched = tuched;
        this.baseAnimation= new Animation(animation.getTextures(),1);
        this.newPosition = new Vector2(position.x, position.y);
        this.process = 0;
        this.current = animation;
        this.obj = new SizeChangeObject(newPosition, current, 100);
        this.myBound = new Rectangle();
        this.wasPressed = false;
        this.pressed = false;
    }

    @Override
    public void update() {
        obj.update();
        newPosition.x = position.x+
                (baseAnimation.getTexture().getWidth()-(current.getTexture().getWidth()/100f)*obj.size)/2f;
        newPosition.y = position.y+
                (baseAnimation.getTexture().getHeight()-(current.getTexture().getHeight()/100f)*obj.size)/2f;
        Gdx.app.log("im here", obj.size+"");

        if (process == 0) {
            current.update();
            if (Gdx.input.justTouched()) {
                mouse = TitanicClass.getMouse();
                if (mouse.overlaps(getBound())) {
                    obj.changeTo(80, 3);
                    process = 11;
                    current = tuched;
                    wasPressed = true;
                }
            } else if (wasPressed) {
                wasPressed = false;
            } else {
                pressed = false;
            }
        }
        else if(process == 11){
            if(obj.end) {
                obj.changeTo(120,2);
                process = 1;
                current = animation;
            }
        } else if (process == 1) {
            if (obj.end) {
                process = 2;
            }
        } else if (process == 2) {
            if (Gdx.input.isTouched()) {
                mouse = TitanicClass.getMouse();
                if(!mouse.overlaps(getBound())) {
                    wasPressed = true;
                    process = 3;
                    obj.changeTo(100, 3);
                }
            } else if (wasPressed) {
                wasPressed = false;
                process = 3;
                obj.changeTo(100, 3);
            }
        } else if (process == 3) {
            if (obj.end) {
                if(!wasPressed)
                    pressed = true;
                else
                    wasPressed = false;
                process = 0;
            }
        }
    }

    public boolean isPressed() {
        return pressed;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        obj.animation = current;
        obj.render(spriteBatch);
    }
    @Override
    public Rectangle getBound() {
        myBound.set(newPosition.x, newPosition.y,
                (current.getTexture().getWidth() / 100f) * obj.size,
                (current.getTexture().getHeight() / 100f) * obj.size);
        return  myBound;
    }
}
