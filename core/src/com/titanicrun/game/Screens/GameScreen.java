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
import com.titanicrun.game.Objects.PlayObjects.MoveObject;
import com.titanicrun.game.Objects.PlayObjects.MovingSizeObject;
import com.titanicrun.game.Objects.PlayObjects.SuperShark;
import com.titanicrun.game.Objects.SystemObjects.AudioPlayerInt;
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
    public SuperShark shark;
    public int score;
    public Text scoreText;
    private Texture pauseLine;
    public Water water;
    public AudioPlayerInt playBGM;
    public Balance playBallance;
    public Shadow shadow;
    public Texture night;
    public EnemiesCreator enemiesCreator;
    public ArrayList<BackgroundCreator> backLvl;
    public DeathScreen deathScreen;
    public Array<PlayerAnimation> playerAnimations;
    public FallObjectsCreator fallObj;
    public boolean pause;
    public boolean music;
    public int lvl;
    private MovingSizeObject touchToPlay;
    public boolean beginGame;

    public GameScreen(GameScreenManager gameScreenManager, Balance balance, String name) {
        super(gameScreenManager, name);
        this.playBallance = balance;
        balance.getBalance();
        Load();
    }
    public void Load() {
        lvl = 0;
        music = true;
        beginGame = true;
        touchToPlay= new MovingSizeObject(new Vector2(50,100), GameTexturesLoader.get("splashes/touchtoplay2.png"), 100, 140, 1.5f);
        //touchToPlay = new MoveObject(GameTexturesLoader.get("splashes/touchtoplay2.png"),new Vector2(50,100), new Vector2(50,100), 1f);
        gameScore = new GameScore(this);
        pause = true;
        shark = new SuperShark(GameTexturesLoader.get("shark.png"), GameTexturesLoader.get("sharkReverse.png"));
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

        playBGM = new AudioPlayerInt();
        playBGM.create();
        Animation[] animations = new Animation[8];
        for(int i = 1; i < 8; i++) {
            animations[i-1] = GameTexturesLoader.get("fallObj/fall"+i+".png");
        }
        animations[7] = GameTexturesLoader.get("fallObj/fallMan1.png");
        fallObj = new FallObjectsCreator(this,animations, 600);
        player.animation.update();
        water.update();
        backLvl.get(lvl).update();
        scoreText = new Text(new Vector2(20, TitanicClass.ScreenHeight - 12), score+"", Color.WHITE);
    }
    @Override
    public void update() {
        if(!pause) {
            shark.update();
            gameScreenManager.removeScreen("Pause");
            gameScreenManager.removeScreen("Death");
            if(Gdx.input.justTouched()) {
                if (TitanicClass.getMouse().getY() >= TitanicClass.ScreenHeight - 125) {
                    pause = true;
                    gameScreenManager.addScreen(new PauseScreen(gameScreenManager, this, "Pause"));
                }
            }
            if(Gdx.input.isKeyPressed(Input.Keys.P)) {
                pause = true;
                // playBGM.waterSound.pause();
                gameScreenManager.addScreen(new PauseScreen(gameScreenManager, this, "Pause"));
            }
            player.update();
            enemiesCreator.update();
            shadow.update();
            fallObj.update();
        }
        else {

            if (Gdx.input.justTouched()) {
                touchToPlay.die();
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
        if(lvl != 0) {
            backLvl.get(lvl-1).update();
        }
        backLvl.get(lvl).update();
            if (score >= lvl*100+90 && lvl != 8)
                lvl++;
        player.animation.update();
        water.update();
        gameScore.update();
        scoreText.textValue = Integer.toString(score);
        scoreText.update();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(night, 0, 0);
        if(lvl != 0)
            backLvl.get(lvl-1).render(spriteBatch);
        backLvl.get(lvl).render(spriteBatch);
        spriteBatch.draw(pauseLine, 0, 0);
        player.render(spriteBatch);
        enemiesCreator.render(spriteBatch);
        fallObj.render(spriteBatch);
        shark.render(spriteBatch);
        water.render(spriteBatch);
        if(score>900)
            shadow.render(spriteBatch);
        //score.render(spriteBatch);
        scoreText.render(spriteBatch);
        gameScore.render(spriteBatch);
        if (beginGame) {
            touchToPlay.render(spriteBatch);
        }
        if (music) {
            playBGM.playMusic("BGM");
            playBGM.playMusic("Water");
        }
    }

    @Override
    public void reset() {
        for(int i = 0; i < 9; i++) {
            backLvl.get(i).reset();
        }
        shark.reset();
        touchToPlay.reset();
        lvl = 0;
        scoreText.reset();
        score = 0;
        pause = true;
        beginGame = true;
        enemiesCreator.reset();
        fallObj.reset();
        Preferences sittings = Gdx.app.getPreferences("Animation");
        int playerIndex = sittings.getInteger("Animation");
        player = new Player(this, playerAnimations.get(playerIndex).run, playerAnimations.get(playerIndex).fly);
        playBallance = new Balance(Gdx.app.getPreferences("Balance").getInteger("Balance"));
        playBGM.pauseAudio("BGM");
        playBGM.pauseAudio("Water");
    }

    public void Die() {
        playBGM.playSound("Death");
        music = false;
        playBGM.pauseAudio("BGM");
        playBGM.pauseAudio("Water");
        deathScreen = new DeathScreen(gameScreenManager,this, "Death");
        gameScreenManager.addScreen(deathScreen);
    }
}
