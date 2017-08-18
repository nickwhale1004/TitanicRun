package com.titanicrun.game.Objects.PlayObjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.titanicrun.game.Objects.BaseObject;
import com.titanicrun.game.Screens.GameScreen;

/**
 * Created by Никита on 04.02.2016.
 */
public class Water extends BaseObject {
    private byte process; //0 - down, 1- up
    private float speedDown, speedUp;
    private float fromY, toY;
    public Water(GameScreen gameScreen, Animation animation, Vector2 position, float toY, float speedDown, float speedUp) {
        super(gameScreen, animation, position);
        this.process = 0;
        this.speedDown = speedDown;
        this.speedUp = speedUp;
        this.fromY = (int)position.y;
        this.toY = toY;
    }

    @Override
    public void update() {
        animation.update();
        if(process == 0) {
            position.y-=speedDown;
            if(position.y <= fromY) {
                process = 1;
            }
        }
        else if(process == 1) {
            position.y+=speedUp;
            if(position.y >= toY) {
                process = 0;
            }
        }
        if(gameScreen!= null && gameScreen.player.getBound().overlaps(getBound())) {
            gameScreen.player.Die();
            gameScreen.Die();
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(animation.getTexture(),position.x,position.y);
    }

    @Override
    public void reset() {

    }
}
