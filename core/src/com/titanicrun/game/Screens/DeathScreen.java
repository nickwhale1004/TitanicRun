package com.titanicrun.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.titanicrun.game.Objects.PlayObjects.Animation;
import com.titanicrun.game.Objects.SystemObjects.AudioPlayerInt;
import com.titanicrun.game.Objects.SystemObjects.Button;
import com.titanicrun.game.Objects.PlayObjects.MoveObject;
import com.titanicrun.game.Objects.SystemObjects.GameTexturesLoader;
import com.titanicrun.game.Objects.SystemObjects.Text;
import com.titanicrun.game.Objects.SystemObjects.TextDraw;
import com.titanicrun.game.TitanicClass;

/**
 * Created by Никита on 30.01.2016.
 */
public class DeathScreen extends Screen {

    public AudioPlayerInt playBGM;
    private Button menu;
    private MoveObject back, gameOver, score, of, max, moveButton;
    private String screen;
    private byte process; //0 - left, 1 - right, 2 - play, 3 - menu
    private int record;
    private Preferences sittings;
    private Text textGameOver, textScore, textOf, textRecord;
    private GlyphLayout layout;

    public DeathScreen(GameScreenManager gameScreenManager, String name) {
        super(gameScreenManager, name);
        this.sittings = Gdx.app.getPreferences("Score");
        this.playBGM = new AudioPlayerInt();
        playBGM.create();
        this.record = sittings.getInteger("Score");
        if(TitanicClass.kostylScore > record) {
            sittings.putInteger("Score", TitanicClass.kostylScore);
            record = TitanicClass.kostylScore;
        }
        this.sittings.flush();
        this.process = -1;
        this.back = new MoveObject(GameTexturesLoader.get("backs/deathBack.png"), new Vector2(TitanicClass.ScreenWidth, 0),
                new Vector2(0, 0), 20);


        this.layout = new GlyphLayout();
        this.textGameOver = new Text(new Vector2(0, 0), "GAME OVER", new Color(1f, 1f, 1f, 1f));
        this.textGameOver.parameter.size = 75;
        this.textGameOver.font = this.textGameOver.generator.generateFont(textGameOver.parameter);
        layout.setText(textGameOver.font, textGameOver.textValue);
        textGameOver.position.x = TitanicClass.ScreenWidth / 2 - layout.width / 2;
        textGameOver.position.y = TitanicClass.ScreenHeight + layout.height + 150;
        this.gameOver = new MoveObject(textGameOver, new Vector2(TitanicClass.ScreenWidth / 2 - layout.width / 2, TitanicClass.ScreenHeight / 2 - layout.height + 150), 13);
        //textScore parameters set
        this.textScore = new Text (new Vector2(0, 0), TitanicClass.kostylScore + "", new Color(1f, 1f, 1f, 1f));
        this.textScore.parameter.size = 75;
        this.textScore.font = this.textScore.generator.generateFont(textScore.parameter);
        //textOf parameters set
        this.textOf = new Text(new Vector2(0, 0), " of ", new Color(1f, 1f, 1f, 1f));
        this.textOf.parameter.size = 75;
        this.textOf.font = this.textOf.generator.generateFont(textOf.parameter);
        //textRecord parameters set
        this.textRecord = new Text(new Vector2(0,0), record + "", new Color(1f, 1f, 1f, 1f));
        this.textRecord.parameter.size = 75;
        this.textRecord.font = this.textRecord.generator.generateFont(textRecord.parameter);
        updatePosition();

        this.screen = "GameScreen";
        Animation buttonAnim = GameTexturesLoader.get("buttons/menu.png");
        this.menu = new Button (buttonAnim, GameTexturesLoader.get("buttons/menuTuched.png"), new Vector2(
                TitanicClass.ScreenWidth / 2 - buttonAnim.getTexture().getWidth() / 2, - buttonAnim.getTexture().getHeight()),0);
        this.moveButton = new MoveObject(menu,
                new Vector2(TitanicClass.ScreenWidth / 2 - buttonAnim.getTexture().getWidth() / 2,
                        TitanicClass.ScreenHeight / 100 * 20), 10);
    }

