package com.titanicrun.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.titanicrun.game.Objects.BaseObject;
import com.titanicrun.game.Objects.PlayObjects.Animation;
import com.titanicrun.game.Objects.PlayObjects.MoveObject;
import com.titanicrun.game.Objects.PlayObjects.MoveObjectGroup;
import com.titanicrun.game.Objects.PlayObjects.MovingSizeObject;
import com.titanicrun.game.Objects.PlayObjects.SizeChangeObject;
import com.titanicrun.game.Objects.SystemObjects.GameTexturesLoader;
import com.titanicrun.game.Objects.SystemObjects.SettingsButton;
import com.titanicrun.game.TitanicClass;

/**
 * Created by Никита on 15.04.2017.
 */
public class SplashScreen extends Screen {
    private Screen screen;
    private Screen settingsScreen;
    private SizeChangeObject back1Sizable;
    private MovingSizeObject texttt;
    MoveObjectGroup group1, group2, group3, group4;
    MoveObject back1, player11, player12, back2, back3, back4, player4, man4, pic41, pic42;
    public int process; //0 - 1screen, 1 - 2screen,2 - 3screen, 3 - 4screen 4-end
    public SplashScreen(GameScreenManager gameScreenManager, String name) {
        super(gameScreenManager, name);
        if ((Gdx.app.getPreferences("Score").getInteger("Score") > 0) || (Gdx.app.getPreferences("Balance").getInteger("Balance") != 0)) {
            this.process = -1;
        } else {
            this.process = 0;
        }
        Animation back1Anim = GameTexturesLoader.get("splashes/1back.png");
        Animation player11Anim = GameTexturesLoader.get("splashes/1char1.png");
        Animation player12Anim = GameTexturesLoader.get("splashes/1char2.png");
        Animation back2Anim = GameTexturesLoader.get("splashes/2.png");
        Animation back3Anim = GameTexturesLoader.get("splashes/32.png");
        Animation back4Anim = GameTexturesLoader.get("splashes/4back.png");
        Animation player4Anim = GameTexturesLoader.get("splashes/4char.png");
        Animation man4Anim = GameTexturesLoader.get("splashes/4man.png");
        Animation pic41Anim = GameTexturesLoader.get("splashes/4pic1.png");
        Animation pic42Anim = GameTexturesLoader.get("splashes/4pic2.png");
        Animation back1PrevAnim = GameTexturesLoader.get("splashes/1backPrev.png");
        this.settingsScreen = gameScreenManager.getScreen("SettingScreen");
        this.settingsScreen.update();
        this.back1Sizable = new SizeChangeObject(new Vector2(TitanicClass.ScreenWidth/2,
                TitanicClass.ScreenHeight/2), back1PrevAnim, 0f);
        back1Sizable.speed = 5f;
        this.back1Sizable.changeTo(100, 2);
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
        texttt = new MovingSizeObject(new Vector2(200,100), GameTexturesLoader.get("splashes/continue.png"), 100, 140, 1.5f);
        group1 = new MoveObjectGroup();
        group1.add(back1);
        group1.add(player11);
        group1.add(player12);
        group2 = new MoveObjectGroup();
        group2.add(back2);
        group3 = new MoveObjectGroup();
        group3.add(back3);
        group4 = new MoveObjectGroup();
        group4.add(back4);
        group4.add(player4);
        group4.add(pic41);
        group4.add(pic42);
        group4.add(man4);
    }

    @Override
    public void update() {
        if (process == -1) {
            back1Sizable.update();
            back1Sizable.position.x =
                    TitanicClass.ScreenWidth/2-(back1Sizable.animation.getTexture().getWidth()/100f)*back1Sizable.size/2f;
            back1Sizable.position.y =
                    TitanicClass.ScreenHeight/2-(back1Sizable.animation.getTexture().getHeight()/100f)*back1Sizable.size/2f;
            if(back1Sizable.end) {
                process = 0;
            }
        }
        else if(process == 0) {
            back1.update();
            player11.update();
            if (player11.end) {
                player12.update();
                if(Gdx.input.justTouched()) {
                    group1.moveOn(new Vector2(0, 800), 20);
                    process = 1;
                }
            }
            texttt.update();
        }
        else if(process == 1) {
            back1.update();
            player11.update();
            player12.update();
            back2.update();
            if(back2.end) {
                if(Gdx.input.justTouched()) {
                    group2.moveOn(new Vector2(0, 800), 20);
                    process = 2;
                }
            }
            texttt.update();
        }
        else if(process == 2) {
            back2.update();
            back3.update();
            if(back3.end) {
                if(Gdx.input.justTouched()) {
                    process = 3;
                    group3.moveOn(new Vector2(0, 800), 20);
                }
            }
            texttt.update();
        }
        else if(process == 3) {
            back3.update();
            back4.update();
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
                    group4.moveOn(new Vector2(0, 800), 20);
                    screen = gameScreenManager.getScreen("EducationScreen");
                    process = 4;
                }
            }
        }
        else if (process == 4) {
            screen.update();
            back4.update();
            player4.update();
            man4.update();
            pic41.update();
            pic42.update();
            if (man4.end) {
                gameScreenManager.setScreen("EducationScreen");
            }
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        if(process == -1) {
            settingsScreen.render(spriteBatch);
            back1Sizable.render(spriteBatch);
        }
        else if(process == 0) {
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
            back1.render(spriteBatch);
            player12.render(spriteBatch);
        }
        else if (process == 2) {
            back3.render(spriteBatch);
            if (back3.end) {
                texttt.render(spriteBatch);
            }
            back2.render(spriteBatch);
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
            back3.render(spriteBatch);
        }
        else if(process == 4) {
            screen.render(spriteBatch);
            back4.render(spriteBatch);
            man4.render(spriteBatch);
            player4.render(spriteBatch);
            pic41.render(spriteBatch);
            pic42.render(spriteBatch);
        }
    }

    @Override
    public void reset() {
        process = -1;
        back1.reset();
        settingsScreen.reset();
        player11.reset();
        player12.reset();
        back2.reset();
        back3.reset();
        back4.reset();
        player4.reset();
        man4.reset();
        pic41.reset();
        pic42.reset();
        texttt.reset();
    }
}
