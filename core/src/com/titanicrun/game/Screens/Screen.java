package com.titanicrun.game.Screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.titanicrun.game.Objects.PlayObjects.Animation;

/**
 * Created by Никита on 28.01.2016.
 */
public abstract class Screen {
    public String name;
    protected OrthographicCamera camera;
    protected GameScreenManager gameScreenManager;

    public Screen(GameScreenManager gameScreenManager, String name) {
        this.gameScreenManager = gameScreenManager;
        this.name = name;
        camera = new OrthographicCamera();
    }
    public abstract void update();
    public abstract void render(SpriteBatch spriteBatch);
    public abstract void reset();
    public static Animation anim(String s) {
        return new Animation(new Texture[] {new Texture(s)}, 1);
    }
    public static Animation anim(Texture t) {
        return new Animation(new Texture[]{t}, 1);
    }
}
