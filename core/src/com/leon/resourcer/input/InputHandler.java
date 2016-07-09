package com.leon.resourcer.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultConnection;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.BinaryHeap;
import com.leon.resourcer.Resourcer;
import com.leon.resourcer.ai.TiledNode;
import com.leon.resourcer.screens.PlayScreen;
import com.leon.resourcer.sprites.units.Builder;
import com.leon.resourcer.sprites.units.Unit;

/**
 * This is a source file from ReSourceR.
 * Created by Leon on 03.07.2016.
 */
public class InputHandler {
    private PlayScreen screen;
    private TiledMap map;
    private Vector2 cellPosAtMouse;
    private Vector2 relativeMousePos;
    private Vector2 selectedCellPos;
    private TiledMapTileLayer.Cell selectedGroundCell;
    private TiledMapTileLayer.Cell selectedObstacleCell;
    private TiledMapTileLayer.Cell selectedBuildingCell;
    private Unit selectedUnit;
    private TiledMapTileLayer groundLayer;
    private TiledMapTileLayer obstaclesLayer;
    private TiledMapTileLayer buildingsLayer;

    public DefaultGraphPath<BinaryHeap.Node> newFoundPath;

    public InputHandler(PlayScreen screen) {
        this.screen = screen;
        map = screen.getMap();
        cellPosAtMouse = null;
        relativeMousePos = null;
        selectedCellPos = null;
        selectedGroundCell = null;
        selectedObstacleCell = null;
        selectedBuildingCell = null;
        selectedUnit = null;
        groundLayer = (TiledMapTileLayer) map.getLayers().get("ground");
        obstaclesLayer = (TiledMapTileLayer) map.getLayers().get("obstacles");
        buildingsLayer = (TiledMapTileLayer) map.getLayers().get("buildings");

        newFoundPath = new DefaultGraphPath<BinaryHeap.Node>();
    }

    public void handleInput(float delta) {
        // Mouse selection
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            relativeMousePos = getRelativeMousePos();
            cellPosAtMouse = getCellPos(relativeMousePos);

            selectedCellPos = new Vector2(cellPosAtMouse);
            TiledMapTileLayer.Cell newSelectedGroundCell = groundLayer.getCell((int) cellPosAtMouse.x, (int) cellPosAtMouse.y);
            TiledMapTileLayer.Cell newSelectedObstacleCell = obstaclesLayer.getCell((int) cellPosAtMouse.x, (int) cellPosAtMouse.y);
            TiledMapTileLayer.Cell newSelectedBuildingCell = buildingsLayer.getCell((int) cellPosAtMouse.x, (int) cellPosAtMouse.y);
            Unit newSelectedUnit = getUnit();

            //if (newSelectedGroundCell != null)
            selectedGroundCell = newSelectedGroundCell;
            //if (newSelectedObstacleCell != null)
            selectedObstacleCell = newSelectedObstacleCell;
            //if (newSelectedBuildingCell != null)
            selectedBuildingCell = newSelectedBuildingCell;

            if (newSelectedUnit != null)
                selectedUnit = newSelectedUnit;
        }
        // Commands
        // Build
        if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
            if(selectedBuildingCell == null) {
                if(selectedObstacleCell == null) {
                    TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell().setTile(map.getTileSets().getTileSet("buildingtiles").getTile(9));
                    buildingsLayer.setCell((int) cellPosAtMouse.x, (int) cellPosAtMouse.y, cell);
                    screen.tiledNodeManager.reEvalPassable(screen.tiledNodeManager.getNodeFromCellPos(new Vector2((int) cellPosAtMouse.x, (int) cellPosAtMouse.y)));
                    screen.b2dWC.addTileBody((int) cellPosAtMouse.x, (int) cellPosAtMouse.y, buildingsLayer);
                }
            }
        }
        // New Unit
        if (Gdx.input.isKeyJustPressed(Input.Keys.N)) {
            if(selectedBuildingCell != null) {
                new Builder(screen, (int) relativeMousePos.x, (int) relativeMousePos.y);
            }
        }
        // Move
        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            if (selectedUnit != null) {
                // Get the Node from the node list, don't create a new one with that value. RIP 2 days for debugging
                TiledNode startNode = screen.tiledNodeManager.getNodeFromCellPos(getCellPos(selectedUnit.b2dBody.getPosition()));
                TiledNode endNode = screen.tiledNodeManager.getNodeFromCellPos(cellPosAtMouse);
                screen.tiledNodeManager.findPath(startNode, endNode, selectedUnit.foundPath);
            }
        }

        // Camera
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            screen.gameCam.position.add(new Vector3(0, 1, 0));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            screen.gameCam.position.add(new Vector3(1, 0, 0));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            screen.gameCam.position.add(new Vector3(-1, 0, 0));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            screen.gameCam.position.add(new Vector3(0, -1, 0));
        }
    }
    private Vector2 getRelativeMousePos () {
        // TODO: Fix this calculation
        float relativeMouseX = (Gdx.input.getX()) / (screen.gamePort.getScreenWidth() / Resourcer.V_WIDTH) + screen.gameCam.position.x - (screen.gamePort.getScreenWidth()) / (screen.gamePort.getScreenWidth() / Resourcer.V_WIDTH) / 2;
        float relativeMouseY = (screen.gamePort.getScreenHeight() - Gdx.input.getY()) / (screen.gamePort.getScreenHeight() / Resourcer.V_HEIGHT) + screen.gameCam.position.y - (screen.gamePort.getScreenHeight()) / (screen.gamePort.getScreenHeight() / Resourcer.V_HEIGHT) / 2;
        return new Vector2(relativeMouseX, relativeMouseY);
    }

    private Vector2 getCellPos(Vector2 pos) {
        return new Vector2((int) (pos.x / map.getProperties().get("tilewidth").hashCode()), (int) (pos.y / map.getProperties().get("tileheight").hashCode()));
    }

    private Unit getUnit() {
        for (Unit unit : screen.allUnits) {
            if (unit.b2dBody.getPosition().x - unit.b2dBody.getFixtureList().first().getShape().getRadius() < getRelativeMousePos().x && unit.b2dBody.getPosition().x + unit.b2dBody.getFixtureList().first().getShape().getRadius() > getRelativeMousePos().x) {
                if (unit.b2dBody.getPosition().y - unit.b2dBody.getFixtureList().first().getShape().getRadius() < getRelativeMousePos().y && unit.b2dBody.getPosition().y + unit.b2dBody.getFixtureList().first().getShape().getRadius() > getRelativeMousePos().y) {
                    return unit;
                }
            }
        }
        return null;
    }
}
