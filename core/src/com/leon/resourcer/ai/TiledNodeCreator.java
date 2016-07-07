package com.leon.resourcer.ai;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.BinaryHeap;

/**
 * This is a source file from ReSourceR.
 * Created by Leon on 07.07.2016.
 */
public class TiledNodeCreator {
    private Array<BinaryHeap.Node> nodes;
    private World world;
    private TiledMap map;
    private TiledMapTileLayer layer;
    private int nodesWidth = 0;
    private int nodesHeight = 0;

    public TiledNodeCreator(TiledMap map, World world) {
        this.map = map;
        this.world = world;

        layer = (TiledMapTileLayer) map.getLayers().get(0);
        nodesWidth = layer.getWidth();
        nodesHeight = layer.getHeight();

        nodes = new Array<BinaryHeap.Node>(nodesWidth * nodesHeight);
        for (int row = 0; row < nodesHeight; row++) {
            for (int node = 0; node < nodesWidth; node++) {
                nodes.add(new BinaryHeap.Node(node + nodesWidth * row));
            }
        }
    }

    public Array<BinaryHeap.Node> getNodes() {
        return nodes;
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
