package com.titanicrun.game.Objects.SystemObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.titanicrun.game.Objects.BaseObject;

/**
 * Created by User on 16.04.2017.
 */

public class Text extends BaseObject {
    public String textValue;
    private BitmapFont font;
    FreeTypeFontGenerator generator;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    public Text (Vector2 position, String startText) {
        this.position = position;
        textValue = startText;
        this.font = new BitmapFont();
        this.generator = new FreeTypeFontGenerator(Gdx.files.internal("peepo.ttf"));
        this.parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        this.parameter.size = 55;
        this.parameter.color = new Color(0.95f, 0.92f, 0.03f, 1);
        this.font = generator.generateFont(parameter);
    }

    @Override
    public void update() {

    }


    @Override
    public void render(SpriteBatch spriteBatch) {
        font.draw(spriteBatch, textValue, position.x, position.y);
    }
}