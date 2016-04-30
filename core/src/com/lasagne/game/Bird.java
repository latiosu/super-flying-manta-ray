package com.lasagne.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;

public class Bird {
    Texture bird;
    boolean upMove;
    double width, height, x, y, velX, velY, accX, accY;


    public Bird() {
        bird = new Texture("bird-solo.png");
        upMove = false;
        width = (int) (Gdx.graphics.getWidth() * 0.3f);
        height = (int) (Gdx.graphics.getHeight() * 0.3f);
        x = 0;
        y = 1000;
        accY = 1;
    }

    public void updateMotion() {
        if (upMove) {
            // Own physics code here
            System.out.println(y);
            velY = -20;
        } else {
            velY = velY + accY;
        }
        y = y - (velY + accY / 2);
    }


}
