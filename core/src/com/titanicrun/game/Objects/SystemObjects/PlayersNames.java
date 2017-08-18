package com.titanicrun.game.Objects.SystemObjects;

import java.util.ArrayList;

/**
 * Created by Никита on 18.08.2017.
 */

public class PlayersNames {
    ArrayList<String> names;
    public PlayersNames() {
        names = new ArrayList<String>();
        names.add("Jack1");
        names.add("Chan2");
        names.add("Steven3");
        names.add("Noname4");
        names.add("Shady5");
        names.add("Ten6");
        names.add("Cap7");
        names.add("Adam8");
        names.add("Vera9");
    }
    public String getPlayerName(int index) {
        return names.get(index);
    }
}
