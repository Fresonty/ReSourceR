package com.leon.resourcer.sprites.units;

import com.badlogic.gdx.ai.GdxAI;
import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.BinaryHeap;
import com.leon.resourcer.screens.PlayScreen;

/**
 * This is a source file from ReSourceR.
 * Created by Leon on 02.07.2016.
 */
public abstract class Unit extends Sprite {
    private World world;
    public Body b2dBody;
    public GraphPath<BinaryHeap.Node> foundPath;
    private PlayScreen screen;

    protected TextureRegion region;

    public Unit(String region ,PlayScreen screen, int x, int y) {
        super(screen.unitAtlas.findRegion(region));
        this.region = new TextureRegion(getTexture(), 0, 0, 8, 8);
        this.region.getTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        this.screen = screen;
        setBounds(0, 0, 8, 8);
        setRegion(this.region);
        this.world = screen.getWorld();
        defineB2dBody(x, y);
        foundPath = new DefaultGraphPath<BinaryHeap.Node>();
        screen.allUnits.add(this);
    }

    private void defineB2dBody(int x, int y) {
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(x, y);

        CircleShape shape = new CircleShape();
        shape.setRadius(4);
        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;

        b2dBody = world.createBody(bdef);
        b2dBody.createFixture(fdef);
        b2dBody.setLinearDamping(20f);
    }

    public void update(float delta) {
        if (foundPath != null && foundPath.getCount() > 1) {
            moveFoundPath();
        }
        setPosition(b2dBody.getPosition().x - getWidth() / 2, b2dBody.getPosition().y - getHeight() / 2);
    }

    public void moveFoundPath() {
        for (int node = 0; node < foundPath.getCount(); node++) {
            BinaryHeap.Node pathNode = foundPath.get(node);
            TiledMapTileLayer layer = (TiledMapTileLayer) screen.getMap().getLayers().get(0);
            Vector2 cellPos = screen.tiledNodeManager.getCellPosFromNode(pathNode);
            layer.setCell((int) cellPos.x, (int) cellPos.y, null);
        }
    }
}