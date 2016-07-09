package com.leon.resourcer.tiled;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.leon.resourcer.tiled.b2d.B2DWorldManager;
import com.leon.resourcer.tiled.node.TiledNodeManager;

/**
 * This is a source file from ReSourceR.
 * Created by Leon on 09.07.2016.
 */
public class TiledWorldManager {
    private TiledMap map;
    private World world;

    public B2DWorldManager b2dWorldManager;
    public TiledNodeManager tiledNodeManager;

    private Array<TiledMapTileLayer> unPassableLayers;

    public TiledWorldManager(TiledMap map, World world) {
        this.map = map;
        this.world = world;

        unPassableLayers = new Array<TiledMapTileLayer>();
        for(int layer = 0; layer < map.getLayers().getCount(); layer++) {
            if(map.getLayers().get(layer).getProperties().get("passable").toString().equals("false")) {
                unPassableLayers.add((TiledMapTileLayer) map.getLayers().get(layer));
            }
        }

        b2dWorldManager = new B2DWorldManager(this);
        tiledNodeManager = new TiledNodeManager(this);
    }

    public TiledMap getMap() {
        return map;
    }

    public World getWorld() {
        return world;
    }

    public Array<TiledMapTileLayer> getUnPassableLayers() {
        return unPassableLayers;
    }
}
