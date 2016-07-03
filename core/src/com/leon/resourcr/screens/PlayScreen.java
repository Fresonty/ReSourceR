package com.leon.resourcr.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.leon.resourcr.Resourcr;
import com.leon.resourcr.sprites.units.Builder;
import com.leon.resourcr.sprites.units.Unit;
import com.leon.resourcr.tools.B2DWorldCreator;
import com.leon.resourcr.tools.InputHandler;

/**
 * Created by Leon on 02.07.2016.
 */
public class PlayScreen implements Screen {
    // Gdx
    private Resourcr game;
    public OrthographicCamera gameCam;
    public Viewport gamePort;

    // Tiled map
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    // B2D
    private World world;
    private Box2DDebugRenderer b2dr;

    // Textures
    public TextureAtlas atlas;

    // Mobs
    private Builder builder1;

    // Game specific
    private InputHandler inputHandler;

    // Units
    public Array<Unit> allUnits;

    private FPSLogger fpsLogger;

    public PlayScreen(Resourcr game) {
        // Gdx
        this.game = game;
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(Resourcr.V_WIDTH, Resourcr.V_HEIGHT, gameCam);
        // gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        // Tiled map
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level2.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);

        // B2D
        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();
        new B2DWorldCreator(world, map);

        // Textures
        atlas = new TextureAtlas("player.txt");

        // Game specific
        inputHandler = new InputHandler(this);
        allUnits = new Array<Unit>();

        // Units
        builder1 = new Builder(world, this, (int) gamePort.getWorldWidth() / 2, (int) gamePort.getWorldHeight() / 2);
        allUnits.add(builder1);

        fpsLogger = new FPSLogger();
    }

    @Override
    public void show() {

    }

    public TiledMap getMap() {
        return map;
    }

    public void handleInput(float delta) {
        inputHandler.handleInput(delta);
    }

    public void update(float delta) {
        world.step(1 / 120f, 6, 2);
        fpsLogger.log();
        handleInput(delta);

        builder1.update(delta);

        gameCam.update();
        renderer.setView(gameCam);
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // World renderer
        renderer.render();

        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        builder1.draw(game.batch);
        game.batch.end();

        b2dr.render(world, gameCam.combined);
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
    }
}
