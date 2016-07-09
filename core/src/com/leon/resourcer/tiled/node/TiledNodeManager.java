package com.leon.resourcer.tiled.node;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.leon.resourcer.tiled.TiledWorldManager;
import com.leon.resourcer.tiled.node.utils.TiledNode;
import com.leon.resourcer.tiled.node.utils.TiledNodeCreator;
import com.leon.resourcer.tiled.node.utils.TiledNodeIndexedGraph;
import com.leon.resourcer.tiled.node.utils.TiledNodeManhattanHeuristic;

/**
 * This is a source file from ReSourceR.
 * Created by Leon on 07.07.2016.
 *
 * This class contains all ai/node necessary objects and variables
 */
public class TiledNodeManager {
    private TiledWorldManager manager;

    private TiledNodeCreator tiledNodeCreator;
    private TiledNodeIndexedGraph tiledNodeIndexedGraph;
    private TiledNodeManhattanHeuristic tiledNodeManhattanHeuristic;
    private IndexedAStarPathFinder<TiledNode> indexedAStarPathFinder;

    private Array<TiledNode> nodes;

    private TiledMap map;


    private int nodesWidth = 0;
    private int nodesHeight = 0;

    public TiledNodeManager(TiledWorldManager manager) {
        this.manager = manager;
        map = manager.getMap();

        nodesWidth = map.getProperties().get("width").hashCode();
        nodesHeight = map.getProperties().get("height").hashCode();

        tiledNodeCreator = new TiledNodeCreator(this);
        tiledNodeIndexedGraph = new TiledNodeIndexedGraph(this);
        tiledNodeManhattanHeuristic = new TiledNodeManhattanHeuristic(this);
        indexedAStarPathFinder = new IndexedAStarPathFinder<TiledNode>(tiledNodeIndexedGraph);
    }

    public void findPath(TiledNode start, TiledNode end, GraphPath<TiledNode> outPath) {
        outPath.clear();
        indexedAStarPathFinder.searchNodePath(start, end, tiledNodeManhattanHeuristic, outPath);
    }

    public Array<TiledNode> getNodes() {
        return nodes;
    }

    public void setNodes(Array<TiledNode> nodes) {
        this.nodes = nodes;
    }

    public int getNodesWidth() {
        return nodesWidth;
    }

    public int getNodesHeight() {
        return nodesHeight;
    }

    public TiledNode getNodeFromCellPos(Vector2 pos) {
        return nodes.get((int) (pos.x + pos.y * nodesWidth));
    }

    public Vector2 getCellPosFromNode(TiledNode node) {
        return new Vector2((int) (node.getValue() % nodesWidth), (int) (node.getValue() / nodesWidth));
    }

    public boolean evalNodePassable(TiledNode node) {
        Vector2 nodePos = getCellPosFromNode(node);
        for(int layer = 0; layer < manager.getUnPassableLayers().size; layer++) {
            if (manager.getUnPassableLayers().get(layer).getCell((int) nodePos.x, (int) nodePos.y) != null) {
                return false;
            }
        }
        return true;
    }

    public void reEvalNodePassable(TiledNode node) {
        if (evalNodePassable(node)) nodes.set((int) node.getValue(), new TiledNode(node.getValue(), true));
        else nodes.set((int) node.getValue(), new TiledNode(node.getValue(), false));
    }
}
