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
        x = 10;
        y = 10;
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
            velX = -20;
        } else {
            velY = velY + accY;
        }
        // Check if running
        if (!running) {
            return;
        }
        // Update x & y positions
        x = x + velX;
        y = y - (velY + accY / 2);
        if (y <= 0) {
            y = 0;
        }
    }

    public void getPlayerTouch(int x,int y) {
        if (x <= (Gdx.graphics.getWidth()/2)) {
            leftTouch();
        } else {
            rightTouch();
        }
    }

    public void leftTouch() {
        System.out.println("left touch!");
    }

    public void rightTouch() {
        System.out.println("right touch!");
    }

}
