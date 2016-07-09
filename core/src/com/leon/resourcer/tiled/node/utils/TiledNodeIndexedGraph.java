package com.leon.resourcer.tiled.node.utils;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultConnection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.utils.Array;
import com.leon.resourcer.tiled.node.TiledNodeManager;

/**
 * This is a source file from ReSourceR.
 * Created by Leon on 07.07.2016.
 */
public class TiledNodeIndexedGraph implements IndexedGraph<TiledNode> {
    private TiledNodeManager manager;
    private Array<TiledNode> nodes;
    int[][] neighbourNodes = {
            {-1, 1}, {0, 1}, {1, 1},
            {-1, 0},           {1, 0},
            {-1, -1}, {0, -1}, {1, -1}
    };

    public TiledNodeIndexedGraph(TiledNodeManager manager) {
        this.manager = manager;
        this.nodes = manager.getNodes();
    }

    @Override
    public int getIndex(TiledNode node) {
        return (int) node.getValue();
    }

    @Override
    public int getNodeCount() {
        return nodes.size;
    }

    @Override
    public Array<Connection<TiledNode>> getConnections(TiledNode fromNode) {
        Array<Connection<TiledNode>> connections = new Array<Connection<TiledNode>>();
        TiledNode node = nodes.get((int) fromNode.getValue());

        for (int[] neighbour : neighbourNodes) {
            // Check if Node is in bounds of map
            TiledNode sideNodeX = nodes.get((int) node.getValue() % manager.getNodesWidth() + neighbour[0]);
            if (sideNodeX.getValue() >= 0 && sideNodeX.getValue() < manager.getNodesWidth() && sideNodeX.getPassable()) {
                TiledNode sideNodeY = nodes.get((int) node.getValue() + neighbour[1] * manager.getNodesWidth());
                if (sideNodeY.getValue() >= 0 && sideNodeY.getValue() < nodes.size && sideNodeY.getPassable()) {
                    TiledNode toNode = nodes.get((int) (node.getValue() + neighbour[0] + neighbour[1] * manager.getNodesWidth()));
                    // Check if Node is passable
                    if (toNode.getPassable()) {
                        // Check if x and y are equal, if so its diagonal
                        if (Math.abs(neighbour[0]) == Math.abs(neighbour[1]))
                            connections.add(new TiledNodeDiagonalConnection<TiledNode>(node, toNode));
                        else connections.add(new DefaultConnection<TiledNode>(node, toNode));
                    }
                }
            }
        }
        return connections;
    }
}