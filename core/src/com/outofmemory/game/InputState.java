package com.outofmemory.game;


public interface InputState {

    void setType(String type);
    String getType();

    void setScore(int score);
    int getScore();

    void setX(float x);
    float getX();

    void setY(float y);
    float getY();
}
