package com.leon.resourcer.tiled.b2d;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.leon.resourcer.tiled.b2d.utils.B2DWorldCreator;
import com.leon.resourcer.tiled.TiledWorldManager;

/**
 * This is a source file from ReSourceR.
 * Created by Leon on 09.07.2016.
 */
public class B2DWorldManager {
    private TiledWorldManager manager;

    private World world;
    private TiledMap map;

    private BodyDef bdef;
    private PolygonShape shape;
    private FixtureDef fdef;
    private Body body;

    private B2DWorldCreator b2dWorldCreator;

    public B2DWorldManager(TiledWorldManager manager) {
        this.manager = manager;
        this.map = manager.getMap();
        this.world = manager.getWorld();

        bdef = new BodyDef();
        shape = new PolygonShape();
        fdef = new FixtureDef();

        b2dWorldCreator = new B2DWorldCreator(this);

        for (TiledMapTileLayer layer : manager.getUnPassableLayers()) {
            b2dWorldCreator.generateTileBodies(layer);
        }
    }

    public void addTileBody(int x, int y, TiledMapTileLayer layer) {
        Rectangle rect = new Rectangle(x * layer.getTileWidth(), y * layer.getTileHeight(), layer.getTileWidth(), layer.getTileHeight());

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);

        shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
        fdef.shape = shape;

        body = world.createBody(bdef);
        body.createFixture(fdef);
    }
}
