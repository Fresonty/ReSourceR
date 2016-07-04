package com.leon.resourcer.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.leon.resourcer.Resourcer;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "ReSourceR";
		config.width = 1600;
		config.height = 808;
		config.resizable = false;
		config.vSyncEnabled = false;
		config.foregroundFPS = 120; // Setting to 0 disables foreground fps throttling
		config.backgroundFPS = 120; // Setting to 0 disables background fps throttling
		new LwjglApplication(new Resourcer(), config);
	}
}
