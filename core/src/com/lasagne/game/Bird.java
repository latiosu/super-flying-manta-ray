package com.lasagne.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;

public class Bird {
    Texture bird;
    boolean upMove, running;
    double width, height, x, y, velX, velY, accX, accY, maxX, maxY, rotation;

    public Bird() {
        bird = new Texture("bird-solo.png");
        upMove = false;
        width = (int) (Gdx.graphics.getWidth() * 0.3f);
        height = (int) (Gdx.graphics.getHeight() * 0.3f);
        x = 10;
        y = 10;
        accY = 1;
        accX = 0;
        velX = -25;
        velY = -25;
        maxX = 30;
        maxY = 30;
        running = false;
        rotation = 0;
    }

    public void updateMotion() {
        if (upMove) {
            // Start motion
            running = true;
        }

//        // Check if running
//        if (!running) {
//            return;
//        }

        // Update velocities
        velY = velY + accY;
        velX = velX + accX;

        // Update x & y positions
        x = x + velX;
        y = y - (velY + accY / 2);
        if (Math.abs(velY) > maxY) {
            velY = maxY;
        }
        if (Math.abs(velX) > maxX) {
            velX = maxX;
        }
        // rotate bird
        rotateBird();
    }

    // Check which side of screen player touched
    public void getPlayerTouch(int x,int y) {
        if (x <= (Gdx.graphics.getWidth()/2)) {
            leftTouch();
        } else {
            rightTouch();
        }
    }

    public void leftTouch() {
        velY = velY - 10;
        velX = velX - 10;
    }

    public void rightTouch() {
        velY = -20;
        velX = velX + 5;
    }

    public void rotateBird() {
        double ratio;
        ratio = (velY/velX);
        rotation = 360 - Math.toDegrees(Math.atan(ratio));
    }

}
