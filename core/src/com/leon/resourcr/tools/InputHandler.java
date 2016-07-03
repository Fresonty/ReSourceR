package com.leon.resourcr.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.leon.resourcr.Resourcr;
import com.leon.resourcr.screens.PlayScreen;
import com.leon.resourcr.sprites.units.Unit;

/**
 * Created by Leon on 03.07.2016.
 */
public class InputHandler {
    private PlayScreen screen;
    private TiledMap map;
    private TiledMapTileLayer.Cell selectedCell;
    private Unit selectedUnit;

    public InputHandler(PlayScreen screen) {
        this.screen = screen;
        map = screen.getMap();
        selectedCell = null;
        selectedUnit = null;
    }

    public void handleInput(float delta) {
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            selectedCell = getCell();
            selectedUnit = getUnit();

            System.out.println(selectedCell);
            System.out.println(selectedUnit);
        }

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
        // TODO Fix this calculation
        float relativeMouseX = (Gdx.input.getX()) / (screen.gamePort.getScreenWidth() / Resourcr.V_WIDTH) + screen.gameCam.position.x - (screen.gamePort.getScreenWidth()) / (screen.gamePort.getScreenWidth() / Resourcr.V_WIDTH) / 2;
        float relativeMouseY = (screen.gamePort.getScreenHeight() - Gdx.input.getY()) / (screen.gamePort.getScreenHeight() / Resourcr.V_HEIGHT) + screen.gameCam.position.y - (screen.gamePort.getScreenHeight()) / (screen.gamePort.getScreenHeight() / Resourcr.V_HEIGHT) / 2;
        return new Vector2(relativeMouseX, relativeMouseY);
    }

    private TiledMapTileLayer.Cell getCell() {
        float x = getRelativeMousePos().x;
        float y = getRelativeMousePos().y;
        TiledMapTileLayer tiledLayer = (TiledMapTileLayer) map.getLayers().get("ground");
        TiledMapTileLayer.Cell cell = tiledLayer.getCell((int) (x / tiledLayer.getTileWidth()), (int) (y / tiledLayer.getTileHeight()));
        if (cell != null) {
            return cell;
        }
        else return null;
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
