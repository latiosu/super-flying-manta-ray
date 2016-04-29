package com.lasagne.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Game extends ApplicationAdapter {
    public static final int PIXEL_SCALE = 10000;

    private static World world;

    Box2DDebugRenderer box2dDebug;

    Camera camera;
	SpriteBatch batch;
    Bird player;

	@Override
	public void create () {
        Box2D.init();
        world = new World(new Vector2(0, -10*PIXEL_SCALE), true); // Gravity + ignore inactive bodies
        box2dDebug = new Box2DDebugRenderer();

        camera = new OrthographicCamera();
        batch = new SpriteBatch();
        player = new Bird();

        // This handles player movement
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown (int x, int y, int pointer, int button) {
                player.upMove = true;
                return true; // return true to indicate the event was handled
            }

            @Override
            public boolean touchUp (int x, int y, int pointer, int button) {
                player.upMove = false;
                return true; // return true to indicate the event was handled
            }
        });

        // Create the ground
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(new Vector2(0, 10));

        Body groundBody = world.createBody(groundBodyDef);

        // Set the polygon shape as a box which is twice the size of our viewport
        // (setAsBox takes half-width and half-height as arguments)
        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(camera.viewportHeight, 10.0f);
        groundBody.createFixture(groundBox, 0.0f);
        groundBox.dispose();

    }

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
        batch.draw(player.bird, player.body.getPosition().x, player.body.getPosition().y, player.width, player.height);
		batch.end();

        box2dDebug.render(world, camera.combined);

        // Update bird positions before rendering
        player.updateMotion();

        world.step(1/45f, 6, 2);
	}

    public static World getWorld() {
        return world;
    }
}
