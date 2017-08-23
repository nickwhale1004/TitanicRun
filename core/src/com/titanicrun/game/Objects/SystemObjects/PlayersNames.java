package com.titanicrun.game.Objects.SystemObjects;

import java.util.ArrayList;

/**
 * Created by Никита on 18.08.2017.
 */

public class PlayersNames {
    static ArrayList<String> names;
    public PlayersNames() {
        names = new ArrayList<String>();
        names.add("JACK");
        names.add("CHAN");
        names.add("STEVEN");
        names.add("NONAME");
        names.add("SHADY");
        names.add("XTEN");
        names.add("CAP");
        names.add("ADAM");
        names.add("VERA");
    }
    public static String getPlayerName(int index) {
        return names.get(index);
    }
}
