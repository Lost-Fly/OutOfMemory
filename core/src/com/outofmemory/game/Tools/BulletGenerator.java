//package com.outofmemory.game.Tools;
//
//import com.outofmemory.game.Heroes.Bullet;
//import com.outofmemory.game.Main;
//import com.outofmemory.game.Screens.WaitingSc;
//
//public class BulletGenerator {
//    boolean isFire;
//
//    public void update(Joystick joy) {
//        isFire = joy.getDir().getX() != 0 || joy.getDir().getY() != 0;
//        if (isFire) WaitingSc.bullets.add(new Bullet(Main.bullet, Main.players.get(Main.meId).position,
//                25, Main.players.get(Main.meId).radius / 8, joy.getDir()));
//
//
//    }
//}
