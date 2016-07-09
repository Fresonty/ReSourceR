package com.leon.resourcer.ai;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.BinaryHeap;

/**
 * This is a source file from ReSourceR.
 * Created by Leon on 07.07.2016.
 */
public class TiledNodeCreator {
    public TiledNodeCreator(TiledNodeManager manager) {
        int nodesWidth = manager.getNodesWidth();
        int nodesHeight = manager.getNodesHeight();

        Array<TiledNode> nodes = new Array<TiledNode>(nodesWidth * nodesHeight);
        for (int row = 0; row < nodesHeight; row++) {
            for (int node = 0; node < nodesWidth; node++) {
                TiledNode temp = new TiledNode(node + nodesWidth * row, true);
                nodes.add(new TiledNode(temp.getValue(), manager.evalPassable(temp)));
            }
        }
        manager.setNodes(nodes);
    }
}
