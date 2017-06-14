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

public class GameScreen extends Screen {
    public GameScore gameScore;
    public Player player;
    public int score;
    public Text scoreText;
    private Texture pauseLine;
    public Water water;
    public AudioPlayerInt playBGM;
    public Balance playBallance;
    public Shadow shadow;
    public Texture night;
    public EnemiesCreator enemiesCreator;
    public BackgroundCreator backFirstLvl, backSecondLvl;
    public DeathScreen deathScreen;
    public Array<PlayerAnimation> playerAnimations;
    public FallObjectsCreator fallObj;
    public boolean pause;

    public GameScreen(GameScreenManager gameScreenManager, Balance balance, String name) {
        super(gameScreenManager, name);
        this.playBallance = balance;
        balance.getBalance();
        Load();
    }
    public void Load() {
        gameScore = new GameScore(this);
        pause = true;
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
        backFirstLvl = new BackgroundCreator(this, GameTexturesLoader.get("backs/1backUsuall.png"), GameTexturesLoader.get("backs/1backPrev.png"),false);
        backSecondLvl = new BackgroundCreator(this, GameTexturesLoader.get("backs/2backUsuall.png"), GameTexturesLoader.get("backs/2backPrev.png"),true);
        Animation waterAnim = new Animation(new Texture[]{
                GameTexturesLoader.get("water.png").getTexture(),
                GameTexturesLoader.get("water2.png").getTexture(),
                GameTexturesLoader.get("water3.png").getTexture()},10);
        water = new Water(this, waterAnim,
                new Vector2(0,-waterAnim.getTexture().getHeight()/4), 0, 1.5f, 0.5f);

        // playBGM = new AudioPlayerInt();
        // playBGM.create();
        Animation[] animations = new Animation[8];
        for(int i = 1; i < 8; i++) {
            animations[i-1] = GameTexturesLoader.get("fallObj/fall"+i+".png");
        }
        animations[7] = GameTexturesLoader.get("fallObj/fallMan1.png");
        fallObj = new FallObjectsCreator(this,animations, 600);
        player.animation.update();
        water.update();

        backFirstLvl.update();
        scoreText = new Text(new Vector2(20, TitanicClass.ScreenHeight - 12), score+"", Color.WHITE);
    }
    @Override
    public void update() {
        if(!pause) {
            gameScreenManager.removeScreen("Pause");
            gameScreenManager.removeScreen("Death");
            if(Gdx.input.justTouched()) {
                if (TitanicClass.getMouse().getY() >= TitanicClass.ScreenHeight - 125) {
                    pause = true;
                    // playBGM.waterSound.pause();
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
                pause = false;
                Gdx.app.log(pause+"","pause");
                backFirstLvl.pause = false;
                //playBGM.waterSound.play();
            }
        }
        backFirstLvl.update();
        if(score >= 90 && score <=190)
            backSecondLvl.update();
        player.animation.update();
        water.update();
        gameScore.update();
        scoreText.textValue = Integer.toString(score);
        scoreText.update();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(night, 0, 0);
        backFirstLvl.render(spriteBatch);
        backSecondLvl.render(spriteBatch);
        spriteBatch.draw(pauseLine, 0, 0);
        player.render(spriteBatch);
        enemiesCreator.render(spriteBatch);
        fallObj.render(spriteBatch);
        water.render(spriteBatch);
        if(score>50)
            shadow.render(spriteBatch);
        //score.render(spriteBatch);
        scoreText.render(spriteBatch);
        gameScore.render(spriteBatch);
    }

    @Override
    public void reset() {
        backFirstLvl.reset();
        backSecondLvl.reset();
        scoreText.reset();
        score = 0;
        pause = true;
        enemiesCreator.reset();
        fallObj.reset();
        Preferences sittings = Gdx.app.getPreferences("Animation");
        int playerIndex = sittings.getInteger("Animation");
        player = new Player(this, playerAnimations.get(playerIndex).run, playerAnimations.get(playerIndex).fly);
        Preferences sittingss = Gdx.app.getPreferences("Balance");
        playBallance= new Balance(sittingss.getInteger("Balance"));    }

    public void Die() {
        //playBGM.dispose();
        deathScreen = new DeathScreen(gameScreenManager,this, "Death");
        gameScreenManager.addScreen(deathScreen);
    }
}
