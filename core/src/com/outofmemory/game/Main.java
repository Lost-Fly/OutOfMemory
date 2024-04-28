package com.outofmemory.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
//import com.outofmemory.game.Screens.HomeScreen;
import com.outofmemory.game.Screens.PlayScreen;
import com.outofmemory.game.render.Renderer;


public class Main extends Game {

    public static int screenWidth;
    public static int screenHeight;

    private Renderer renderer;

    public Main() {
    }

    @Override
    public void create() {
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        renderer = new Renderer();
        renderer.setScreen(new PlayScreen());
    }

    @Override
    public void render() {
        renderer.render();
    }

    @Override
    public void dispose() {

    }


}
