package com.leon.resourcer.ai;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.BinaryHeap;

/**
 * This is a source file from ReSourceR.
 * Created by Leon on 08.07.2016.
 */
public class TiledNode extends BinaryHeap.Node {
    private boolean passable;
    public TiledNode(float value, boolean passable) {
        super(value);
        this.passable = passable;
    }
    public boolean getPassable() {
        return passable;
    }
}
