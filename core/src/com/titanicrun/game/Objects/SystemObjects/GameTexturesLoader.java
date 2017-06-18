package com.titanicrun.game.Objects.SystemObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.titanicrun.game.Objects.PlayObjects.Animation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Никита on 22.04.2017.
 */

public class GameTexturesLoader {
    static ArrayList<String> dictionaryString;
    static Map<String,Animation> dictionary ;
    static int n = 0;
    static int index = 0;
    public GameTexturesLoader() {
        dictionaryString = new ArrayList<String>();
        dictionary = new HashMap<String, Animation>();
        dictionaryString.add("backs/deathBack.png");
        dictionaryString.add("numbers/gameover.png");
        dictionaryString.add("shark.png");
        dictionaryString.add("sharkReverse.png");
        dictionaryString.add("numbers/of.png");
        dictionaryString.add("buttons/menu.png");
        dictionaryString.add("buttons/menuTuched.png");
        dictionaryString.add("buttons/play.png");
        dictionaryString.add("backs/night.png");
        dictionaryString.add("buttons/playTuched.png");
        dictionaryString.add("buttons/skin.png");
        dictionaryString.add("buttons/skinTuched.png");
        dictionaryString.add("buttons/settings.png");
        dictionaryString.add("menu/waterMenu.png");
        dictionaryString.add("menu/waterMenu2.png");
        dictionaryString.add("menu/waterMenu3.png");
        dictionaryString.add("menu/ship.png");
        dictionaryString.add("backs/runner.png");
        /////
        dictionaryString.add("backs/shadow.png");
        dictionaryString.add("backs/night.png");
        dictionaryString.add("backs/pauseLine.png");
        for (int i = 1; i <= 9; i++) {
            dictionaryString.add("players/" + i + "player.png");
            dictionaryString.add("players/" + i + "player2.png");
            dictionaryString.add("players/" + i + "player3.png");
            dictionaryString.add("players/" + i + "playerFront.png");
            dictionaryString.add("players/" + i + "playerPreview.png");
        }
        dictionaryString.add("object.png");
        for (int i = 1; i <= 9; i++) {
            dictionaryString.add("backs/" + i + "backUsuall.png");
            dictionaryString.add("backs/" + i + "backPreview.png");
        }
        //8
        dictionaryString.add("water.png");
        dictionaryString.add("water2.png");
        dictionaryString.add("water3.png");
        for (int i = 1; i < 8; i++) {
            dictionaryString.add("fallObj/fall" + i + ".png");
        }
        dictionaryString.add("fallObj/fallMan1.png");
        //////
        dictionaryString.add("sllBack.png");
        dictionaryString.add("backs/skin.png");
        dictionaryString.add("backs/skinUp.png");
        dictionaryString.add("buttons/select.png");
        dictionaryString.add("buttons/selectTuched.png");
        dictionaryString.add("buttons/menuSmall.png");
        dictionaryString.add("buttons/menuSmallTuched.png");
        dictionaryString.add("buttons/buy.png");
        dictionaryString.add("buttons/buyTuched.png");
        for (int i = 1; i <= 9; i++) {
            dictionaryString.add("players/" + i + "playerSkin.png");
        }
        dictionaryString.add("sll.png");
        dictionaryString.add("players/unknow.png");
        dictionaryString.add("selected.png");
        dictionaryString.add("preview.png");
        dictionaryString.add("splashes/continue.png");
        dictionaryString.add("splashes/1back.png");
        dictionaryString.add("splashes/1char1.png");
        dictionaryString.add("splashes/1char2.png");
        dictionaryString.add("splashes/2.png");
        dictionaryString.add("splashes/32.png");
        dictionaryString.add("splashes/4back.png");
        dictionaryString.add("splashes/4char.png");
        dictionaryString.add("splashes/4man.png");
        dictionaryString.add("splashes/4pic1.png");
        dictionaryString.add("splashes/4pic2.png");
        dictionaryString.add("splashes/touchtoplay.png");
        dictionaryString.add("splashes/touchtoplay2.png");
        dictionaryString.add("splashes/pressscreen.png");
        dictionaryString.add("splashes/goodluck.png");
        dictionaryString.add("backs/pauseField.png");
        dictionaryString.add("splashes/presspause.png");
        dictionaryString.add("splashes/catchit.png");
        dictionaryString.add("numbers/pause.png");
        dictionaryString.add("numbers/big3.png");
        dictionaryString.add("numbers/big2.png");
        dictionaryString.add("numbers/big1.png");
        dictionaryString.add("backs/pause.png");
        dictionaryString.add("numbers/run.png");
        dictionaryString.add("buttons/sounds1.png");
        dictionaryString.add("backs/background.png");
        dictionaryString.add("backs/runner.png");
        dictionaryString.add("buttons/historyButton.png");
        dictionaryString.add("buttons/sounds1.png");
        dictionaryString.add("buttons/sounds3.png");
        dictionaryString.add("buttons/sounds2.png");
        dictionaryString.add("buttons/sounds4.png");
        dictionaryString.add("splashes/1backPrev.png");
        n = dictionaryString.size() / 11;
    }
    public void update() {
        for(int i = 0; i < n; i++) {
            dictionary.put(dictionaryString.get(index), anim(dictionaryString.get(index)));
            index++;
        }
    }
    public void specialUpdate() {
        while(index < dictionaryString.size()) {
            dictionary.put(dictionaryString.get(index), anim(dictionaryString.get(index)));
            index++;
        }
    }
    public static Animation get(String s) {
       return dictionary.get(s);
    }
    public static Animation anim(Texture t) {
        return new Animation(new Texture[]{t}, 1);
    }
    public static Animation anim(String s) {
        return new Animation(new Texture[] {new Texture(s)}, 1);
    }
}