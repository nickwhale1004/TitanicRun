package com.titanicrun.game.Objects.PlayObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.titanicrun.game.Objects.BaseObject;
import com.titanicrun.game.Screens.GameScreen;
import com.titanicrun.game.Screens.Screen;
import com.titanicrun.game.TitanicClass;

import java.util.Random;

/**
 * Created by Никита on 24.04.2016.
 */
public class FallObjectsCreator extends Creator {
    public boolean isFirst = true;
    public FallObject current;
    private Random randAnim, randType;
    public FallObjectsCreator(GameScreen gameScreens, Animation[] objAnimations, float interval) {
        super(objAnimations, interval);
        this.randAnim = new Random();
        this.randType = new Random();
        this.current = new FallObject(gameScreens, objAnimations[randAnim.nextInt(objAnimations.length)], randType.nextInt(2));
    }
    private void tick() {
        current.set(objAnimations[randAnim.nextInt(objAnimations.length)], randType.nextInt(2));
    }
    @Override
    public void update() {
        time++;
        if(time >= interval) {
            isFirst = false;
            time = 0;
            tick();
        }
        if(!isFirst)
            current.update();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        current.render(spriteBatch);
    }
    public void reset() {
        time = 0;
        current.position.y = TitanicClass.ScreenHeight + current.animation.getTexture().getHeight();
        current.wasTuched = false;
        isFirst = true;
        current.animation = objAnimations[randAnim.nextInt(objAnimations.length)];
        int type = randType.nextInt(2);
        if(type == 0) {
            current.position.x = 30;
        }
        else {
            current.position.x = TitanicClass.ScreenWidth-30-current.animation.getTexture().getWidth();
        }
    }
}

