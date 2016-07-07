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

        Array<BinaryHeap.Node> nodes = new Array<BinaryHeap.Node>(nodesWidth * nodesHeight);
        for (int row = 0; row < nodesHeight; row++) {
            for (int node = 0; node < nodesWidth; node++) {
                nodes.add(new BinaryHeap.Node(node + nodesWidth * row));
            }
        }
        manager.setNodes(nodes);
    }
}
