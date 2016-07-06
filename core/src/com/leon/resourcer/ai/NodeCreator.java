package com.leon.resourcer.ai;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.BinaryHeap;

/**
 * This is a source file from ReSourceR.
 * Created by Leon on 05.07.2016.
 */
public class NodeCreator {
    private Array<BinaryHeap.Node> nodes;
    private World world;
    private TiledMap map;
    private TiledMapTileLayer layer;
    private int nodesWidth = 0;
    private int nodesHeight = 0;

    public NodeCreator(World world, TiledMap map) {
        this.world = world;
        this.map = map;
        this.layer = (TiledMapTileLayer) map.getLayers().get(0);
        this.nodesWidth = this.layer.getWidth();
        this.nodesHeight = this.layer.getHeight();

        this.nodes = new Array<BinaryHeap.Node>(this.nodesWidth * this.nodesHeight);
        for (int row = 0; row < this.nodesHeight; row++) {
            for (int node = 0; node < this.nodesWidth; node++) {
                nodes.add(new BinaryHeap.Node(node + nodesWidth * row));
            }
        }
    }
    public Array<BinaryHeap.Node> getNodes() {
        return this.nodes;
    }

    public int getNodesWidth() {
        return this.nodesWidth;
    }

    public int getNodesHeight() {
        return this.nodesHeight;
    }

    public BinaryHeap.Node getNodeFromPos(Vector2 pos) {
        return this.nodes.get((int) (pos.x + pos.y * this.nodesWidth));
    }

    public Vector2 getPosFromNode(BinaryHeap.Node node) {
        return new Vector2((int) (node.getValue() % this.nodesWidth), (int) (node.getValue() / this.nodesWidth));
    }
}
