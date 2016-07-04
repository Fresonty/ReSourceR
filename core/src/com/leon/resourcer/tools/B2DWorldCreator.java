package com.leon.resourcer.tools;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * This is a source file from ReSourceR.
 * Created by Leon on 02.07.2016.
 */
public class B2DWorldCreator {
    private World world;
    private TiledMap map;
    private BodyDef bdef;
    private  PolygonShape shape;
    private FixtureDef fdef;
    private Body body;

    public B2DWorldCreator(World world, TiledMap map) {
        this.world = world;
        this.map = map;
        bdef = new BodyDef();
        shape = new PolygonShape();
        fdef = new FixtureDef();

        TiledMapTileLayer obstacleLayer = (TiledMapTileLayer) map.getLayers().get("obstacles");
        generateTileBodies(obstacleLayer);
    }

    public void generateTileBodies(TiledMapTileLayer layer) {
        for (int row = 0; row < layer.getHeight(); row++) {
            for (int column = 0; column < layer.getWidth(); column++) {
                TiledMapTileLayer.Cell tileCell = layer.getCell(column, row);
                // Make sure tileCell isn't empty
                if (tileCell != null) {
                    addTileBody(column, row, layer);
                }
            }
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
