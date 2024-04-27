package com.outofmemory.game.Screens;


import static com.outofmemory.game.Main.screenHeight;
import static com.outofmemory.game.Main.screenWidth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.outofmemory.game.Heroes.Player;
import com.outofmemory.game.Tools.Joystick;
import com.outofmemory.game.Tools.Point2D;
import com.outofmemory.game.Tools.TileMapHelper;

public class PlayScreen implements Screen {

    Joystick joy;
    private final TileMapHelper tileMapHelper;

    private final Player player;

    private final OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private final OrthographicCamera hudCamera;


    public PlayScreen() {
        hudCamera = new OrthographicCamera(screenWidth, screenHeight);
        hudCamera.viewportWidth = screenWidth; // Размеры для камеры HUD
        hudCamera.viewportHeight = screenHeight;
        hudCamera.position.set(screenWidth / 2, screenHeight / 2, 0);
        hudCamera.update();

        this.tileMapHelper = new TileMapHelper();
        orthogonalTiledMapRenderer = tileMapHelper.setupMap();

        this.player = tileMapHelper.getPlayer();

        Gdx.input.setInputProcessor(new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                screenY = screenHeight - screenY;
                multitouch((int) screenX, (int) screenY, true, pointer);
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                screenY = screenHeight - screenY;
                multitouch((int) screenX, (int) screenY, false, pointer);
                return false;
            }

            @Override
            public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
                return touchUp(screenX, screenY, pointer, button);
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                return touchDown(screenX, screenY, pointer, 0);
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                return false;
            }

            @Override
            public boolean scrolled(float amountX, float amountY) {
                return false;
            }
        });

        loadHeroes();
    }

    public void multitouch(float x, float y, boolean isDownTouch, int pointer) {
        for (int i = 0; i < 3; i++) {
            joy.update(x, y, isDownTouch, pointer);
        }
    }

    public void render(SpriteBatch batch, OrthographicCamera camera) {
        player.setDirection(joy.getDir());
        player.update();

        cameraUpdate(camera); // Обновляем камеру игры

        orthogonalTiledMapRenderer.setView(camera); // Устанавливаем камеру для рендерера карты
        orthogonalTiledMapRenderer.render(); // Рендерим карту

        player.draw(batch);

        // Начинаем рисовать элементы интерфейса с использованием камеры HUD
        batch.setProjectionMatrix(hudCamera.combined);
        joy.draw(batch); // Рисуем джойстик с использованием камеры HUD
    }

    @Override
    public void dispose() {

    }

    public void loadHeroes() {
        joy = new Joystick(new Texture("circle.png"),
                new Texture("circle.png"),
                new Point2D(((screenHeight / 3) / 2 + (screenHeight / 3) / 4),
                        (screenHeight / 3) / 2 + (screenHeight / 3) / 4), screenHeight / 3);
    }

    private void cameraUpdate(OrthographicCamera camera) {
        // Получаем размеры карты
        MapProperties properties = tileMapHelper.tiledMap.getProperties();
        int mapWidth = properties.get("width", Integer.class);
        int mapHeight = properties.get("height", Integer.class);
        int tilePixelWidth = properties.get("tilewidth", Integer.class);
        int tilePixelHeight = properties.get("tileheight", Integer.class);
        int mapPixelWidth = mapWidth * tilePixelWidth;
        int mapPixelHeight = mapHeight * tilePixelHeight;

        // Устанавливаем позицию камеры на игрока
        camera.position.set(player.getBody().getPosition().x, player.getBody().getPosition().y, 0);

        // Ограничиваем движение камеры границами карты
        float cameraHalfWidth = camera.viewportWidth * 0.5f;
        float cameraHalfHeight = camera.viewportHeight * 0.5f;

        // Ограничиваем, что камера не выходит за пределы карты
        float cameraLeft = clamp(camera.position.x - cameraHalfWidth, 0, mapPixelWidth - camera.viewportWidth);
        float cameraBottom = clamp(camera.position.y - cameraHalfHeight, 0, mapPixelHeight - camera.viewportHeight);

        camera.position.x = cameraLeft + cameraHalfWidth;
        camera.position.y = cameraBottom + cameraHalfHeight;

        camera.update();
    }

    private float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }
}

