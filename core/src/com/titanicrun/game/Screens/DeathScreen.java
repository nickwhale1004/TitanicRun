package com.titanicrun.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.titanicrun.game.Objects.PlayObjects.Animation;
import com.titanicrun.game.Objects.SystemObjects.Button;
import com.titanicrun.game.Objects.PlayObjects.MoveObject;
import com.titanicrun.game.Objects.SystemObjects.TextDraw;
import com.titanicrun.game.TitanicClass;

/**
 * Created by Никита on 30.01.2016.
 */
public class DeathScreen extends Screen {
    private Button menu;
    private GameScreen gameScreen;
    private MoveObject back, gameOver, score, of, max, moveButton;
    private Screen screen;
    private byte process; //0 - left, 1 - right, 2 - play, 3 - menu
    private int record;
    private Preferences sittings;

    public DeathScreen(GameScreenManager gameScreenManager, GameScreen gameScreen) {
        super(gameScreenManager);
        this.gameScreen = gameScreen;
        this.sittings = Gdx.app.getPreferences("Score");
        this.record = sittings.getInteger("Score");
        if(gameScreen.score.getScore() > record) {
            sittings.putInteger("Score", gameScreen.score.getScore());
            record = gameScreen.score.getScore();
        }
        this.sittings.flush();
        this.sittings = Gdx.app.getPreferences("Balance");
        this.sittings.putInteger("Balance", gameScreen.playBallance.getBalance());       // понаписывыл хуйни и рад
        this.sittings.flush();
        this.process = 0;
        this.back = new MoveObject(anim("backs/deathBack.png"), new Vector2(TitanicClass.ScreenWidth, 0),
                new Vector2(0, 0), 20);
        this.gameOver = new MoveObject(anim("numbers/gameover.png"),
                new Vector2(TitanicClass.ScreenWidth / 2 - anim("numbers/gameover.png").getTexture().getWidth() / 2,
                        TitanicClass.ScreenHeight),
                new Vector2(TitanicClass.ScreenWidth / 2 - anim("numbers/gameover.png").getTexture().getWidth() / 2,
                        TitanicClass.ScreenHeight / 2 + 10),
                13);
        this.of = new MoveObject(anim("numbers/of.png"),
                new Vector2((TitanicClass.ScreenWidth / 2 - anim("numbers/of.png").getTexture().getWidth() / 2) * 3,
                        TitanicClass.ScreenHeight / 2 - TitanicClass.scoreABC[0].getHeight() - 10),
                new Vector2(TitanicClass.ScreenWidth / 2 - anim("numbers/of.png").getTexture().getWidth() / 2,
                        TitanicClass.ScreenHeight / 2 - TitanicClass.scoreABC[0].getHeight() - 10), 15);
        this.max = new MoveObject(new TextDraw(
                new Vector2(TitanicClass.ScreenWidth,
                        TitanicClass.ScreenHeight / 2 - TitanicClass.scoreABC[0].getHeight() - 10), record + ""),
                new Vector2(TitanicClass.ScreenWidth / 2 + anim("numbers/of.png").getTexture().getWidth(),
                        TitanicClass.ScreenHeight / 2 - TitanicClass.scoreABC[0].getHeight() - 10), 7);
        String score = gameScreen.score.getScore() + "";
        this.score = new MoveObject(new TextDraw(
                new Vector2(-score.length() * TitanicClass.scoreABC[0].getWidth(),                                //from
                        TitanicClass.ScreenHeight / 2 - TitanicClass.scoreABC[0].getHeight() - 10), score),
                new Vector2(TitanicClass.ScreenWidth / 2 - score.length() * TitanicClass.scoreABC[0].getWidth() - 50,    //to
                        TitanicClass.ScreenHeight / 2 - TitanicClass.scoreABC[0].getHeight() - 10), 7);
        this.screen = gameScreen;
        Animation buttonAnim = anim("buttons/menu.png");
        this.menu = new Button(buttonAnim, anim("buttons/menuTuched.png"), new Vector2(
                TitanicClass.ScreenWidth / 2 - buttonAnim.getTexture().getWidth() / 2, - buttonAnim.getTexture().getHeight()));
        this.moveButton = new MoveObject(menu,
                new Vector2(TitanicClass.ScreenWidth / 2 - buttonAnim.getTexture().getWidth() / 2,
                        TitanicClass.ScreenHeight / 100 * 20), 10);
    }

    @Override
    public void update() {
        if(process == 0) {
            back.update();
            of.update();
            if (back.end) {
                gameOver.update();
                score.update();
                max.update();
                moveButton.update();
                if ((Gdx.input.justTouched() && !TitanicClass.getMouse().overlaps(menu.getBound())) || menu.isPressed()) {
                    back.reverse();
                    of.reverse();
                    gameOver.reverse();
                    score.reverse();
                    max.reverse();
                    moveButton.reverse();
                    process = 1;
                    if(menu.isPressed()){
                        screen = new MenuScreen(gameScreenManager);
                    }
                    else{
                        screen = new GameScreen(gameScreenManager, gameScreen.playBallance);
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
            screen.update();
        }
        else if(process == 2) {
            gameScreenManager.setScreen(screen);
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        screen.render(spriteBatch);
        back.render(spriteBatch);
        menu.render(spriteBatch);
        of.render(spriteBatch);
        gameOver.render(spriteBatch);
        score.render(spriteBatch);
        max.render(spriteBatch);
    }
}
