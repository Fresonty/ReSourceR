package com.leon.resourcer.tiled.node.utils;

import com.badlogic.gdx.utils.Array;
import com.leon.resourcer.tiled.node.TiledNodeManager;

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
                nodes.add(new TiledNode(temp.getValue(), manager.evalNodePassable(temp)));
            }
        }
        manager.setNodes(nodes);
    }
}
