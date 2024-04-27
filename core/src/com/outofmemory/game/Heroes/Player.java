package com.outofmemory.game.Heroes;

import static com.outofmemory.game.Tools.TileMapHelper.worldHeight;
import static com.outofmemory.game.Tools.TileMapHelper.worldWigth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.outofmemory.game.Tools.TileMapHelper;

public class Player extends Heroes {

    public Player(Body body, TextureMapObject textureMapObject) {
        super(body, textureMapObject);
    }

    @Override
    public void update() {

        float oldX = body.getPosition().x;
        float oldY = body.getPosition().y;

        float newXDir =  direction.getX()*body.getAngularVelocity();
        float newYDir =  direction.getY()*body.getAngularVelocity();

        float newX = oldX+newXDir;
        float newY = oldY+newYDir;

        if (newX <= 0){
            newX = 0;
        }
        if (newY <= 0) {
            newY = 0;
        }
        if (newX >= worldWigth) {
            newX = oldX;
        }
        if (newY >= worldHeight) {
            newY = oldY;
        }

        body.setTransform(new Vector2(newX, newY), direction.getX()*10f + direction.getY()*10f);

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

