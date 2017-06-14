package com.titanicrun.game.Objects.PlayObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.titanicrun.game.Objects.BaseObject;
import com.titanicrun.game.Screens.GameScreen;
import com.titanicrun.game.TitanicClass;


/**
 * Created by Никита on 28.01.2016.
 */
public class Player extends BaseObject {
    private Vector2 basePos;
    private Animation dieAnim, notDie;
    private float speed, limit;
    private boolean toDie, toDown;
    public Player(GameScreen gameScreen, Animation animation, Animation dieAnim) {
        super(gameScreen, animation, new Vector2(
                TitanicClass.ScreenWidth / 2 - animation.getTexture().getWidth() / 2,
                TitanicClass.ScreenHeight));
        this.speed = 6f;
        this.basePos = new Vector2(position.x, position.y);
        this.notDie = animation;
        this.limit = speed*1.8f;
        this.dieAnim = dieAnim;
        this.toDie = false;
        this.toDown = true;
    }
    @Override
    public void update() {
        animation.update();
        if(toDown) {
            position.y-=5;
            if(position.y <= TitanicClass.ScreenHeight - animation.getTexture().getHeight())
                toDown = false;
        }
        else if(!toDie) {
            speed++;
            if (speed >= limit)
                speed = limit;
            if (Gdx.input.isTouched() || Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                position.y += 15;
                speed = 1f;
            }
            position.y -= speed;
            if (position.y <= 0) {
                position.y = 0;
                speed = 1f;
            } else if (position.y + animation.getTexture().getHeight() >= TitanicClass.ScreenHeight) {
                position.y = TitanicClass.ScreenHeight - animation.getTexture().getHeight();
            }
        }
        else {
            position.y-=5;
        }
    }
    public void Die(){
        toDie = true;
        animation = dieAnim;
    }
    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(animation.getTexture(),position.x,position.y);
    }
    public void reset() {
        toDown = true;
        toDie = false;
        animation = notDie;
        position.y = basePos.y;
    }
}
