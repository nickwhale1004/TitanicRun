package com.titanicrun.game.Objects.PlayObjects;

import com.badlogic.gdx.math.Vector2;
import com.titanicrun.game.TitanicClass;

/**
 * Created by Никита on 29.01.2016.
 */
public class Score extends com.titanicrun.game.Objects.SystemObjects.TextDraw {
    private int score;
    public Score() {
        super(new Vector2(5, TitanicClass.ScreenHeight-5-TitanicClass.scoreABC[0].getHeight()-5), "0");
        this.score = 0;
    }
    public void addPoint() {
        score++;
        text = ""+score;
    }
    public int getScore() {
        return score;
    }
}
