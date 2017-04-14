package com.titanicrun.game.Screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

/**
 * Created by Никита on 28.01.2016.
 */
public class GameScreenManager {
    private Stack<Screen> screens;

    public GameScreenManager() {
        screens = new Stack<Screen>();
    }

    public void addScreen(Screen screen) {
        screens.push(screen);
    }
    public void deleteScreen() {
        screens.pop();
    }
    public void setScreen(Screen screen) {
        deleteScreen();
        addScreen(screen);
    }

    public void update() {
        screens.peek().update();
    }
    public void render(SpriteBatch spriteBatch) {
        screens.peek().render(spriteBatch);
    }
}
