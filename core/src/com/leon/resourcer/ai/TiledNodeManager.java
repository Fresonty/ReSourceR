package com.leon.resourcer.ai;

import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.BinaryHeap;

/**
 * This is a source file from ReSourceR.
 * Created by Leon on 07.07.2016.
 *
 * This class contains all Node necessary objects and variables
 */
public class TiledNodeManager {
    private TiledNodeCreator tiledNodeCreator;
    private TiledIndexedGraph tiledIndexedGraph;
    private TiledManhattanHeuristic tiledManhattanHeuristic;
    private IndexedAStarPathFinder<BinaryHeap.Node> indexedAStarPathFinder;

    private Array<BinaryHeap.Node> nodes;

    private TiledMap map;

    private int nodesWidth = 0;
    private int nodesHeight = 0;

    public TiledNodeManager(TiledMap map) {
        this.map = map;

        nodesWidth = map.getProperties().get("width").hashCode();
        nodesHeight = map.getProperties().get("height").hashCode();

        tiledNodeCreator = new TiledNodeCreator(this);
        tiledIndexedGraph = new TiledIndexedGraph(this);
        tiledManhattanHeuristic = new TiledManhattanHeuristic(this);
        indexedAStarPathFinder = new IndexedAStarPathFinder<BinaryHeap.Node>(tiledIndexedGraph, true);
    }

    public void findPath(BinaryHeap.Node start, BinaryHeap.Node end, GraphPath<BinaryHeap.Node> outPath) {
        outPath.clear();
        if (indexedAStarPathFinder.searchNodePath(start, end, tiledManhattanHeuristic, outPath)) System.out.println("New Path: " + outPath.getCount());
    }

    public Array<BinaryHeap.Node> getNodes() {
        return nodes;
    }

    public void setNodes(Array<BinaryHeap.Node> nodes) {
        this.nodes = nodes;
    }

    public int getNodesWidth() {
        return nodesWidth;
    }

    public int getNodesHeight() {
        return nodesHeight;
    }

    public BinaryHeap.Node getNodeFromCellPos(Vector2 pos) {
        return nodes.get((int) (pos.x + pos.y * nodesWidth));
    }

    public Vector2 getCellPosFromNode(BinaryHeap.Node node) {
        return new Vector2((int) (node.getValue() % nodesWidth), (int) (node.getValue() / nodesWidth));
    }
}
