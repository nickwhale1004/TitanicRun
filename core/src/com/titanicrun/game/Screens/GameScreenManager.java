package com.titanicrun.game.Screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Stack;

/**
 * Created by Никита on 28.01.2016.
 */
public class GameScreenManager {
    private Map<String,Screen> screens;
    private Screen currenScreen;
    public GameScreenManager() {
        screens = new HashMap<String, Screen>();
        currenScreen = null;
    }

    public Screen getCurrenScreen() {
        return currenScreen;
    }

    public void addScreen(Screen screen) {
        screens.put(screen.name, screen);
        currenScreen = screen;
    }
    public void setScreen(String name) {
        currenScreen.reset();
        currenScreen = screens.get(name);
    }
    public void setNonResetScreen(String name) {
        currenScreen = screens.get(name);
    }
    public Screen getScreen(String name) {
        return screens.get(name);
    }
    public void removeScreen(String name) {
        if(currenScreen != screens.get(name) && screens.containsKey(name)) {
            screens.remove(name);
        }
    }
    public void update() {
        currenScreen.update();
    }
    public void render(SpriteBatch spriteBatch) {
        currenScreen.render(spriteBatch);
    }
}
