package com.titanicrun.game.Objects.PlayObjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.titanicrun.game.Objects.BaseObject;
import com.titanicrun.game.Objects.SystemObjects.Text;
import com.titanicrun.game.Screens.GameScreen;
import com.titanicrun.game.TitanicClass;

/**
 * Created by Никита on 15.04.2017.
 */
public class GameScore extends BaseObject {
    private GameScreen gameScreen;
    private Text ballanceText;
    public boolean failingObjectCatched;
    public boolean objectFalled;
    public boolean sharkCathed;
    public boolean sharkFalled;
    private boolean isFinished;
    private MoveObject moveableText;
    private GlyphLayout layout;
    private int process; //0-nothing, 1-just fallObj, 2-just shark

    public GameScore(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        layout = new GlyphLayout();
        ballanceText = new Text(new Vector2(0, 0), Integer.toString(0), new Color(0.95f, 0.92f, 0.03f, 1));
        failingObjectCatched = false;
        objectFalled = false;
        sharkCathed = false;
        sharkFalled = false;
        isFinished = false;
        moveableText = new MoveObject(ballanceText, ballanceText.position, 5);
    }
    @Override
    public void update() {
        if (gameScreen.fallObj.current.position.y == 0) {
            objectFalled = true;
        }
        if (gameScreen.shark.position.y <= 0 && sharkCathed) {
            sharkFalled = true;
        }
        if (gameScreen.shark.touched) {
            gameScreen.shark.touched = false;
            sharkCathed = true;
        }
        if (process == 0) {
            ballanceText.textValue = Integer.toString(gameScreen.playBallance.getBalance());
            layout.setText(ballanceText.font, ballanceText.textValue);
            ballanceText.position.x = TitanicClass.ScreenWidth - layout.width - 15;
            ballanceText.position.y = TitanicClass.ScreenHeight - 12;
            moveableText = new MoveObject(ballanceText, ballanceText.position, 5);

            if (objectFalled && failingObjectCatched) {
                process = 1;
            }
            if (sharkCathed && sharkFalled) {
                process = 2;
            }
        } else if (process == 1) {
            move();
            //О Б Н У Л Е Н И Е  П Е Р Е М Е Н Н Ы Х
            if (isFinished && moveableText.position.x == (TitanicClass.ScreenWidth - layout.width - 15)) {
                if(sharkCathed && sharkFalled) {
                    process = 2;
                }
                else {
                    process = 0;
                }
                isFinished = false;
                failingObjectCatched = false;
                objectFalled = false;
            }
        }
        else if (process == 2) {
            move();
            //О Б Н У Л Е Н И Е  П Е Р Е М Е Н Н Ы Х
            if (isFinished && moveableText.position.x == (TitanicClass.ScreenWidth - layout.width - 15)) {
                if(failingObjectCatched && objectFalled) {
                    process = 1;
                }
                else {
                    process = 0;
                }
                sharkFalled = false;
                sharkCathed = false;
                isFinished = false;
            }
        }
    }
    public void move() {
        //Н А Ч И Н А Е М  Д В И Ж Е Н И Е  В П Р А В О
        if (!isFinished) {
            moveableText.change(new Vector2(TitanicClass.ScreenWidth, ballanceText.position.y));
            moveableText.update();
        }
        //П Р О В Е Р К А  Н А  О К О Н Ч А Н И Е  Д В И Ж Е Н И Я  И  Д О Б А В Л Е Н И Е  М О Н Е Т
        if (moveableText.position.x == TitanicClass.ScreenWidth) {
            isFinished = true;
            gameScreen.playBallance.addPoint(10);
            ballanceText.textValue = Integer.toString(gameScreen.playBallance.getBalance());
            layout.setText(ballanceText.font, ballanceText.textValue);
            Vector2 toPos = new Vector2(0,0);
            toPos.x = TitanicClass.ScreenWidth - layout.width - 15;
            toPos.y = TitanicClass.ScreenHeight - 12;
            moveableText = new MoveObject(ballanceText, toPos, 5);
        }
        //Д В И Ж Е Н И Е  В Л Е В О
        if (isFinished && moveableText.position.x > (TitanicClass.ScreenWidth - layout.width - 15)) {
            moveableText.change(new Vector2(TitanicClass.ScreenWidth - layout.width - 15, ballanceText.position.y));
            moveableText.update();
        }

    }
    @Override
    public void render(SpriteBatch spriteBatch) {
        moveableText.render(spriteBatch);
    }

    @Override
    public void reset() {

    }
}
