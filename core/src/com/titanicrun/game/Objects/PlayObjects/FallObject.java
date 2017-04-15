package com.titanicrun.game.Objects.PlayObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.titanicrun.game.Objects.BaseObject;
import com.titanicrun.game.Screens.GameScreen;
import com.titanicrun.game.Screens.Screen;
import com.titanicrun.game.TitanicClass;

/**
 * Created by User on 16.04.2017.
 */

public class FallObject extends BaseObject {
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
                wasTuched = true;
                animation = Screen.anim("fallObj/+10.png");
                gameScreen.gameScore.failingObjectCatched = true;
            }
        }
        if(!wasTuched && Gdx.input.isKeyPressed(Input.Keys.Z)) {
            gameScreen.gameScore.failingObjectCatched = true;
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