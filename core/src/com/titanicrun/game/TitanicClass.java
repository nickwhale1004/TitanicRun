package com.titanicrun.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.titanicrun.game.Objects.SystemObjects.AudioPlayerInt;
import com.titanicrun.game.Objects.SystemObjects.GameTexturesLoader;
import com.titanicrun.game.Screens.GameScreenManager;
import com.titanicrun.game.Screens.LoadingScreen;

public class TitanicClass extends ApplicationAdapter {
	private static GPGS gpgs;
	private static SpriteBatch spriteBatch;
    public static AudioPlayerInt playBGM;
	public static int kostylScore;
	public static boolean kostylIsEducation;
	public static int kostylIsDeathScreenEnd;
	public static final int ScreenHeight = 800;
	public static final int ScreenWidth = 480;
	private static OrthographicCamera camera;
	public static boolean isPause;
	public GameScreenManager gameScreenManager;

	public TitanicClass(GPGS gpgs) {
		this.gpgs = gpgs;
	}
	@Override
	public void create () {
		playBGM = new AudioPlayerInt();
        playBGM.create();
		isPause = false;
		gameScreenManager = new GameScreenManager();
		gameScreenManager.addScreen(new LoadingScreen(gameScreenManager, "LoadingScreen"));
		spriteBatch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, ScreenWidth, ScreenHeight);
	}
	@Override
	public void render () {
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		spriteBatch.setProjectionMatrix(camera.combined);
		gameScreenManager.update();
		spriteBatch.begin();
		gameScreenManager.render(spriteBatch);
		spriteBatch.end();
	}

	public static Rectangle getMouse() {
		Vector3 mousePosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(),0);
		TitanicClass.camera.unproject(mousePosition);
		return new Rectangle(mousePosition.x, mousePosition.y,1,1);
	}
	public static Rectangle getMouse(int n) {
		Vector3 mousePosition = new Vector3(Gdx.input.getX(n), Gdx.input.getY(n),0);
		TitanicClass.camera.unproject(mousePosition);
		return new Rectangle(mousePosition.x, mousePosition.y,1,1);
	}
	@Override
	public void pause() {
		if(gameScreenManager.getCurrenScreen().name == "GameScreen" ||
				gameScreenManager.getCurrenScreen().name == "EducationScreen") {
			gameScreenManager.getCurrenScreen().update();
		}
		isPause = true;
	}
	@Override
	public void resume() {
		if(gameScreenManager.getCurrenScreen().name == "GameScreen"||
				gameScreenManager.getCurrenScreen().name == "EducationScreen") {
			gameScreenManager.getCurrenScreen().update();
		}
		isPause = false;
	}
	public static void showSnapshots() {
		gpgs.showSavedGamesUI();
	}
	public static void showLeaderboard() {
		if(gpgs.isConnected())
			gpgs.showLeaderboard();
	}
	public static void addScore(int i) {
		if (gpgs != null) {
			gpgs.submitScore(i);
		}
	}
	@Override
	public void dispose() {
		//exit();
	}
	public static void exit() {
		if(spriteBatch.isDrawing())
			spriteBatch.end();
		GameTexturesLoader.dispose();
		spriteBatch.dispose();
		playBGM.dispose();
		if (gpgs != null && gpgs.isConnected())
			gpgs.disconnect();
	}
}


