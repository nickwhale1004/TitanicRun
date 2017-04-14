package com.titanicrun.game.Objects.SystemObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.titanicrun.game.Objects.PlayObjects.Animation;
import com.titanicrun.game.Objects.BaseObject;
import com.titanicrun.game.TitanicClass;

/**
 * Created by Никита on 03.02.2016.
 */
public class Button extends BaseObject {
    private Animation tuched, current;
    private boolean wasPressed;
    private Rectangle mouse = new Rectangle();
    private boolean pressed;

    public Button(Animation animation, Animation tuched, Vector2 position) {
        super(animation, position);
        this.tuched = tuched;
        this.current = animation;
        this.wasPressed = false;
        this.pressed = false;
    }


    @Override
    public void update() {
        current.update();
        if(Gdx.input.isTouched()) {
            mouse = TitanicClass.getMouse();
            if(mouse.overlaps(getBound())) {
                current = tuched;
                wasPressed = true;
            }
            else {
                    current = animation;
            }
        }
        else if (wasPressed) {
            pressed = true;
            current = animation;
            wasPressed = false;
        }
        else {
            pressed = false;
        }
    }
    public boolean isPressed() {
        return pressed;
    }
    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(current.getTexture(),position.x,position.y);
    }
}
