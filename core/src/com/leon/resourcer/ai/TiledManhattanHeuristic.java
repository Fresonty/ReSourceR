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

    public TiledManhattanHeuristic(TiledNodeManager manager) {
        this.manager = manager;
    }

    @Override
    public float estimate(BinaryHeap.Node node, BinaryHeap.Node endNode) {
        return Math.abs((node.getValue() % 50) - (endNode.getValue() % 50) + (node.getValue() / 50 - (endNode.getValue() / 50)));
    }
}
