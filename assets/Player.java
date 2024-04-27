import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

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

//    private int score = 0;
//    private float health;
//    private boolean ghost;
//    private long startTimer=0;
//    private float wigth;
//    private float hight;
//    private Body body;
//
//
//    public Player(float width, float hight, Body body) {
//        super(width,hight,body);
//        this.health = health;
//    }
//
//
//    @Override
//    public void draw(SpriteBatch batch) {
////        if (ghost) {
////            this.img = Main.ghost;
////        }else {
////            this.img = Main.capibara;
////        }
//
//        batch.draw(img, position.getX() - radius, position.getY() - radius);
//    }
//
//
//    @Override
//    public void update() {
//        if (position.getX() + radius > Main.screenWidth) position.setX(Main.screenWidth - radius);
//        if (position.getX() - radius < 0) position.setX(radius);
//        if (position.getY() + radius > Main.screenHeight) position.setY(Main.screenHeight - radius);
//        if (position.getY() - radius < 0) position.setY(radius);
//        if (startTimer==0 && ghost) startTimer = System.currentTimeMillis();
//        int seconds=0;
//        if (startTimer>0) seconds = (int)(System.currentTimeMillis()-startTimer)/1000;
//        if (seconds>3){
//            ghost=false;
//            startTimer=0;
//        }
//
//        position.addCords(direction.getX() * speed, direction.getY() * speed);
//        bounds.centerPos.setPoint(position);
//
//
//    }
//
//    public void hit() {
//        if (!ghost) {
//            ghost = true;
//            health--;
//        }
//    }
//    public boolean isGhost(){ return ghost;}
//    public float getHealth(){return health;}
//    public void setScore(){score++;}
//    public int getScore(){return score;}

