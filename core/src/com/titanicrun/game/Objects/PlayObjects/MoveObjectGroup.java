package com.titanicrun.game.Objects.PlayObjects;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Created by Никита on 16.06.2017.
 */

public class MoveObjectGroup {
    ArrayList<MoveObject> objects;
    public MoveObjectGroup() {
        objects = new ArrayList<MoveObject>();
    }
    public void add(MoveObject obj) {
        objects.add(obj);
    }
    public void moveTo(Vector2 toPosition, int speed) {
        for(int i = 0; i < objects.size(); i++) {
            objects.get(i).change(toPosition);
            objects.get(i).speed.x = speed;
            objects.get(i).speed.y = speed;
        }
    }
    public void moveOn(Vector2 onPosition, int speed) {
        for(int i = 0; i < objects.size(); i++) {
            objects.get(i).change(new Vector2(objects.get(i).position.x+onPosition.x,
                    objects.get(i).position.y+onPosition.y));
            objects.get(i).speed.x = speed;
            objects.get(i).speed.y = speed;
        }
    }
}
