package com.outofmemory.game.Heroes;

import static com.outofmemory.game.Tools.TileMapHelper.collisionLayer;
import static com.outofmemory.game.Tools.TileMapHelper.worldHeight;
import static com.outofmemory.game.Tools.TileMapHelper.worldWigth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.ObjectMap;
import com.outofmemory.game.Tools.TileMapHelper;

import java.util.HashMap;

public class Player extends Heroes {

    private float increment;

    public Player(Body body, TextureMapObject textureMapObject) {
        super(body, textureMapObject);
    }

    @Override
    public void update() {

//        float oldX = body.getPosition().x;
//        float oldY = body.getPosition().y;

        float newXDir =  direction.getX()*body.getAngularVelocity();
        float newYDir =  direction.getY()*body.getAngularVelocity();

        float newX = getX() + newXDir;
        float newY = getY() + newYDir;

//        float newX = oldX+newXDir;
//        float newY = oldY+newYDir;
//
//        if (newX <= 0){
//            newX = 0;
//        }
//        if (newY <= 0) {
//            newY = 0;
//        }
//        if (newX >= worldWigth) {
//            newX = oldX;
//        }
//        if (newY >= worldHeight) {
//            newY = oldY;
//        }

        Vector2 velocity = new Vector2(newXDir, newYDir);

        // save old position
        float oldX = body.getPosition().x, oldY = body.getPosition().y;
        boolean collisionX = false, collisionY = false;

        // calculate the increment for step in #collidesLeft() and #collidesRight()
        increment = collisionLayer.getTileWidth();
        increment = getWidth() < increment ? getWidth() / 2 : increment / 2;

//        Gdx.app.log("VELOCITY", "vlx " + newXDir + "  vly " + newYDir);

        boolean isBlock = false;
        
        if (collisionLayer.getCell((int)newX,(int)newY)!= null){
            isBlock = collisionLayer.getCell((int)newX,(int)newY).getTile().getProperties().containsKey("blocked");
        }

//        Gdx.app.log("COLLIDE BLOCK", "playerPosX " + body.getPosition().x + "  vly " + body.getPosition().y
//        + "  " +  isBlock
//        );

        if(velocity.x < 0) // going left
            collisionX = collidesLeft();
        else if(velocity.x > 0) // going right
            collisionX = collidesRight();

        // react to x collision
        if(collisionX) {
            newX = oldX;
            velocity.x = 0;
        }

        // move on y
//        setY(getY() + velocity.y * delta * 5f);

        // calculate the increment for step in #collidesBottom() and #collidesTop()
        increment = collisionLayer.getTileHeight();
        increment = getHeight() < increment ? getHeight() / 2 : increment / 2;

        boolean canJump;
        if(velocity.y < 0) // going down
            canJump = collisionY = collidesBottom();
        else if(velocity.y > 0) // going up
            collisionY = collidesTop();

        // react to y collision
        if(collisionY) {
            newY = oldY;
            velocity.y = 0;
        }


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
//        Gdx.app.log("VELOCITY", "vlx " + newX + "  vly " + newY);
//        if (newX >= 593) {
//            if ((newY >= 663) && (newY <= 769)) {
//                flag
//                newX = oldX;
//                newY = oldY;
//            }
//        }

        ObjectMap<String, MapObject> hashMap = TileMapHelper.taskPoints;

        for(MapObject taskPoint: hashMap.values()){

            float objXleft = (float) taskPoint.getProperties().get("x");
            float objXright = (float) taskPoint.getProperties().get("x") + 95;

            float objYbottom = (float) taskPoint.getProperties().get("y") + 95;
            float objYtop = (float) taskPoint.getProperties().get("y");


            Gdx.app.log("CHECK COLLISIN", "pX " + newX + " pY " + newY + "  oX " +

                    objXleft + ' ' + objXright + "   oY " + objYtop + ' ' + objYbottom + "   " + taskPoint.getName());
            if(newX >= objXleft && newX <= objXright){
                if(newY >= objYtop && newY <= objYbottom){
                    newX = oldX;
                    newY = oldY;
                }
            }

        }




        body.setTransform(new Vector2(newX, newY), direction.getX()*10f + direction.getY()*10f);

    }

    private boolean isCellBlocked(float x, float y) {
        TiledMapTileLayer.Cell cell = collisionLayer.getCell((int) (x / collisionLayer.getTileWidth()), (int) (y / collisionLayer.getTileHeight()));
        String blockedKey = "blocked";
        return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey(blockedKey);
    }

    public boolean collidesRight() {
        for(float step = 0; step <= getHeight(); step += increment)
            if(isCellBlocked(getX() + getWidth(), getY() + step))
                return true;
        return false;
    }

    public boolean collidesLeft() {
        for(float step = 0; step <= getHeight(); step += increment)
            if(isCellBlocked(getX(), getY() + step))
                return true;
        return false;
    }

    public boolean collidesTop() {
        for(float step = 0; step <= getWidth(); step += increment)
            if(isCellBlocked(getX() + step, getY() + getHeight()))
                return true;
        return false;

    }

    public boolean collidesBottom() {
        for(float step = 0; step <= getWidth(); step += increment)
            if(isCellBlocked(getX() + step, getY()))
                return true;
        return false;
    }

    public float getX(){
        return body.getPosition().x;
    }

    public float getY(){
        return body.getPosition().y;
    }

    public float getWidth(){
        return worldWigth;
    }

    public float getHeight(){
        return worldHeight;
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

