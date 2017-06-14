package com.titanicrun.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.titanicrun.game.Objects.BaseObject;
import com.titanicrun.game.Objects.PlayObjects.Animation;
import com.titanicrun.game.Objects.PlayObjects.MoveObject;
import com.titanicrun.game.Objects.PlayObjects.MovingSizeObject;
import com.titanicrun.game.TitanicClass;

/**
 * Created by Никита on 15.04.2017.
 */
public class SplashScreen extends Screen {
    private EducationScreen educationScreen;
    private MovingSizeObject texttt;
    MoveObject back1, player11, player12, back2, back3, back4, player4, man4, pic41, pic42;
    int process; //0 - 1screen, 1 - 2screen,2 - 3screen, 3 - 4screen
    public SplashScreen(GameScreenManager gameScreenManager, String name) {
        super(gameScreenManager, name);
        this.process = 0;
        Animation back1Anim = anim("splashes/1back.png");
        Animation player11Anim = anim("splashes/1char1.png");
        Animation player12Anim = anim("splashes/1char2.png");
        Animation back2Anim = anim("splashes/2.png");
        Animation back3Anim = anim("splashes/32.png");
        Animation back4Anim = anim("splashes/4back.png");
        Animation player4Anim = anim("splashes/4char.png");
        Animation man4Anim = anim("splashes/4man.png");
        Animation pic41Anim = anim("splashes/4pic1.png");
        Animation pic42Anim = anim("splashes/4pic2.png");
        this.back1 = new MoveObject(back1Anim,
                new Vector2(-back1Anim.getTexture().getWidth() + TitanicClass.ScreenWidth, 0),
                new Vector2(0, 0), 2);
        this.player11 = new MoveObject(player11Anim,
                new Vector2(-player11Anim.getTexture().getWidth() + TitanicClass.ScreenWidth - 170, 0),
                new Vector2(-180, 0), 1.97f);
        this.player12 = new MoveObject(player12Anim, new Vector2(-170, 0), new Vector2(-180, 0), 1.97f);
        this.back2 = new MoveObject(back2Anim,
                new Vector2(-back2Anim.getTexture().getWidth() + TitanicClass.ScreenWidth+50, 0),
                new Vector2((-back2Anim.getTexture().getWidth()+TitanicClass.ScreenHeight)/2-150, 0), 1f);
        this.back3 = new MoveObject(back3Anim,
                new Vector2(-350, -200),
                new Vector2(-350,0),1f);
        this.back4 = new MoveObject(back4Anim,
                new Vector2(-back4Anim.getTexture().getWidth() + TitanicClass.ScreenWidth, 0),
                new Vector2(0, 0), 2);
        this.player4 = new MoveObject(player4Anim,
                new Vector2(-player4Anim.getTexture().getWidth() + TitanicClass.ScreenWidth+10, 0),
                new Vector2(0, 0), 1.95f);
        this.man4 = new MoveObject(man4Anim,
                new Vector2(-man4Anim.getTexture().getWidth() + TitanicClass.ScreenWidth+10, +10),
                new Vector2(400, -300), 2.1f, 0.25f);
        this.pic41 = new MoveObject(pic41Anim,
                new Vector2(-pic41Anim.getTexture().getWidth() + TitanicClass.ScreenWidth, 0),
                new Vector2(0, 0), 2);
        this.pic42 = new MoveObject(pic42Anim,
                new Vector2(-pic42Anim.getTexture().getWidth() + TitanicClass.ScreenWidth, 0),
                new Vector2(0, 0), 2);
        texttt = new MovingSizeObject(new Vector2(30,50), anim("splashes/continue.png"), 100, 140, 1.5f);
    }

    @Override
    public void update() {
        if(process == 0) {
            back1.update();
            player11.update();
            if (player11.end) {
                player12.update();
                if(Gdx.input.justTouched()) {
                    process = 1;
                }
            }
            texttt.update();
        }
        else if(process == 1) {
            back2.update();
            if(back2.end) {
                if(Gdx.input.justTouched()) {
                    process = 2;
                }
            }
            texttt.update();
        }
        else if(process == 2) {
            back3.update();
            if(back3.end) {
                if(Gdx.input.justTouched()) {
                    process = 3;
                }
            }
            texttt.update();
        }
        else if(process == 3) {
            back4.update();;
            player4.update();
            if(back4.end) {
                man4.speed.x = 1.1f;
                man4.speed.y = 0.7f;
            }
            man4.update();
            pic41.update();
            pic42.update();
            texttt.update();
            if(pic42.end) {
                if(Gdx.input.justTouched()) {
                    educationScreen = new EducationScreen(gameScreenManager,  "Education");
                    gameScreenManager.addScreen(educationScreen);
                }
            }
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        if(process == 0) {
            back1.render(spriteBatch);
            if (player11.end) {
                player12.render(spriteBatch);
            } else {
                player11.render(spriteBatch);
            }
            if (player12.end) {
                texttt.render(spriteBatch);
            }
        }
        else if (process == 1) {
            back2.render(spriteBatch);
            if (back2.end) {
                texttt.render(spriteBatch);
            }
        }
        else if (process == 2) {
            back3.render(spriteBatch);
            if (back3.end) {
                texttt.render(spriteBatch);
            }
        }
        else if (process == 3) {
            back4.render(spriteBatch);
            man4.render(spriteBatch);
            player4.render(spriteBatch);
            pic41.render(spriteBatch);
            pic42.render(spriteBatch);
            if (pic42.end) {
                texttt.render(spriteBatch);
            }
        }
    }

    @Override
    public void reset() {
        process = 0;
        back1.reset();
        player11.reset();
        player12.reset();
        back2.reset();
        back3.reset();
        back4.reset();
        player4.reset();
        man4.reset();
        pic41.reset();
        pic42.reset();
    }
}
