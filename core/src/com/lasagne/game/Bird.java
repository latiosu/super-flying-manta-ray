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
        x = 500;
        y = 800;
        accY = 1;
        velX = 0;
    }

    public void updateMotion() {
        if (upMove) {
            // Own physics code here
            // Check stingray hasn't left the screen
            if (y > Gdx.graphics.getHeight()) {
                velY = 0;
            } else {
                velY = -20;
            }

            velX = 3;
        } else {
            velY = velY + accY;
            velX = -1;
        }
        // Check stringray is within boundaries
        if (x < 400 || x > 1600) {
            velX = 0;
        }
        // Update x & y positions
        x = x + velX;
        y = y - (velY + accY / 2);
    }


}
