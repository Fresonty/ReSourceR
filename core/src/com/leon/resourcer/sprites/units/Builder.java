package com.leon.resourcer.sprites.units;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.leon.resourcer.screens.PlayScreen;

/**
 * This is a source file from ReSourceR.
 * Created by Leon on 02.07.2016.
 */
public class Builder extends Unit {
    public Builder(PlayScreen screen, int x, int y) {
        super("builder",screen, x, y);
    }
}
