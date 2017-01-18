package com.leon.resourcer.sprites.units;

import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.steer.Limiter;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.PrioritySteering;
import com.badlogic.gdx.ai.steer.limiters.LinearLimiter;
import com.badlogic.gdx.ai.utils.Location;
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
import com.leon.resourcer.tiled.node.utils.TiledNode;
import com.leon.resourcer.screens.PlayScreen;

/**
 * This is a source file from ReSourceR.
 * Created by Leon on 02.07.2016.
 */
public abstract class Unit extends Sprite implements Steerable<Vector2> {
    private World world;
    private Body b2dBody;
    private GraphPath<TiledNode> foundPath;
    private PlayScreen screen;

    private TextureRegion region;

    // Steering
    private SteeringBehavior<Vector2> steeringBehavior;
    private Limiter limiter;
    private SteeringAcceleration<Vector2> steeringAcceleration;

    public Unit(String region , PlayScreen screen, int x, int y) {
        super(screen.unitAtlas.findRegion(region));
        this.region = new TextureRegion(getTexture(), 0, 0, 8, 8);
        this.region.getTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        this.screen = screen;
        setBounds(0, 0, 8, 8);
        setRegion(this.region);
        this.world = screen.getWorld();
        defineB2dBody(x, y);
        foundPath = new DefaultGraphPath<TiledNode>();
        screen.allUnits.add(this);

        steeringBehavior = new Arrive<Vector2>(this);
        limiter = new LinearLimiter(2f, 2f);
        steeringBehavior.setLimiter(limiter);
        steeringAcceleration = new SteeringAcceleration<Vector2>(new Vector2(0, 0));
        steeringBehavior.calculateSteering(steeringAcceleration);
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

    public Body getB2dBody() {
        return b2dBody;
    }

    public GraphPath<TiledNode> getFoundPath() {
        return foundPath;
    }

    public void moveFoundPath() {
        for (int node = 0; node < foundPath.getCount(); node++) {
            TiledNode pathNode = foundPath.get(node);
            TiledMapTileLayer layer = (TiledMapTileLayer) screen.getMap().getLayers().get(0);
            Vector2 cellPos = screen.tiledWorldManager.tiledNodeManager.getCellPosFromNode(pathNode);
            layer.setCell((int) cellPos.x, (int) cellPos.y, null);
        }
    }

    @Override
    public void setMaxAngularAcceleration(float maxAngularAcceleration) {

    }

    @Override
    public float getZeroLinearSpeedThreshold() {
        return 0;
    }

    @Override
    public void setZeroLinearSpeedThreshold(float value) {

    }

    @Override
    public float getMaxLinearSpeed() {
        return 0;
    }

    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed) {

    }

    @Override
    public float getMaxLinearAcceleration() {
        return 0;
    }

    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration) {

    }

    @Override
    public float getMaxAngularSpeed() {
        return 0;
    }

    @Override
    public void setMaxAngularSpeed(float maxAngularSpeed) {

    }

    @Override
    public float getMaxAngularAcceleration() {
        return 0;
    }

    @Override
    public Vector2 getLinearVelocity() {
        return null;
    }

    @Override
    public float getAngularVelocity() {
        return 0;
    }

    @Override
    public float getBoundingRadius() {
        return 0;
    }

    @Override
    public boolean isTagged() {
        return false;
    }

    @Override
    public void setTagged(boolean tagged) {

    }

    @Override
    public Vector2 getPosition() {
        return null;
    }

    @Override
    public float getOrientation() {
        return 0;
    }

    @Override
    public void setOrientation(float orientation) {

    }

    @Override
    public float vectorToAngle(Vector2 vector) {
        return 0;
    }

    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle) {
        return null;
    }

    @Override
    public Location<Vector2> newLocation() {
        return null;
    }
}