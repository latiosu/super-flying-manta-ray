package com.lasagne.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;

public class Bird {
    Texture bird;
    boolean upMove, running;
    double width, height, x, y, velX, velY, accX, accY;


    public Bird() {
        bird = new Texture("bird-solo.png");
        upMove = false;
        width = (int) (Gdx.graphics.getWidth() * 0.3f);
        height = (int) (Gdx.graphics.getHeight() * 0.3f);
        x = 500;
        y = 500;
        accY = 1;
        velX = 0;
        running = false;
    }

    public void updateMotion() {
        if (upMove) {
            // Start motion
            running = true;

            // Check stingray hasn't left the screen
            if (y > Gdx.graphics.getHeight()) {
                velY = 0;
            } else {
                velY = -20;
            }
            velX = 5;
        } else {
            velY = velY + accY;
            velX = -1;
        }
        // Check if running
        if (!running) {
            return;
        }
        // Check stringray is within boundaries
        if (x < 300) {
            velX = 0;
        }
        if (x > 1350) {
            velX = -1;
        }
        // Update x & y positions
        x = x + velX;
        y = y - (velY + accY / 2);
        if (y <= 0) {
            y = 0;
        }
    }


}
