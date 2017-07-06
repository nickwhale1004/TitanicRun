package com.titanicrun.game.Objects.SystemObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.titanicrun.game.TitanicClass;

import java.text.DecimalFormat;

/**
 * Created by Никита on 24.06.2017.
 */

public class TouchPanel {
    private int time;
    public int isPressed; //-1-unknow, 0-no, 1-yes
    private Vector2 prevTouchPos;
    private float speed;
    private Rectangle lastTouchPos;
    private float deltaY;
    public Putter items;
    private Rectangle touchPlace;
    public TouchPanel(Putter items) {
        this.items = items;
        this.touchPlace = new Rectangle(20,20, 420, 425);
        this.time = 0;
        this.isPressed = 0;
        this.speed = 0;
    }
    public void update() {
        updateTouch();
        if (isPressed == 1 /*&& TitanicClass.getMouse().overlaps(touchPlace)*/) {
            if (prevTouchPos != null) {
                deltaY = TitanicClass.getMouse().getY() - prevTouchPos.y;
                for (Button x : items.skins) {
                    x.position.y += deltaY;
                }
            }
            prevTouchPos = new Vector2(TitanicClass.getMouse().getX(), TitanicClass.getMouse().getY());
        }
        else if(isPressed == 0) {
            if(prevTouchPos != null)
                prevTouchPos = null;
        }
        if(isPressed == 0) {
            if(speed != 0)
                speed/=1.1;
            if(lastTouchPos!= null) {
                items.skins.get(0).simulateTouch = lastTouchPos;
            }
            items.update();
            items.skins.get(0).simulateTouch = null;
            lastTouchPos = null;
            if(speed != 0) {
                for (Button x : items.skins) {
                    x.position.y += speed;
                }
            }
        }
        while (items.skins.get(0).position.y < touchPlace.getY()+2)
            for (Button x : items.skins) {
                x.position.y+=0.1f;
            }
        while (items.skins.get(items.skins.size()-1).position.y > touchPlace.getY()+touchPlace.getHeight()-120)
            for (Button x : items.skins) {
                x.position.y-=0.1f;
            }
    }
    public void render(SpriteBatch spriteBatch) {
        items.render(spriteBatch);
    }
    public void reset() {
        items.reset();
        prevTouchPos =  null;
        time = 0;
        isPressed = 0;
        speed = 0;
        lastTouchPos = null;
    }
    public void updateTouch() {
        if (isPressed == 0) {
            if (Gdx.input.isTouched()) {
                if (TitanicClass.getMouse().overlaps(touchPlace)) {
                    speed = 0;
                    lastTouchPos = TitanicClass.getMouse();
                    isPressed = -1;
                }
            }

        }
        if (isPressed == -1) {
            time++;
            if (!Gdx.input.isTouched()) {
                isPressed = 0;
                time = 0;
            } else {
                if (lastTouchPos != null && lastTouchPos.y != TitanicClass.getMouse().getY()) {
                    time = 0;
                    isPressed = 1;
                } else
                    lastTouchPos = TitanicClass.getMouse();
            }
            if (time >= 8) {
                time = 0;
                isPressed = 1;
            }
        }
        if (isPressed == 1) {
            if (!Gdx.input.isTouched()) {
                speed += TitanicClass.getMouse().getY() - prevTouchPos.y;
                speed *= 1.1;
                lastTouchPos = null;
                isPressed = 0;
            }
        }
    }
}
