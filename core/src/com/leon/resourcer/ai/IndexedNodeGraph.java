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
 * Created by Leon on 05.07.2016.
 */
public class IndexedNodeGraph implements IndexedGraph<BinaryHeap.Node> {
    private NodeCreator nodeCreator;
    private Array<BinaryHeap.Node> nodes;
    private PlayScreen screen;
    private TiledMapTileLayer layer;

    public IndexedNodeGraph(NodeCreator nodeCreator, Array<BinaryHeap.Node> nodes, PlayScreen screen) {
        this.nodeCreator = nodeCreator;
        this.nodes = nodes;
        this.screen = screen;
        this.layer = (TiledMapTileLayer) screen.getMap().getLayers().get(0);
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
        //System.out.println("node: " + node.getValue());
        if (node.getValue() - nodeCreator.getNodesWidth() >= 0) {
            BinaryHeap.Node topNode = nodes.get((int) node.getValue() - nodeCreator.getNodesWidth());
            connections.add(new DefaultConnection<BinaryHeap.Node>(node, topNode));
            //System.out.println("added: " + nodes.get((int) node.getValue() - nodeCreator.getNodesWidth()));
        }
        if (node.getValue() % nodeCreator.getNodesWidth() - 1 >= 0) {
            connections.add(new DefaultConnection<BinaryHeap.Node>(node, nodes.get((int) node.getValue() - 1)));
            //System.out.println("added: " + nodes.get((int) node.getValue() - 1));
        }
        if (node.getValue() + nodeCreator.getNodesWidth() < nodes.size) {
            connections.add(new DefaultConnection<BinaryHeap.Node>(node, nodes.get((int) node.getValue() + nodeCreator.getNodesWidth())));
            //System.out.println("added: " + nodes.get((int) node.getValue() + nodeCreator.getNodesWidth()));
        }
        if (node.getValue() % nodeCreator.getNodesWidth() + 1 < nodeCreator.getNodesWidth()) {
            connections.add(new DefaultConnection<BinaryHeap.Node>(node, nodes.get((int) node.getValue() + 1)));
            //System.out.println("added: " + nodes.get((int) node.getValue() + 1));
        }
        //for (int i = 0; i < connections.size; i++) {
            //System.out.println(connections.get(i).getFromNode().getValue() + " " + connections.get(i).getToNode().getValue());
        //}
        //System.out.println("connections size: " + connections.size);
        // this.layer.setCell((int) nodeCreator.getCellPosFromNode(node).x, (int) nodeCreator.getCellPosFromNode(node).y, null);
        return connections;
    }
}
