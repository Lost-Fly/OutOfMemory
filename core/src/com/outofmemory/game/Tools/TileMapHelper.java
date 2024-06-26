package com.outofmemory.game.Tools;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ObjectMap;
import com.outofmemory.game.Heroes.Player;

import java.util.HashMap;
import java.util.Objects;

public class TileMapHelper {
    public TiledMap tiledMap;
    public static TiledMapTileLayer collisionLayer;
    public static int worldHeight;
    public static int worldWigth;
    private Player player;
    private Player line;
    private final World world;

    public TileMapHelper() {
        this.world = new World(new Vector2(0, 0), false);
    }

    public static ObjectMap<String, MapObject> taskPoints = new ObjectMap<>();


    public OrthogonalTiledMapRenderer setupMap() {
        this.tiledMap = new TmxMapLoader().load("map1.tmx");
        MapProperties properties = tiledMap.getProperties();
        worldHeight = properties.get("height", Integer.class) * 32 - 16;
        worldWigth = properties.get("width", Integer.class) * 32 - 16;
        parseMapObjects(tiledMap.getLayers().get("person").getObjects());


        collisionLayer = (TiledMapTileLayer) tiledMap.getLayers().get(0);


        for(MapLayer mapLayer : tiledMap.getLayers()){
//           Gdx.app.log("CHECK LAYER", "id " + mapLayer.getName());
           if (Objects.equals(mapLayer.getName(), "taskPoints")){

               for(MapObject mapObject   : mapLayer.getObjects()){
//                   Gdx.app.log("CHECK OBJECT COUNT", "id " + mapLayer.getObjects().getCount());
                   if (mapObject.getName()!=null) {
                      taskPoints.put(mapObject.getName(), mapObject);

//                       Gdx.app.log("CHECK OBJECT", "getX " + mapObject.getProperties().get("x"));
                   }
               }

           }
        }




        return new OrthogonalTiledMapRenderer(tiledMap);
    }

    private void parseMapObjects(MapObjects mapObjects) {
        for (MapObject mapObject : mapObjects) {

            TextureMapObject textureMapObject = ((TextureMapObject) mapObject);
            String textureMapObjectName = textureMapObject.getName();

            if (textureMapObjectName != null && textureMapObjectName.equals("person")) {

                Body body = BodyHelperService.createBody(textureMapObject,
                        world, false);

                player = new Player(body, textureMapObject);

            } else if (textureMapObjectName != null && textureMapObjectName.equals("line")) {

                Body body = BodyHelperService.createBody(textureMapObject,
                        world, false);

                textureMapObject.setScaleX(0.5f);
                textureMapObject.setScaleY(0.5f);

                line = new Player(body, textureMapObject);

            }

        }
    }

    public Player getPlayer() {
        return player;
    }

    private Shape createRectangleShape(RectangleMapObject rectangleMapObject) {
        PolygonShape shape = new PolygonShape();
        Vector2 size = new Vector2((rectangleMapObject.getRectangle().width / 2) / 32.0f, (rectangleMapObject.getRectangle().height / 2) / 32.0f);
        shape.setAsBox(size.x, size.y, rectangleMapObject.getRectangle().getCenter(new Vector2()).scl(1 / 32.0f), 0.0f);
        return shape;
    }

    public Player getLine() {
        return line;
    }
}
