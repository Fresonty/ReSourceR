package com.leon.resourcr.tools;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Leon on 02.07.2016.
 */
public class B2DWorldCreator {
    public B2DWorldCreator(World world, TiledMap map) {
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        TiledMapTileLayer tiledLayer = (TiledMapTileLayer) map.getLayers().get("obstacles");
        for (int row = 0; row < tiledLayer.getHeight(); row++) {
            for (int column = 0; column < tiledLayer.getWidth(); column++) {
                TiledMapTileLayer.Cell tileCell = tiledLayer.getCell(column, row);
                // Make sure tileCell isn't empty
                if (tileCell != null) {
                    Rectangle rect = new Rectangle(column * tiledLayer.getTileWidth(), row * tiledLayer.getTileHeight(), tiledLayer.getTileWidth(), tiledLayer.getTileHeight());

                    bdef.type = BodyDef.BodyType.StaticBody;
                    bdef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);

                    shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
                    fdef.shape = shape;

                    body = world.createBody(bdef);
                    body.createFixture(fdef);
                }
            }
        }
    }
}
