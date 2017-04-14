package com.titanicrun.game.Objects.PlayObjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Никита on 28.01.2016.
 */
public abstract class Creator {
    protected Animation objAnimation;
    protected Animation[] objAnimations;
    protected float interval;
    protected int time;

    public Creator(Animation objAnimation, float interval) {
        this.objAnimation = objAnimation;
        this.interval = interval;
        this.time = 0;
    }
    public Creator(Animation[] objAnimations, float interval) {
        this.objAnimations = objAnimations;
        this.interval = interval;
        this.time = 0;
    }

    public abstract void update();
    public abstract void render(SpriteBatch spriteBatch);
}
