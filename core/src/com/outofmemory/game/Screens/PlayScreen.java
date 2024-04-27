package com.outofmemory.game.Screens;



import static com.outofmemory.game.Main.screenHeight;
import static com.outofmemory.game.Main.screenWidth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.outofmemory.game.Heroes.Player;
import com.outofmemory.game.Main;
import com.outofmemory.game.Tools.Joystick;
import com.outofmemory.game.Tools.Point2D;
import com.outofmemory.game.Tools.TileMapHelper;

public class PlayScreen extends ScreenAdapter {

    private Main main;
    Joystick joy;
    private SpriteBatch batch;
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private OrthographicCamera camera;

    private OrthographicCamera hudCamera;
    private TileMapHelper tileMapHelper;

    public static Player player;

    public World getWorld() {
        return world;
    }
    public static void setPlayer(Player player) {
        PlayScreen.player = player;
    }


    public PlayScreen(OrthographicCamera orthographicCamera){
        this.camera = orthographicCamera;
        this.batch = new SpriteBatch();
        this.world = new World(new Vector2(0,0),false);
        this.tileMapHelper = new TileMapHelper(this);
        this.orthogonalTiledMapRenderer = tileMapHelper.setupMap();

        hudCamera = new OrthographicCamera(screenWidth, screenHeight);
        hudCamera.position.set(screenWidth / 2, screenHeight / 2, 0);
        hudCamera.update();

    }

    @Override
    public void show() {
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
//                Vector2 worldCoordinates = screenToWorldCoordinates(screenX, screenY);
//                multitouch(worldCoordinates.x, worldCoordinates.y, true, pointer);
                screenY = screenHeight - screenY;
                multitouch((int)screenX, (int)screenY, true, pointer);
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
//                Vector2 worldCoordinates = screenToWorldCoordinates(screenX, screenY);
//                multitouch(worldCoordinates.x, worldCoordinates.y, false, pointer);
                screenY = screenHeight - screenY;
                multitouch((int)screenX, (int)screenY, false, pointer);
                return false;
            }

            @Override
            public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
//                Vector2 worldCoordinates = screenToWorldCoordinates(screenX, screenY);
//                multitouch(worldCoordinates.x, worldCoordinates.y, true, pointer);
                screenY = screenHeight - screenY;
                multitouch((int)screenX, (int)screenY, false, pointer);
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
//                Vector2 worldCoordinates = screenToWorldCoordinates(screenX, screenY);
//                multitouch(worldCoordinates.x, worldCoordinates.y, true, pointer);
                screenY = screenHeight - screenY;
                multitouch((int)screenX, (int)screenY, true, pointer);
                return false;
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
    public void multitouch(float x, float y, boolean isDownTouch, int pointer){
        for(int i =0; i < 3; i++){
            joy.update(x,y,isDownTouch, pointer);
        }
    }


//    @Override
//    public void render(float delta) {
//
//        Gdx.gl.glClearColor(0,0,0,1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        gameUpdate();
//        orthogonalTiledMapRenderer.render();
//        Main.batch.begin();
//        gameRender(Main.batch);
//        Main.batch.end();
//
//
//    }

    @Override
    public void render(float delta) {
        gameUpdate(); // Выполняем логику игры
        cameraUpdate(); // Обновляем камеру игры

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        orthogonalTiledMapRenderer.setView(camera); // Устанавливаем камеру для рендерера карты
        orthogonalTiledMapRenderer.render(); // Рендерим карту

        batch.setProjectionMatrix(camera.combined); // Устанавливаем матрицу проекции для рендеринга с позиции камеры игры
        batch.begin();
        gameRender(batch); // Рисуем объекты игры
        batch.end();

        // Начинаем рисовать элементы интерфейса с использованием камеры HUD
        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();
        joy.draw(batch); // Рисуем джойстик с использованием камеры HUD
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = 150; // Размеры для игровой камеры
        camera.viewportHeight = 100;
        camera.update();

        hudCamera.viewportWidth = screenWidth; // Размеры для камеры HUD
        hudCamera.viewportHeight = screenHeight;
        hudCamera.position.set(screenWidth / 2, screenHeight / 2, 0);
        hudCamera.update();

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
    public void loadHeroes(){
//        joy = new Joystick(Main.circle, Main.capibara, new Point2D((float)50, (float)50), 10);
        joy = new Joystick(Main.circle, Main.circle, new Point2D(((screenHeight/3)/2+(screenHeight/3)/4), (screenHeight/3)/2+(screenHeight/3)/4), screenHeight/3);
    }
    public void gameUpdate() {
//        orthogonalTiledMapRenderer.setView(camera);
        player.setDirection(joy.getDir());
        player.update();
        cameraUpdate();
        orthogonalTiledMapRenderer.setView(camera);

    }

//    private void cameraUpdate() {
//        camera.position.set(player.getBody().getPosition().x,player.getBody().getPosition().y,0);
////        camera.setToOrtho(false);
//
//        camera.update();
//    }

    private void cameraUpdate() {
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

    public void gameRender(SpriteBatch batch) {
//        joy.draw(batch);
        player.draw(batch);
    }
    private Vector2 screenToWorldCoordinates(int screenX, int screenY) {
        Vector3 screenCoords = new Vector3(screenX, screenY, 0);
        camera.unproject(screenCoords);
        return new Vector2(screenCoords.x, screenCoords.y);
    }
}

