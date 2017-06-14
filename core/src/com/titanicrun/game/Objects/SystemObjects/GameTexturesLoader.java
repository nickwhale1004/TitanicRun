package com.titanicrun.game.Objects.SystemObjects;

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
    static Map<String,Animation> dictionary ;
    public GameTexturesLoader() {
        dictionary = new HashMap<String, Animation>();
        dictionary.put("backs/deathBack.png", anim("backs/deathBack.png"));
        dictionary.put("numbers/gameover.png", anim("numbers/gameover.png"));
        dictionary.put("numbers/of.png", anim("numbers/of.png"));
        dictionary.put("buttons/menu.png", anim("buttons/menu.png"));
        dictionary.put("buttons/menuTuched.png", anim("buttons/menuTuched.png"));
        dictionary.put("buttons/play.png", anim("buttons/play.png"));
        dictionary.put("backs/night.png", anim("backs/night.png"));
        dictionary.put("buttons/playTuched.png", anim("buttons/playTuched.png"));
        dictionary.put("buttons/skin.png", anim("buttons/skin.png"));
        dictionary.put("buttons/skinTuched.png", anim("buttons/skinTuched.png"));
        dictionary.put("buttons/settings.png", anim("buttons/settings.png"));
        dictionary.put("menu/waterMenu.png", anim("menu/waterMenu.png"));
        dictionary.put("menu/waterMenu2.png", anim("menu/waterMenu2.png"));
        dictionary.put("menu/waterMenu3.png", anim("menu/waterMenu3.png"));
        dictionary.put("menu/ship.png", anim("menu/ship.png"));
        dictionary.put("backs/runner.png", anim("backs/runner.png"));
        /////
        dictionary.put("backs/shadow.png", anim("backs/shadow.png"));
        dictionary.put("backs/night.png", anim("backs/night.png"));
        dictionary.put("backs/pauseLine.png", anim("backs/pauseLine.png"));
        for(int i = 1; i <= 9; i++) {
            dictionary.put("player"+i, anim("players/"+i+"player.png"));
            dictionary.put("player2"+i,anim("players/"+i+"player2.png"));
            dictionary.put("player3"+i,anim("players/"+i+"player3.png"));
            dictionary.put("playerFront"+i, anim("players/"+i+"playerFront.png"));
            dictionary.put("playerPreview"+i, anim("players/"+i+"playerPreview.png"));
        }
        dictionary.put("object.png", anim("object.png"));
        for(int i = 1; i<=2; i++) {
            dictionary.put("backs/"+i+"backUsuall.png", anim("backs/"+i+"backUsuall.png"));
            dictionary.put("backs/"+i+"backPrev.png", anim("backs/"+i+"backPrev.png"));
        }
        dictionary.put("water.png", anim("water.png"));
        dictionary.put("water2.png", anim("water2.png"));
        dictionary.put("water3.png", anim("water3.png"));
        for(int i = 1; i < 8; i++) {
            dictionary.put("fallObj/fall"+i+".png", anim("fallObj/fall"+i+".png"));
        }
        dictionary.put("fallObj/fallMan1.png", anim("fallObj/fallMan1.png"));
        //////
        dictionary.put("sllBack.png", anim("sllBack.png"));
        dictionary.put("backs/skin.png", anim("backs/skin.png"));
        dictionary.put("backs/skinUp.png", anim("backs/skinUp.png"));
        dictionary.put("buttons/select.png", anim("buttons/select.png"));
        dictionary.put("buttons/selectTuched.png", anim("buttons/selectTuched.png"));
        dictionary.put("buttons/menuSmall.png", anim("buttons/menuSmall.png"));
        dictionary.put("buttons/menuSmallTuched.png", anim("buttons/menuSmallTuched.png"));
        dictionary.put("buttons/buy.png", anim("buttons/buy.png"));
        dictionary.put("buttons/buyTuched.png", anim("buttons/buyTuched.png"));
        for(int i = 1; i <= 9; i++) {
            dictionary.put("players/"+i+"playerSkin.png", anim("players/"+i+"playerSkin.png"));
        }
        dictionary.put("sll.png", anim("sll.png"));
        dictionary.put("players/unknow.png", anim("players/unknow.png"));
        dictionary.put("selected.png", anim("selected.png"));
        dictionary.put("preview.png", anim("preview.png"));
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
