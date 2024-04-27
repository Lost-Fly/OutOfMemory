package com.outofmemory.game.Tools;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.outofmemory.game.Heroes.Player;
import com.outofmemory.game.Screens.PlayScreen;

public class TileMapHelper {
    public TiledMap tiledMap;
    private final PlayScreen playScreen;
    public TileMapHelper(PlayScreen playScreen){
        this.playScreen = playScreen;
    }
    public OrthogonalTiledMapRenderer setupMap(){
        tiledMap=new TmxMapLoader().load(("Map.tmx"));
        parseMapObjects(tiledMap.getLayers().get("lightObject").getObjects());
        return new OrthogonalTiledMapRenderer(tiledMap);
    }
    private void parseMapObjects(MapObjects mapObjects){
        for (MapObject mapObject: mapObjects){


            if(mapObject instanceof TextureMapObject){
                Gdx.app.log("INFO","Я нашел текстуру");
                TextureMapObject textureMapObject = ((TextureMapObject) mapObject);
                String textureMapObjectName = textureMapObject.getName();
                Gdx.app.log("INFO",textureMapObjectName);
                if(textureMapObjectName.equals("kek")) {
                    Gdx.app.log("INFO","Я зашел в test текстуру");
                    Body body = BodyHelperService.createBody(textureMapObject,
                            playScreen.getWorld(),false);
                    Gdx.app.log("INFO","Я тут");
                    //textureMapObject.setScaleX(4.0f);
                    //textureMapObject.setScaleY(4.0f);
//                    body.setTransform(textureMapObject.getX(), textureMapObject.getY(), 0);
                    playScreen.setPlayer(new Player(body,textureMapObject));

                }
            }
//            if (mapObject instanceof RectangleMapObject){
//                createStaticBody((RectangleMapObject) mapObject);
//            }
        }
    }

//    private void createStaticBody(RectangleMapObject rectangleMapObject){
//        BodyDef bodyDef = new BodyDef();
//        bodyDef.type = BodyDef.BodyType.StaticBody;
//        Body body  =playScreen.getWorld().createBody(bodyDef);
//        Shape shape = createRectangleShape(rectangleMapObject);
//        body.createFixture(shape,1000);
//        shape.dispose();
//    }

    private Shape createRectangleShape(RectangleMapObject rectangleMapObject) {
        PolygonShape shape = new PolygonShape();
        Vector2 size = new Vector2((rectangleMapObject.getRectangle().width / 2) / 32.0f, (rectangleMapObject.getRectangle().height / 2) / 32.0f);
        shape.setAsBox(size.x, size.y, rectangleMapObject.getRectangle().getCenter(new Vector2()).scl(1/32.0f), 0.0f);
        return shape;
    }

}
