package com.lasagne.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Bird {
    Texture bird;
    boolean upMove;
    double width, height, x, y, velX, velY, accX, accY, maxX, maxY, rotation, distance;

    public Bird() {
        bird = new Texture("bird-solo.png");
        upMove = false;
        width = (int) (Gdx.graphics.getWidth() * 0.3f);
        height = (int) (Gdx.graphics.getHeight() * 0.3f);
        init();
    }

    public void init() {
        x = 10;
        y = 10;
        accY = 1;
        accX = 0;
        velX = -40;
        velY = -40;
        maxX = 40;
        maxY = 40;
        distance = 0;
        rotation = 0;
    }

    public void updateMotion() {

//        // Check if running
//        if (!running) {
//            return;
//        }

        // Update velocities
        velY = velY + accY;
        velX = velX + accX;

        // Update x & y positions
        x = x + velX;
        distance += x;
        y = y - (velY + accY / 2);
        if (velY > maxY) {
            velY = maxY;
        }
        if (velY < -maxY) {
            velY = -maxY;
        }
        if (velX > maxX) {
            velX = maxX;
        }
        if (velX < -maxX) {
            velX = -maxX;
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
