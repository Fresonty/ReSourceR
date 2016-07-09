package com.leon.resourcer.tiled.node.utils;

import com.badlogic.gdx.ai.pfa.DefaultConnection;

/**
 * This is a source file from ReSourceR.
 * Created by Leon on 08.07.2016.
 */
public class TiledNodeDiagonalConnection<N> extends DefaultConnection<N> {
    public TiledNodeDiagonalConnection(N fromNode, N toNode) {
        super(fromNode, toNode);
    }

    @Override
    public float getCost() {
        return 1.5f;
    }
}
