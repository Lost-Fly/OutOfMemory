package com.outofmemory.game.Screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.outofmemory.game.Tools.ScreenConsumer;

import java.util.function.Consumer;

public interface Screen {
    void render(SpriteBatch batch, OrthographicCamera camera);

    void dispose();

    default void screenToChange(ScreenConsumer screenConsumer) {

    }
}
