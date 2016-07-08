package com.leon.resourcer.ai;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultConnection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.BinaryHeap;

/**
 * This is a source file from ReSourceR.
 * Created by Leon on 07.07.2016.
 */
public class TiledIndexedGraph implements IndexedGraph<BinaryHeap.Node> {
    private TiledNodeManager manager;
    private Array<BinaryHeap.Node> nodes;
    int[][] neighbourNodes = {
            {-1, -1}, {0, -1}, {1, -1},
            {-1, 0},           {1, 0},
            {-1, 1}, {0, 1}, {1, 1}
    };

    public TiledIndexedGraph(TiledNodeManager manager) {
        this.manager = manager;
        this.nodes = manager.getNodes();
    }

    @Override
    public int getIndex(BinaryHeap.Node node) {
        return (int) node.getValue();
    }

    @Override
    public int getNodeCount() {
        return nodes.size;
    }

    @Override
    public Array<Connection<BinaryHeap.Node>> getConnections(BinaryHeap.Node fromNode) {
        Array<Connection<BinaryHeap.Node>> connections = new Array<Connection<BinaryHeap.Node>>();
        BinaryHeap.Node node = nodes.get((int) fromNode.getValue());

        for (int[] neighbour : neighbourNodes) {
            if (getPassable(neighbour)) {
                if (node.getValue() % manager.getNodesWidth() + neighbour[0] >= 0 && node.getValue() % manager.getNodesWidth() + neighbour[0] < manager.getNodesWidth()) {
                    if (node.getValue() + neighbour[1] * manager.getNodesWidth() >= 0 && node.getValue() + neighbour[1] * manager.getNodesWidth() < nodes.size) {
                        BinaryHeap.Node toNode = nodes.get((int) (node.getValue() + neighbour[0] + neighbour[1] * manager.getNodesWidth()));
                        if (neighbour[0] == neighbour[1])
                            connections.add(new DiagonalConnection<BinaryHeap.Node>(node, toNode));
                        else connections.add(new DefaultConnection<BinaryHeap.Node>(node, toNode));
                    }
                }
            }
        }
        return connections;
    }

    private boolean getPassable(int[] node) {


        return true;
    }
}