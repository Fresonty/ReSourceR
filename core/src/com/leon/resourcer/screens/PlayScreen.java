package com.leon.resourcer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.BinaryHeap;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.leon.resourcer.Resourcer;
import com.leon.resourcer.ai.IndexedNodeGraph;
import com.leon.resourcer.sprites.units.Unit;
import com.leon.resourcer.tools.B2DWorldCreator;
import com.leon.resourcer.input.InputHandler;
import com.leon.resourcer.ai.NodeCreator;

/**
 * This is a source file from ReSourceR.
 * Created by Leon on 02.07.2016.
 */
public class PlayScreen implements Screen {
    // Gdx
    private Resourcer game;
    public OrthographicCamera gameCam;
    public Viewport gamePort;

    // Tiled map
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    // B2D
    private World world;
    private Box2DDebugRenderer b2dr;
    public B2DWorldCreator b2dWC;

    // Textures
    public TextureAtlas unitAtlas;

    // Game specific
    private InputHandler inputHandler;

    // Units
    public Array<Unit> allUnits;
    public NodeCreator nodeCreator;
    public Array<BinaryHeap.Node> nodes;
    public IndexedNodeGraph indexedNodeGraph;

    private FPSLogger fpsLogger;

    public PlayScreen(Resourcer game) {
        // Gdx
        this.game = game;
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(Resourcer.V_WIDTH, Resourcer.V_HEIGHT, gameCam);
        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        // Tiled map
        TmxMapLoader mapLoader = new TmxMapLoader();
        map = mapLoader.load("levels/level3.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);

        // B2D
        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();
        b2dWC = new B2DWorldCreator(world, map);

        // Textures
        unitAtlas = new TextureAtlas("img/units.txt");

        // Game specific
        inputHandler = new InputHandler(this);

        // Units
        allUnits = new Array<Unit>();
        nodeCreator = new NodeCreator(world, map);
        nodes = nodeCreator.getNodes();
        indexedNodeGraph = new IndexedNodeGraph(nodeCreator, nodes, this);

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

        for (Unit unit : allUnits) {
            unit.update(delta);
        }

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
        for (Unit unit : allUnits) {
            unit.draw(game.batch);
        }
        game.batch.end();

        b2dr.render(world, gameCam.combined);
    }

    public World getWorld() {
        return world;
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
        game.batch.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
    }
}
