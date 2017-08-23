package com.titanicrun.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.titanicrun.game.Objects.PlayObjects.Animation;
import com.titanicrun.game.Objects.PlayObjects.GameScore;
import com.titanicrun.game.Objects.PlayObjects.MovingSizeObject;
import com.titanicrun.game.Objects.PlayObjects.NormalShark;
import com.titanicrun.game.Objects.PlayObjects.BackgroundCreator;
import com.titanicrun.game.Objects.SystemObjects.Balance;
import com.titanicrun.game.Objects.PlayObjects.EnemiesCreator;
import com.titanicrun.game.Objects.PlayObjects.FallObjectsCreator;
import com.titanicrun.game.Objects.PlayObjects.Player;
import com.titanicrun.game.Objects.SystemObjects.GameTexturesLoader;
import com.titanicrun.game.Objects.SystemObjects.PlayerAnimation;
import com.titanicrun.game.Objects.PlayObjects.Shadow;
import com.titanicrun.game.Objects.PlayObjects.Water;
import com.titanicrun.game.TitanicClass;
import com.titanicrun.game.Objects.SystemObjects.Text;

import java.util.ArrayList;

public class GameScreen extends Screen {
    public GameScore gameScore;
    public Player player;
    public NormalShark shark;
    public int score;
    public Text scoreText;
    private Texture pauseLine;
    public Water water;
    public Balance playBallance;
    public Shadow shadow;
    public Texture night;
    public EnemiesCreator enemiesCreator;
    public ArrayList<BackgroundCreator> backLvl;
    public Array<PlayerAnimation> playerAnimations;
    public ArrayList<FallObjectsCreator> fallObj;
    private Preferences balanceSittings;
    public boolean pause;
    public boolean music;
    public int lvl;
    private MovingSizeObject touchToPlay;
    public boolean beginGame;

    public GameScreen(GameScreenManager gameScreenManager, Balance balance, String name) {
        super(gameScreenManager, name);
        this.playBallance = new Balance(balance.getBalance());
        Load();
    }
    public void Load() {
        balanceSittings = Gdx.app.getPreferences("Balance");
        lvl = 0 ;
        music = true;
        beginGame = true;
        touchToPlay= new MovingSizeObject(new Vector2(200,100), GameTexturesLoader.get("splashes/touchtoplay2.png"),
                140, 100, 1.5f);
        gameScore = new GameScore(this);
        pause = true;

        shark = new NormalShark(GameTexturesLoader.get("shark.png"), GameTexturesLoader.get("sharkReverse.png"));

        shadow = new Shadow(this, GameTexturesLoader.get("backs/shadow.png"));
        night = GameTexturesLoader.get("backs/night.png").getTexture();
        pauseLine = GameTexturesLoader.get("backs/pauseLine.png").getTexture();
        playerAnimations = new Array<PlayerAnimation>();
        for(int i = 1; i <= 9; i++)
            playerAnimations.add(new PlayerAnimation(new Animation(new Texture[]{
                    GameTexturesLoader.get("players/"+i+"player.png").getTexture(),
                    GameTexturesLoader.get("players/"+i+"player2.png").getTexture(),
                    GameTexturesLoader.get("players/"+i+"player3.png").getTexture(),
                    GameTexturesLoader.get("players/"+i+"player2.png").getTexture()},5),
                    GameTexturesLoader.get("players/"+i+"playerFront.png"),
                    GameTexturesLoader.get("players/"+i+"playerPreview.png")));
        Preferences sittings = Gdx.app.getPreferences("Animation");
        int playerIndex = sittings.getInteger("Animation");
        player = new Player(this, playerAnimations.get(playerIndex).run, playerAnimations.get(playerIndex).fly);
        enemiesCreator = new EnemiesCreator(this, GameTexturesLoader.get("object.png"),60);
        score = 0;
        backLvl = new ArrayList<BackgroundCreator>();
        backLvl.add(new BackgroundCreator(this, GameTexturesLoader.get("backs/1backUsuall.png"), GameTexturesLoader.get("backs/1backPreview.png"),false));
        for(int i = 2; i < 10; i++) {
            backLvl.add(new BackgroundCreator(this, GameTexturesLoader.get("backs/"+i+"backUsuall.png"), GameTexturesLoader.get("backs/"+i+"backPreview.png"), true));
        }
        Animation waterAnim = new Animation(new Texture[]{
                GameTexturesLoader.get("water.png").getTexture(),
                GameTexturesLoader.get("water2.png").getTexture(),
                GameTexturesLoader.get("water3.png").getTexture()},10);
        water = new Water(this, waterAnim,
                new Vector2(0,-waterAnim.getTexture().getHeight()/4), 0, 1.5f, 0.5f);
        //FALL 1
        Animation[] animations = new Animation[8];
        for(int i = 1; i < 8; i++) {
            animations[i-1] = GameTexturesLoader.get("fallObj/fall"+i+".png");
        }
        animations[7] = GameTexturesLoader.get("fallObj/fallMan1.png");
        fallObj = new ArrayList<FallObjectsCreator>();
        fallObj.add(new FallObjectsCreator(this,animations, 600));
        //FALL 2
        Animation[] animations2 = new Animation[9];
        for(int i = 1; i < 5; i++) {
            animations2[i-1] = GameTexturesLoader.get("fallObj/2fall"+i+".png");
        }
        for(int i = 5; i < 8; i++) {
            animations2[i-1] = GameTexturesLoader.get("fallObj/fall"+i+".png");
        }
        animations2[7] = new Animation(new Texture[] {
                GameTexturesLoader.get("fallObj/nigga1.png").getTexture(),
        GameTexturesLoader.get("fallObj/nigga2.png").getTexture()},4);
        animations2[8] = GameTexturesLoader.get("fallObj/spoon.png");
        fallObj.add(new FallObjectsCreator(this,animations2, 600));
        //FALL 3
        Animation[] animations3 = new Animation[8];
        for(int i = 0; i < 3; i++) {
            animations3[i] = GameTexturesLoader.get("fallObj/fall"+(i+5)+".png");
        }
        for(int i = 3; i < 8; i++) {
            animations3[i] = GameTexturesLoader.get("fallObj/3fall"+(i-2)+".png");
        }
        fallObj.add(new FallObjectsCreator(this, animations3, 600));

        player.animation.update();
        water.update();
        backLvl.get(lvl).update();
        scoreText = new Text(new Vector2(20, TitanicClass.ScreenHeight - 12), score+"", Color.WHITE);
    }

