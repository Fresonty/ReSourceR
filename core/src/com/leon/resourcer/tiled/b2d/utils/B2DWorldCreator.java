package com.leon.resourcer.tiled.b2d.utils;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.leon.resourcer.tiled.b2d.B2DWorldManager;

/**
 * This is a source file from ReSourceR.
 * Created by Leon on 02.07.2016.
 */
public class B2DWorldCreator {
    private B2DWorldManager manager;

    public B2DWorldCreator(B2DWorldManager manager) {
        this.manager = manager;
    }

    public void generateTileBodies(TiledMapTileLayer layer) {
        for (int row = 0; row < layer.getHeight(); row++) {
            for (int column = 0; column < layer.getWidth(); column++) {
                TiledMapTileLayer.Cell tileCell = layer.getCell(column, row);
                // Make sure tileCell isn't empty
                if (tileCell != null) {
                    manager.addTileBody(column, row, layer);
                }
            }
        }
    }
}
