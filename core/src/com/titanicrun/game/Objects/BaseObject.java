package com.titanicrun.game.Objects;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.titanicrun.game.Screens.GameScreen;

/**
 * Created by Никита on 28.01.2016.
 */
public abstract class BaseObject {
    protected GameScreen gameScreen;
    public com.titanicrun.game.Objects.PlayObjects.Animation animation;
    public Vector2 position;
    protected float angle;
    private Rectangle bound;
    public BaseObject() {    }
    public BaseObject(GameScreen gameScreen, com.titanicrun.game.Objects.PlayObjects.Animation animation, Vector2 position){
        this.gameScreen = gameScreen;
        this.animation = animation;
        this.position = position;
        this.angle = 0;
        this.bound = new Rectangle();
    }
    public BaseObject(com.titanicrun.game.Objects.PlayObjects.Animation animation, Vector2 position){
        this.animation = animation;
        this.position = position;
        this.angle = 0;
        this.bound = new Rectangle();
    }
    public Rectangle getBound() {
        bound.set(position.x, position.y, animation.getTexture().getWidth(), animation.getTexture().getHeight());
        return bound;
    }
    public abstract void update();
    public abstract void render(SpriteBatch spriteBatch);
}
