package com.leon.resourcer.tiled.node.utils;

import com.badlogic.gdx.ai.pfa.Heuristic;
import com.leon.resourcer.tiled.node.TiledNodeManager;

/**
 * This is a source file from ReSourceR.
 * Created by Leon on 07.07.2016.
 */
public class TiledNodeManhattanHeuristic implements Heuristic<TiledNode> {
    private TiledNodeManager manager;
    private int nodesWidth;

    public TiledNodeManhattanHeuristic(TiledNodeManager manager) {
        this.manager = manager;
        nodesWidth = manager.getNodesWidth();
    }

    @Override
    public float estimate(TiledNode node, TiledNode endNode) {
        return Math.abs((node.getValue() % nodesWidth) - (endNode.getValue() % nodesWidth) + (node.getValue() / nodesWidth - (endNode.getValue() / nodesWidth)));
    }
}
