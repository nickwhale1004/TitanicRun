package com.titanicrun.game.Objects.PlayObjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.titanicrun.game.Objects.BaseObject;
import com.badlogic.gdx.graphics.Texture;
import com.titanicrun.game.Objects.SystemObjects.TextDraw;

/**
 * Created by Никита on 30.01.2016.
 */
public class MoveObject extends BaseObject {
    private Vector2 basePos;
    public boolean end;
    protected Vector2 toPosition;
    public Vector2 speed;
    private Vector2 fromPosition;
    private TextDraw textDraw;
    public BaseObject obj;

    public MoveObject(Animation animation, Vector2 position, Vector2 toPosition, float speedx, float speedy) {
        super(animation, position);
        this.fromPosition = new Vector2(position.x, position.y);
        inicializate(toPosition,new Vector2(speedx, speedy));
    }
    public MoveObject(Animation animation, Vector2 position, Vector2 toPosition, float speed) {
        super(animation, position);
        this.fromPosition = new Vector2(position.x, position.y);
        inicializate(toPosition,new Vector2(speed, speed));
    }
    public MoveObject(TextDraw textDraw, Vector2 toPosition, int speed) {
        super(null, textDraw.drawPosition);
        this.textDraw = textDraw;
        inicializate(toPosition, new Vector2(speed, speed));
    }
    public MoveObject(BaseObject obj, Vector2 toPosition, int speed) {//
        super(obj.animation, obj.position);
        this.obj = obj;
        this.fromPosition = obj.position;
        inicializate(toPosition,new Vector2(speed, speed));

    }
    private void inicializate(Vector2 toPosition, Vector2 speed)
    {
        this.toPosition = new Vector2(toPosition.x, toPosition.y);
        this.fromPosition = new Vector2(position.x, position.y);
        this.speed = speed;
        this.end = false;
        this.basePos = new Vector2(position.x, position.y);
    }
    @Override
    public void update() {
        if(animation != null)
            animation.update();
        if(obj != null)
            obj.update();

        if(position.y < toPosition.y) {
            position.y += speed.y;
        }
        else if(position.y > toPosition.y) {
            position.y -= speed.y;
        }
        if(position.x < toPosition.x) {
            position.x += speed.x;
        }
        else if(position.x > toPosition.x) {
            position.x -= speed.x;
        }
        if(position.y < toPosition.y && fromPosition.y > toPosition.y ||
                position.y > toPosition.y && fromPosition.y < toPosition.y) {
            position.y = toPosition.y;
        }
        if(position.x < toPosition.x && fromPosition.x > toPosition.x ||
                position.x > toPosition.x && fromPosition.x < toPosition.x) {
            position.x = toPosition.x;
        }
        if(position.x == toPosition.x && position.y == toPosition.y) {
            end = true;
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        if(animation != null) {
            spriteBatch.draw(animation.getTexture(), position.x, position.y);
        }
        else if (obj != null){
            obj.render(spriteBatch);
        }
        else {
            textDraw.render(spriteBatch);
        }
    }

    public void reverse() {
        Vector2 temp = new Vector2(toPosition.x, toPosition.y);
        toPosition = new Vector2(fromPosition.x,fromPosition.y);
        fromPosition = new Vector2(temp.x, temp.y);
        end = false;
    }
    public void change(Vector2 toPosition) {
        this.toPosition = toPosition;
        this.fromPosition = new Vector2(position.x, position.y);
        this.end = false;
    }
    public Texture getTexture() {
        return animation.getTexture();
    }
    public void reset() {
        position.x = basePos.x;
        position.y = basePos.y;
        toPosition = null;
    }
}
