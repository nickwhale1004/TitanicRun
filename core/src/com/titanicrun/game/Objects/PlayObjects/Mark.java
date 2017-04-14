package com.titanicrun.game.Objects.PlayObjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.titanicrun.game.Objects.BaseObject;
import com.titanicrun.game.Objects.SystemObjects.Putter;

/**
 * Created by Никита on 10.04.2016.
 */
public class Mark extends BaseObject {
    private Putter items;
    private int current;
    private Vector2 wherePosition;
    public Mark(Animation animation, Putter items, int firstEllement, Vector2 wherePosition) {
        this.animation = animation;
        if(firstEllement != -1) {
            this.position = new Vector2(items.skins.get(firstEllement).position.x + wherePosition.x,
                    items.skins.get(firstEllement).position.y + wherePosition.y);
        }
        this.items = items;
        this.current = firstEllement;
        this.wherePosition = wherePosition;
    }

    @Override
    public void update() {
        if(current!=-1) {
            position.x = items.skins.get(current).position.x + wherePosition.x;
            position.y = items.skins.get(current).position.y + wherePosition.y;
        }
    }
    public void setTo(int index){
        current = index;
    }
    @Override
    public void render(SpriteBatch spriteBatch) {
        if(current != -1) {
            spriteBatch.draw(animation.getTexture(), position.x, position.y);
        }
    }
}
