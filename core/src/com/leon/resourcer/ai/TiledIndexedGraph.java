package com.leon.resourcer.ai;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultConnection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.BinaryHeap;
import com.leon.resourcer.screens.PlayScreen;

/**
 * This is a source file from ReSourceR.
 * Created by Leon on 07.07.2016.
 */
public class TiledIndexedGraph implements IndexedGraph<BinaryHeap.Node> {
    private TiledNodeManager manager;
    private Array<BinaryHeap.Node> nodes;

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

        if (node.getValue() - manager.getNodesWidth() >= 0) {
            BinaryHeap.Node topNode = nodes.get((int) node.getValue() - manager.getNodesWidth());
            connections.add(new DefaultConnection<BinaryHeap.Node>(node, topNode));
        }
        if (node.getValue() % manager.getNodesWidth() - 1 >= 0) {
            BinaryHeap.Node leftNode = nodes.get((int) node.getValue() - 1);
            connections.add(new DefaultConnection<BinaryHeap.Node>(node, leftNode));
        }
        if (node.getValue() + manager.getNodesWidth() < nodes.size) {
            BinaryHeap.Node bottomNode = nodes.get((int) node.getValue() + manager.getNodesWidth());
            connections.add(new DefaultConnection<BinaryHeap.Node>(node, bottomNode));
        }
        if (node.getValue() % manager.getNodesWidth() + 1 < manager.getNodesWidth()) {
            BinaryHeap.Node rightNode = nodes.get((int) node.getValue() + 1);
            connections.add(new DefaultConnection<BinaryHeap.Node>(node, rightNode));
        }

        return connections;
    }
}