    @Override
    public void update() {
        if (!pause) {
            if (!beginGame)
                shark.update();
            gameScreenManager.removeScreen("Pause");
            for (int i = 0; i < 2; i++) {
                if (Gdx.input.isTouched(i) || TitanicClass.isPause) {
                    if (((TitanicClass.getMouse(i).getY() >= TitanicClass.ScreenHeight - 125) &&
                            (TitanicClass.getMouse(i).getY() < TitanicClass.ScreenHeight) &&
                            (TitanicClass.getMouse(i).getX() > 0) &&
                            (TitanicClass.getMouse(i).getX() < TitanicClass.ScreenWidth)) || TitanicClass.isPause) {
                        pause = true;
                        gameScreenManager.addScreen(new PauseScreen(gameScreenManager, this, "Pause"));
                        break;
                    }
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.P)) {
                pause = true;
                gameScreenManager.addScreen(new PauseScreen(gameScreenManager, this, "Pause"));
            }
            player.update();
            enemiesCreator.update();
            shadow.update();
            int t = lvl;
            if(t>2)
                t = 2;
            fallObj.get(t).update();
        } else {
            if (Gdx.input.justTouched()) {
                touchToPlay.die();
                touchToPlay.speed = 3f;
                pause = false;
                backLvl.get(lvl).pause = false;
            }
        }
        if (touchToPlay.end) {
            beginGame = false;
        }
        if (beginGame) {
            touchToPlay.update();
        }
        if (lvl != 0) {
            backLvl.get(lvl - 1).update();
        }
        backLvl.get(lvl).update();
        if (score >= lvl * 100 + 90 && lvl != 8) {
            lvl++;
        }
        player.animation.update();
        water.update();
        gameScore.update();
        scoreText.textValue = Integer.toString(score);
        scoreText.update();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(night, 0, 0);
        if(lvl != 0) {
            backLvl.get(lvl - 1).render(spriteBatch);
        }
        backLvl.get(lvl).render(spriteBatch);
        spriteBatch.draw(pauseLine, 0, 0);
        player.render(spriteBatch);
        enemiesCreator.render(spriteBatch);
        int t = lvl;
        if(t>2)
            t = 2;

        fallObj.get(t).render(spriteBatch);
        shark.render(spriteBatch);
        water.render(spriteBatch);
        if(score>900)
            shadow.render(spriteBatch);
        scoreText.render(spriteBatch);
        gameScore.render(spriteBatch);
        if (beginGame) {
            touchToPlay.render(spriteBatch);
        }
        if (music) {
            TitanicClass.playBGM.playMusic("BGM");
            TitanicClass.playBGM.playMusic("Water");
            if(!pause) {
                TitanicClass.playBGM.playMusic("run");
            }
            else {
                TitanicClass.playBGM.pauseAudio("run");
            }
        }
    }

    @Override
    public void reset() {
        for(int i = 0; i < 9; i++) {
            backLvl.get(i).reset();
        }
        for(int i = 0; i < 2; i++) {
            fallObj.get(i).reset();
        }
        music = true;
        shark.reset();
        touchToPlay.reset();
        touchToPlay.speed = 1.5f;
        lvl = 0;
        scoreText.reset();
        score = 0;
        gameScore.reset();
        pause = true;
        beginGame = true;
        enemiesCreator.reset();
        int playerIndex = Gdx.app.getPreferences("Animation").getInteger("Animation");
        player = new Player(this, playerAnimations.get(playerIndex).run, playerAnimations.get(playerIndex).fly);
        TitanicClass.playBGM.pauseAudio("BGM");
        TitanicClass.playBGM.pauseAudio("run");
        TitanicClass.playBGM.pauseAudio("Water");
        playBallance = new Balance(Gdx.app.getPreferences("Balance").getInteger("Balance"));
        music = true;
    }

    public void Die() {
        music = false;
        TitanicClass.playBGM.pauseAudio("BGM");
        TitanicClass.playBGM.pauseAudio("Water");
        TitanicClass.playBGM.pauseAudio("run");
        TitanicClass.kostylScore = score;
        balanceSittings.putInteger("Balance", playBallance.getBalance());
        balanceSittings.flush();
        death();
    }
    public void death() {
        TitanicClass.kostylIsEducation = false;
        gameScreenManager.setNonResetScreen("DeathScreen");
    }
}
