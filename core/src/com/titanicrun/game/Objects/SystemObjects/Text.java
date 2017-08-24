package com.titanicrun.game.Objects.SystemObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.titanicrun.game.Objects.BaseObject;

/**
 * Created by User on 16.04.2017.
 */

public class Text extends BaseObject {
    public String textValue, sartText;
    public BitmapFont font;
    public FreeTypeFontGenerator generator;
    public FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    public GlyphLayout layout;
    public boolean drawOnCenter;
    public Text (Vector2 position, String startText, Color color,/*int size,*/ boolean drawOnCenter) {
        this.position = position;
        this.textValue = startText+"";
        this.layout = new GlyphLayout();
        this.generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/peepo.ttf"));
        this.parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        this.parameter.size = /*size*/55;
        this.parameter.color = color;
        this.sartText = startText;
        //this.parameter.borderStraight = true;
        this.parameter.borderWidth = 2;
        this.parameter.borderGamma = 50;
        this.parameter.borderColor = Color.BLACK;
        this.font = generator.generateFont(parameter);
        this.drawOnCenter = drawOnCenter;
    }
    public Text (Vector2 position, String startText, Color color, int size, boolean drawOnCenter) {
        this.position = position;
        this.textValue = startText+"";
        this.layout = new GlyphLayout();
        this.generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/peepo.ttf"));
        this.parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        this.parameter.size = size;
        this.parameter.color = color;
        this.sartText = startText;
       // this.parameter.borderStraight = true;
        this.parameter.borderWidth = 2;
        this.parameter.borderColor = Color.BLACK;
        this.font = generator.generateFont(parameter);
        this.drawOnCenter = drawOnCenter;
    }
    @Override
    public void update() {

    }


    @Override
    public void render(SpriteBatch spriteBatch) {
        if(!drawOnCenter)
            font.draw(spriteBatch, textValue, position.x, position.y);
        else {
            layout.setText(font, textValue);
            font.draw(spriteBatch, textValue, position.x - layout.width/2, position.y - layout.height/2);
        }
    }
    public void reset() {
       // textValue = sartText+"";
    }
}
