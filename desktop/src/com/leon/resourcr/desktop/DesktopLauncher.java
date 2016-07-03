package com.leon.resourcr.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.leon.resourcr.Resourcr;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1600;
		config.height = 808;
		config.resizable = false;
		config.vSyncEnabled = false;
		config.foregroundFPS = 120; // Setting to 0 disables foreground fps throttling
		config.backgroundFPS = 120; // Setting to 0 disables background fps throttling
		new LwjglApplication(new Resourcr(), config);
	}
}
