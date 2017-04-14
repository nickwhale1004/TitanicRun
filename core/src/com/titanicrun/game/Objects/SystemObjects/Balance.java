package com.titanicrun.game.Objects.SystemObjects;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by geniu on 24.04.2016.
 */
public class Balance extends com.titanicrun.game.Objects.SystemObjects.TextDraw {
    private static int balance;
    public Balance(int money, Vector2 position) {
        super(position, "" + money);
    }

    public void setBalance(int savedBalance){
        balance = savedBalance;
        text = balance+"";
    }

    public void addPoint(int money) {
        balance+=money;
        text = ""+balance;
    }
    public int getBalance() {
        text = ""+balance;
        return balance;
    }

    public void Buy(int coast){
        balance -=coast;
        if(balance < 0)
            balance = 0;
        text = ""+balance;
    }

}
