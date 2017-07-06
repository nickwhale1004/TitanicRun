package com.titanicrun.game.Objects.SystemObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.titanicrun.game.Objects.PlayObjects.Animation;
import com.titanicrun.game.Objects.BaseObject;
import com.titanicrun.game.Objects.PlayObjects.SizeChangeObject;
import com.titanicrun.game.TitanicClass;

/**
 * Created by Никита on 03.02.2016.
 */
public class Button extends BaseObject {
    private int type;
    private int process; //0- normal, 11 - smaler, 1- bigger, 2- big, 3- normaler
    private SizeChangeObject obj;
    public static Rectangle simulateTouch;
    private Animation baseAnimation;
    private Vector2 newPosition;
    private Animation tuched, current;
    private boolean wasPressed;
    private Rectangle mouse = new Rectangle();
    private Rectangle myBound;
    private boolean pressed;
    private boolean musicPlayed;

    public Button(Animation animation, Animation tuched, Vector2 position, int type) {
        super(animation, position);
        this.type = type;
        this.musicPlayed = false;
        this.myBound = new Rectangle();
        if(type == 1) {
            this.tuched = tuched;
            this.current = animation;
            this.wasPressed = false;
            this.pressed = false;
        }
        else {
            this.tuched = tuched;
            this.baseAnimation= new Animation(animation.getTextures(),1);
            this.newPosition = new Vector2(position.x, position.y);
            this.process = 0;
            this.current = animation;
            this.obj = new SizeChangeObject(newPosition, current, 100);
            this.wasPressed = false;
            this.pressed = false;
        }
    }


    @Override
    public void update() {
        if(type == 1) {
            current.update();
            if (Gdx.input.isTouched() || simulateTouch != null) {
                mouse = TitanicClass.getMouse();
                if(simulateTouch != null) {
                    mouse = simulateTouch;
                }
                if (mouse.overlaps(getBound())) {
                    current = tuched;
                    wasPressed = true;
                } else {
                    current = animation;
                }
            } else if (wasPressed) {
                pressed = true;
                current = animation;
                wasPressed = false;
            } else {
                pressed = false;
            }
        }
        else {
            obj.update();
            newPosition.x = position.x+
                    (baseAnimation.getTexture().getWidth()-(current.getTexture().getWidth()/100f)*obj.size)/2f;
            newPosition.y = position.y+
                    (baseAnimation.getTexture().getHeight()-(current.getTexture().getHeight()/100f)*obj.size)/2f;


            if (process == 0) {
                current.update();
                if (Gdx.input.isTouched()) {
                    mouse = TitanicClass.getMouse();
                    if (mouse.overlaps(getBound())) {
                        obj.changeTo(80, 2);
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
                    obj.changeTo(100,2);
                    current = animation;
                    process = 12;
                }
            }  else if(process == 12) {
                if (obj.end) {
                    obj.changeTo(120, 3);
                    process = 1;
                }
            } else if (process == 1) {
                if (obj.end) {
                    current = animation;
                    process = 2;
                }
            } else if (process == 2) {
                if (Gdx.input.isTouched()) {
                    mouse = TitanicClass.getMouse();
                    if(!mouse.overlaps(getBound())) {
                        wasPressed = true;
                        process = 3;
                        obj.changeTo(100, 2);
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
    }
    public boolean isPressed() {
        return pressed;
    }
    @Override
    public void render(SpriteBatch spriteBatch) {
        if (wasPressed && !musicPlayed) {
            TitanicClass.playBGM.playSound("Button");
            musicPlayed = true;
        }
        if (!wasPressed) {
            musicPlayed = false;
        }
        if(type == 1) {
            spriteBatch.draw(current.getTexture(), position.x, position.y);
        }
        else {
            obj.animation = current;
            obj.render(spriteBatch);
        }
    }
    @Override
    public Rectangle getBound() {
        if(type == 1) {
            myBound.set(position.x, position.y, animation.getTexture().getWidth(), animation.getTexture().getHeight());
        }
        else {
            myBound.set(newPosition.x, newPosition.y,
                    (current.getTexture().getWidth() / 100f) * obj.size,
                    (current.getTexture().getHeight() / 100f) * obj.size);
        }
        return  myBound;
    }
    public void reset() {
        this.obj.reset();
        this.wasPressed = false;
        this.musicPlayed = false;
        this.pressed = false;
    }
}
