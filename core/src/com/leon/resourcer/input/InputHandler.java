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
    private Vector2 absCellPosAtMouse;
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
        absCellPosAtMouse = null;
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
            cellPosAtMouse = getCellPosAtMouse();
            absCellPosAtMouse = new Vector2(cellPosAtMouse.x * groundLayer.getTileWidth(), cellPosAtMouse.y * groundLayer.getTileHeight());

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

            // System.out.println(screen.nodes);

            /*
            System.out.println(selectedGroundCell);
            System.out.println(selectedObstacleCell);
            System.out.println(selectedBuildingCell);
            */
            //System.out.println(selectedUnit);
            //System.out.println(screen.nodeCreator.getNodeFromCellPos(cellPosAtMouse));

        }
        // Commands
        // Build
        if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
            if(selectedBuildingCell == null) {
                if(selectedObstacleCell == null) {
                    TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell().setTile(map.getTileSets().getTileSet("buildingtiles").getTile(9));
                    buildingsLayer.setCell((int) cellPosAtMouse.x, (int) cellPosAtMouse.y, cell);
                    screen.b2dWC.addTileBody((int) cellPosAtMouse.x, (int) cellPosAtMouse.y, buildingsLayer);
                }
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.N)) {
            if(selectedBuildingCell != null) {
                new Builder(screen, (int) absCellPosAtMouse.x, (int) absCellPosAtMouse.y);
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            if (selectedUnit != null) {

                /*
                //for (int node = 0; node < screen.nodes.size; node ++) {
                    //System.out.println(screen.nodes.get(node));
                //}

                BinaryHeap.Node startNode = new BinaryHeap.Node(cellPosAtMouse.x + cellPosAtMouse.y * screen.tiledNodeCreator.getNodesWidth());
                //System.out.println(cellPosAtMouse.y);
                System.out.println("StartNode: " + startNode);
                BinaryHeap.Node endNode = new BinaryHeap.Node(cellPosAtMouse.x + 3 + (cellPosAtMouse.y) * screen.tiledNodeCreator.getNodesWidth());
                System.out.println("EndNode: " + endNode);


                System.out.println("Estimate: " + screen.tiledManhattanHeuristic.estimate(startNode, endNode));
                //System.out.println(screen.indexedNodeGraph);
                //System.out.println(screen.indexedNodeGraph.getNodeCount());
                newFoundPath.clear();
                //newFoundPath.add(new BinaryHeap.Node(7));

                screen.indexedAStarPathFinder.searchNodePath(startNode, endNode, screen.tiledManhattanHeuristic, newFoundPath);

                System.out.println(screen.indexedAStarPathFinder.metrics.visitedNodes);
                System.out.println(screen.indexedAStarPathFinder.metrics.openListPeak);
                System.out.println(screen.indexedAStarPathFinder.metrics.openListAdditions);
                System.out.println(newFoundPath);
                // System.out.println(selectedUnit.foundPath);
                System.out.println(newFoundPath.getCount());
                System.out.println(newFoundPath.nodes);
                for(BinaryHeap.Node node : newFoundPath) {
                    System.out.print(node);
                }
                for(int node = 0; node < newFoundPath.getCount(); node++) {
                    System.out.println(newFoundPath.get(node));
                }*/

                BinaryHeap.Node startNode = screen.tiledNodeCreator.getNodes().get((int) (cellPosAtMouse.x + cellPosAtMouse.y * screen.tiledNodeCreator.getNodesWidth()));
                System.out.println("StartNode: " + startNode);
                BinaryHeap.Node endNode = screen.tiledNodeCreator.getNodes().get((int) (cellPosAtMouse.x + 3 + (cellPosAtMouse.y) * screen.tiledNodeCreator.getNodesWidth()));
                System.out.println("EndNode: " + endNode);
                screen.findPath(startNode, endNode);
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

    private Vector2 getCellPosAtMouse() {
        float mouseX = getRelativeMousePos().x;
        float mouseY = getRelativeMousePos().y;

        TiledMapTileLayer tiledLayer = (TiledMapTileLayer) map.getLayers().get("ground");

        int cellPosX = (int) (mouseX / tiledLayer.getTileWidth());
        int cellPosY = (int) (mouseY / tiledLayer.getTileHeight());

        return new Vector2(cellPosX, cellPosY);
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
