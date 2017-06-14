package com.titanicrun.game.Objects.SystemObjects;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by geniu on 24.04.2016.
 */
public class Balance {
    private static int balance;
    public Balance(int money) {
        balance = money;
    }

    public void setBalance(int savedBalance){
        balance = savedBalance;
    }

    public void addPoint(int money) {
        balance+=money;
    }
    public int getBalance() {
        return balance;
    }

    public void Buy(int coast){
        balance -=coast;
        if(balance < 0)
            balance = 0;
    }

}
