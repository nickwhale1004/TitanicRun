package com.titanicrun.game.Objects.SystemObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.titanicrun.game.TitanicClass;

/**
 * Created by Никита on 29.01.2016.
 */
public class TextDraw{
    public Vector2 drawPosition;    //место отрисовки
    protected String text;          //текст
    private Texture[] abc;          //шрифт
    private Vector2 lastPointer;    //место, с которого нужно рисовать следущую букву/цифру

    public TextDraw(Vector2 drawPosition, String text) {
        this.drawPosition = drawPosition;
        this.abc = TitanicClass.scoreABC;
        this.text = text;
        this.lastPointer = new Vector2(drawPosition.x,drawPosition.y);
    }
    public void render(SpriteBatch spriteBatch) {
        for(int i = 0; i < text.length(); i++) {        //перебираю переданный текст и рисую каждый символ
            //методе .draw(текстура(номер элемента совпадает с цифрой из символа текста), позиция x, позиция y)
            spriteBatch.draw(abc[Integer.parseInt(text.charAt(i) + "")], lastPointer.x, lastPointer.y);
            lastPointer.x+=abc[Integer.parseInt(text.charAt(i) + "")].getWidth(); //увеличиваю позцию места отрисовки на длинну текстуры
        }
        lastPointer.x = drawPosition.x;  //после конца отрисовки ставлю на место lastPointer
        lastPointer.y = drawPosition.y;
    }
}