    private void updatePosition() {
        textScore.textValue = TitanicClass.kostylScore + "";
        textRecord.textValue = record + "";
        layout.setText(textScore.font, textScore.textValue + textOf.textValue + textRecord.textValue);
        while (layout.width > TitanicClass.ScreenWidth) {
            textScore.parameter.size -= 10;
            textOf.parameter.size -= 10;
            textRecord.parameter.size -= 10;
        }
        layout.setText(textScore.font, textScore.textValue + textOf.textValue + textRecord.textValue);
        textScore.position.x = - layout.width;
        textScore.position.y = TitanicClass.ScreenHeight / 2 - layout.height + 50;
        score = new MoveObject(textScore, new Vector2 (TitanicClass.ScreenWidth / 2 - layout.width / 2, TitanicClass.ScreenHeight / 2 - layout.height + 50),
                (int)(TitanicClass.ScreenWidth / 2 - layout.width / 2 - textScore.position.x) / 30);

        layout.setText(textOf.font, textOf.textValue);
        textOf.position.x = (TitanicClass.ScreenWidth + layout.width);
        textOf.position.y = TitanicClass.ScreenHeight / 2 - layout.height + 50;
        layout.setText(textScore.font, textScore.textValue);
        of = new MoveObject(textOf, new Vector2(score.toPosition.x + layout.width, TitanicClass.ScreenHeight / 2 - layout.height + 50),
                (int)-(score.toPosition.x + layout.width - textOf.position.x) / 30);

        layout.setText(textRecord.font, textRecord.textValue);
        textRecord.position.x = TitanicClass.ScreenWidth + layout.width;
        textRecord.position.y = TitanicClass.ScreenHeight / 2 - layout.height + 50;
        layout.setText(textOf.font, textOf.textValue);
        max = new MoveObject(textRecord, new Vector2(of.toPosition.x + layout.width, TitanicClass.ScreenHeight / 2 - layout.height + 50),
                (int)-(of.toPosition.x + layout.width - textRecord.position.x)/30);
    }

    @Override
    public void update() {
        if(process == -1) {
            if(TitanicClass.kostylScore > record) {
                sittings.putInteger("Score", TitanicClass.kostylScore);
                record = TitanicClass.kostylScore;
                sittings.flush();
            }
            updatePosition();
            process = 0;
        }
        if(process == 0) {
            back.update();
            of.update();
            if (back.end) {
                gameOver.update();
                score.update();
                max.update();
                moveButton.update();
                if ((Gdx.input.justTouched() && !TitanicClass.getMouse().overlaps(menu.getBound())) || menu.isPressed() || Gdx.input.isKeyPressed(Input.Keys.BACK)) {
                    Gdx.input.setCatchBackKey(true);
                    back.reverse();
                    if (TitanicClass.kostylIsEducation) {
                        gameScreenManager.getScreen("EducationScreen").reset();
                    } else {
                        gameScreenManager.getScreen("GameScreen").reset();
                    }
                    of.reverse();
                    gameOver.reverse();
                    score.reverse();
                    max.reverse();
                    moveButton.reverse();
                    process = 1;
                    if(menu.isPressed() || Gdx.input.isKeyPressed(Input.Keys.BACK)){
                        Gdx.input.setCatchBackKey(true);
                        screen = "MenuScreen";
                        TitanicClass.isPause = true;
                    }
                    else{
                        screen = "GameScreen";
                    }
                }
            }
        }
        else if (process == 1) {
            gameOver.update();
            score.update();
            max.update();
            moveButton.update();
            if(score.end) {
                back.update();
                of.update();
                if(back.end) {
                    process = 2;
                }
            }
            gameScreenManager.getScreen(screen).update();
        }
        else if(process == 2) {
            TitanicClass.isPause = false;
            gameScreenManager.setScreen(screen);
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        if(TitanicClass.kostylIsEducation)
            gameScreenManager.getScreen("EducationScreen").render(spriteBatch);
        else
            gameScreenManager.getScreen(screen).render(spriteBatch);
        back.render(spriteBatch);
        menu.render(spriteBatch);
        of.render(spriteBatch);
        gameOver.render(spriteBatch);
        score.render(spriteBatch);
        max.render(spriteBatch);
    }

    @Override
    public void reset() {
        screen = "GameScreen";
        textScore.parameter.size = 75;
        textOf.parameter.size = 75;
        textRecord.parameter.size = 75;
       /*textScore.font = textScore.generator.generateFont(textScore.parameter);
        textOf.font = textOf.generator.generateFont(textOf.parameter);
        textRecord.font = textRecord.generator.generateFont(textRecord.parameter);*/
        textGameOver.reset();
        textScore.reset();
        process = -1;
        TitanicClass.kostylScore = 0;
        back.reset();
        moveButton.reset();
        of.reset();
        gameOver.reset();
        score.reset();
        max.reset();
    }
}