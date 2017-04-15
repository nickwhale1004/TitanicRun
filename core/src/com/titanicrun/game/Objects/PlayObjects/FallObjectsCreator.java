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
    boolean isFirst = true;
    private FallObject current;
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
}
class FallObject extends BaseObject {
    private boolean wasTuched;
    public FallObject(GameScreen gameScreen, Animation animation, int type) {
        super(gameScreen, animation, new Vector2(0, 0));
        set(animation, type);
    }

    @Override
    public void update() {
        animation.update();
        position.y-=10;
        if(!wasTuched && Gdx.input.justTouched()) {
            if (TitanicClass.getMouse().overlaps(getBound())) {
                gameScreen.playBallance.addPoint(10);
                wasTuched = true;
                animation = Screen.anim("fallObj/+10.png");
            }
        }
        if(!wasTuched && Gdx.input.isKeyPressed(Input.Keys.Z)) {
            gameScreen.playBallance.addPoint(10);
            wasTuched = true;
            animation = Screen.anim("fallObj/+10.png");
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
            spriteBatch.draw(animation.getTexture(), position.x, position.y);
    }

    public void set(Animation animation, int type) {
        this.animation = animation;
        this.wasTuched = false;
        this.position.y = TitanicClass.ScreenHeight;
        if(type == 0) {
            this.position.x = 30;
        }
        else {
            this.position.x = TitanicClass.ScreenWidth-30-animation.getTexture().getWidth();
        }
    }
}
