package com.titanicrun.game.Objects.SystemObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Preferences;
import com.titanicrun.game.Objects.SystemObjects.Button;
import com.titanicrun.game.Screens.GameScreenManager;
import com.titanicrun.game.TitanicClass;

import java.util.ArrayList;

/**
 * Created by Никита on 01.03.2016.
 */
public class Putter {
    public ArrayList<com.titanicrun.game.Objects.SystemObjects.Button> skins, baseSkins;
    public Rectangle rectangle;
    private Preferences sittings;
    private int curr;

    public Putter(Rectangle rectangle, ArrayList<Button> skins) {
        baseSkins = new ArrayList<Button>();
        for (Button b: skins) {
            this.baseSkins.add(b);
        }
        Load(rectangle, skins);
    }
    private void Load(Rectangle rectangle, ArrayList<Button> skins) {
        this.skins = skins;
        this.sittings = Gdx.app.getPreferences("Animation");
        this.curr = sittings.getInteger("Animation");
        this.rectangle = rectangle;
        int n = (int)rectangle.getWidth()/(skins.get(0).animation.getTexture().getWidth() + (int)rectangle.getX());
        if(n > skins.size())
            n = skins.size();
        int k = skins.size()/n;
        int s = 0;
        for(int i = 1; i <= k; i++) {
            for(int j = 0; j < n; j++) {
                skins.get(s).position = new Vector2(rectangle.getX() + j * (skins.get(0).animation.getTexture().getWidth() + 20),
                        rectangle.getWidth() - i * (skins.get(0).animation.getTexture().getHeight() + 20)-35);
                s++;
            }
        }
        if(skins.size() % n != 0 && s!= 0) {
            int y = (int) skins.get(s - 1).position.y - skins.get(0).animation.getTexture().getHeight() - 20;
            for (int i = 0; i < skins.size() % n; i++) {
                skins.get(s).position = new Vector2(rectangle.getX() + i * (skins.get(0).animation.getTexture().getWidth() + 20), y);
                s++;
            }
        }
    }
    public void update() {
        for(int i = 0; i < skins.size(); i++) {
            skins.get(i).update();
            if(skins.get(i).isPressed() && (TitanicClass.getMouse().getY() <= TitanicClass.ScreenHeight/2) ) {
                curr = i;
            }
        }
    }

    public void render(SpriteBatch spriteBatch) {
        for(int i = 0; i < skins.size(); i++) {
            skins.get(i).render(spriteBatch);
        }
    }
    public void reset() {
        skins = new ArrayList<Button>();
        for(Button b : baseSkins) {
            skins.add(b);
        }
        Load(rectangle, skins);
    }
    public int getAnimation(){
        return curr;
    }
}