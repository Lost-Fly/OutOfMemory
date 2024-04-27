package com.outofmemory.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.outofmemory.game.Screens.PlayScreen;


public class Main extends Game {
    public static SpriteBatch batch;
    public static int screenWidth;
    public static int screenHeight;
    public static Texture img;
    public static Texture capibara;
    public static Texture circle;
    public  static Texture tabouret;
    private OrthographicCamera orthographicCamera;

    public Main() {
    }

    @Override
    public void create () {
        batch = new SpriteBatch();
        img = new Texture("packlogo.png");
        circle = new Texture("circle.png");
        capibara = new Texture("capibara.png");
        tabouret = new Texture("tabouret.png");
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        orthographicCamera = new OrthographicCamera();
        //orthographicCamera.setToOrtho(false,450,300);
        setScreen(new PlayScreen(orthographicCamera));
    }

    @Override
    public void render () {
        super.render();
    }

    @Override
    public void dispose () {
        batch.dispose();
        img.dispose();
    }


}
