package com.leon.resourcer.sprites.units;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.leon.resourcer.screens.PlayScreen;

/**
 * This is a source file from ReSourceR.
 * Created by Leon on 02.07.2016.
 */
public abstract class Unit extends Sprite {
    protected World world;
    public Body b2dBody;

    protected TextureRegion region;

    public Unit(String region ,PlayScreen screen, int x, int y) {
        super(screen.unitAtlas.findRegion(region));
        this.region = new TextureRegion(getTexture(), 0, 0, 8, 8);
        this.region.getTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        setBounds(0, 0, 8, 8);
        setRegion(this.region);
        this.world = screen.getWorld();
        defineB2dBody(x, y);

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
        setPosition(b2dBody.getPosition().x - getWidth() / 2, b2dBody.getPosition().y - getHeight() / 2);
    }
}
