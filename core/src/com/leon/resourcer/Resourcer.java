package com.leon.resourcer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.leon.resourcer.screens.PlayScreen;

public class Resourcer extends Game {
	public SpriteBatch batch;
	public static final int V_WIDTH = 800;
	public static final int V_HEIGHT = 404;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
