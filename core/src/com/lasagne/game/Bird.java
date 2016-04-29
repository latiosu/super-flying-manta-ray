package com.lasagne.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Bird {
    Texture bird;
    Body body;
    boolean upMove;
    int width, height, speed;

    public Bird() {
        bird = new Texture("bird-solo.png");
        upMove = false;
        width = (int) (Gdx.graphics.getWidth() * 0.3f);
        height = (int) (Gdx.graphics.getHeight() * 0.3f);
        speed = 700;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody; // Subject to gravity
        bodyDef.position.set(Gdx.graphics.getWidth() / 2, 100);

        // Create out body in the world using our body definition
        body = Game.getWorld().createBody(bodyDef);

        CircleShape circle = new CircleShape();
        circle.setRadius(6f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.0f; // No bouncy bird

        // Attach fixture to the body
        Fixture fixture = body.createFixture(fixtureDef);

        // Always dispose of shapes when you finish with them!
        circle.dispose();
    }

    public void updateMotion() {
        if (upMove) {
            body.setLinearVelocity(0, speed * Game.PIXEL_SCALE * Gdx.graphics.getDeltaTime());
        }
    }


}
