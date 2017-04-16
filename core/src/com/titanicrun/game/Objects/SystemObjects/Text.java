package com.titanicrun.game.Objects.SystemObjects;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.titanicrun.game.Objects.BaseObject;

/**
 * Created by User on 16.04.2017.
 */

public class Text extends BaseObject {
    public String textValue = new String();
    BitmapFont font = new BitmapFont();
    public Text (Vector2 position, String startText) {
        this.position = position;
        textValue = startText;
    }

    @Override
    public void update() {

    }


    @Override
    public void render(SpriteBatch spriteBatch) {
        font.getData().setScale(3.5f);
        font.setColor(0.95f, 0.92f, 0.03f, 1);
        font.draw(spriteBatch, textValue, position.x, position.y);
    }
}
