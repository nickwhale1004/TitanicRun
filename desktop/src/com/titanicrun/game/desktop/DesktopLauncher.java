package com.titanicrun.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.titanicrun.game.TitanicClass;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		System.setProperty("user.name","seconduser");
		config.width = TitanicClass.ScreenWidth;
		config.height = TitanicClass.ScreenHeight;
		config.backgroundFPS = 30;
		new LwjglApplication(new TitanicClass(null), config);
	}
}
