package com.outofmemory.game.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.outofmemory.game.Screens.HomeScreen;
import com.outofmemory.game.Screens.PlayScreen;
import com.outofmemory.game.Screens.Screen;

public class Renderer {

    private final SpriteBatch batch = new SpriteBatch();

    private final OrthographicCamera camera;

    private Screen currentScreen;

    public Renderer() {
        camera = new OrthographicCamera(150, 100);
    }

    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined); // Устанавливаем матрицу проекции для рендеринга с позиции камеры игры
        batch.begin();

        currentScreen.render(batch, camera);

        batch.end();
    }

    public void setScreen(Screen screen) {
        currentScreen = screen;
        currentScreen.screenToChange(newScreen -> currentScreen = newScreen);
    }

}
