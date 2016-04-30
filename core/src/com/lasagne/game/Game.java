package com.lasagne.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game extends ApplicationAdapter {

    Camera camera;
	SpriteBatch batch;
    Bird player;

	@Override
	public void create () {
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

    }

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
        batch.draw(player.bird, player.x, player.y, player.width, player.height);
		batch.end();

        // Update bird positions before rendering
        player.updateMotion();

	}
}
