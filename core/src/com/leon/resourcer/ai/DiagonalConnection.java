package com.leon.resourcer.ai;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultConnection;

/**
 * This is a source file from ReSourceR.
 * Created by Leon on 08.07.2016.
 */
public class DiagonalConnection<N> extends DefaultConnection<N> {
    public DiagonalConnection(N fromNode, N toNode) {
        super(fromNode, toNode);
    }

    @Override
    public float getCost() {
        return 1.5f;
    }
}
