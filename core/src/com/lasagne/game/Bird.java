package com.lasagne.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;

public class Bird {
    Texture bird;
    Body body;
    boolean upMove;
    int width, height, speed, x, y, velX, velY, accX, accY;

    public Bird() {
        bird = new Texture("bird-solo.png");
        upMove = false;
        width = (int) (Gdx.graphics.getWidth() * 0.3f);
        height = (int) (Gdx.graphics.getHeight() * 0.3f);
        speed = 700;

    }

    public void updateMotion() {
        if (upMove) {
            // Own physics code here
            System.out.println(body.getPosition().x);
        }
    }


}
