package com.outofmemory.game.Heroes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.outofmemory.game.Heroes.Heroes;

public class Player extends Heroes {

    public Player(Body body, TextureMapObject textureMapObject) {
        super(body, textureMapObject);
    }

    @Override
    public void update() {

        float new_x_dir =  direction.getX()*body.getAngularVelocity();
        float new_y_dir =  direction.getY()*body.getAngularVelocity();

        float old_x = body.getPosition().x;
        float old_y = body.getPosition().y;

        body.setTransform(new Vector2(old_x+new_x_dir, old_y+new_y_dir), direction.getX()*10f + direction.getY()*10f);

    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(textureRegion,
                body.getPosition().x, body.getPosition().y,
                textureRegion.getRegionWidth() / 2.0f, textureRegion.getRegionHeight() / 2.0f,
                textureRegion.getRegionWidth(), textureRegion.getRegionHeight(),
                textureMapObject.getScaleX(),textureMapObject.getScaleY(), body.getAngle());
    }
}

