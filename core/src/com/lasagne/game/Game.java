package com.lasagne.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Game extends ApplicationAdapter {

    private static final int        FRAME_COLS = 6;
    private static final int        FRAME_ROWS = 5;

    Animation walkAnimation;          // animation obj
    Texture                         walkSheet;              // sprite sheet
    TextureRegion[]                 walkFrames;             // frames of animation
    SpriteBatch                     spriteBatch;            // put texture on screen
    TextureRegion                   currentFrame;

    float stateTime;                                        // seconds from start of animation

	@Override
	public void create () {
        walkSheet = new Texture(Gdx.files.internal("animation_sheet.png"));
        // use 2D array to read in values then convert to 1D array
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth(), walkSheet.getHeight());
        walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        walkAnimation = new Animation(0.025f, walkFrames);      // create animation
        spriteBatch = new SpriteBatch();
        stateTime = 0f;

    }

	@Override
	public void render () {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT); // clears screen
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = walkAnimation.getKeyFrame(stateTime, true); // get frame
        spriteBatch.begin();
        spriteBatch.draw(currentFrame, 50, 50); // position to render in 
        spriteBatch.end();

	}
}
