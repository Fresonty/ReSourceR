package com.leon.resourcer.ai;

import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.BinaryHeap;

/**
 * This is a source file from ReSourceR.
 * Created by Leon on 07.07.2016.
 */
public class TiledManhattanHeuristic implements Heuristic<BinaryHeap.Node> {
    private TiledNodeManager manager;
    private int nodesWidth;

    public TiledManhattanHeuristic(TiledNodeManager manager) {
        this.manager = manager;
        nodesWidth = manager.getNodesWidth();
    }

    @Override
    public float estimate(BinaryHeap.Node node, BinaryHeap.Node endNode) {
        return Math.abs((node.getValue() % nodesWidth) - (endNode.getValue() % nodesWidth) + (node.getValue() / nodesWidth - (endNode.getValue() / nodesWidth)));
    }
}
