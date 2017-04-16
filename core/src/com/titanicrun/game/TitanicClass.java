package com.titanicrun.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.titanicrun.game.Objects.SystemObjects.Balance;
import com.titanicrun.game.Screens.GameScreenManager;
import com.titanicrun.game.Screens.MenuScreen;
import com.titanicrun.game.Screens.SplashScreen;

public class TitanicClass extends ApplicationAdapter {
	SpriteBatch spriteBatch;
	public static Texture[] scoreABC = new Texture[11];
	public static final int ScreenHeight = 800;
	public static final int ScreenWidth = 480;
	public static OrthographicCamera camera;

	public GameScreenManager gameScreenManager;
	@Override
	public void create () {
		//ABC
		for(int i = 0; i < 10; i++) {
			scoreABC[i] = new Texture("numbers/"+i+".png");
		}
		scoreABC[10] = new Texture("numbers/space.png");

		gameScreenManager = new GameScreenManager();
        gameScreenManager.addScreen(new MenuScreen(gameScreenManager));
		spriteBatch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, ScreenWidth, ScreenHeight);
	}

	@Override
	public void render () {
        Gdx.gl.glClearColor(0,0,2,1);
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
}
