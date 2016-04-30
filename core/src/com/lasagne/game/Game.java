package com.lasagne.game;

import java.util.Random;
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

    private static final int        FRAME_COLS = 5;
    private static final int        FRAME_ROWS = 1;

    Animation walkAnimation;          // animation obj
    Texture                         walkSheet;              // sprite sheet
    TextureRegion[]                 walkFrames;             // frames of animation
    SpriteBatch                     spriteBatch;            // put texture on screen
    TextureRegion                   currentFrame;

    float stateTime;                                        // seconds from start of animation
    Random random;

	@Override
	public void create () {
        walkSheet = new Texture(Gdx.files.internal("bird_sprite.png"));
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/FRAME_COLS, walkSheet.getHeight()/FRAME_ROWS);              // #10
        walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        walkAnimation = new Animation(0.2f, walkFrames);      // create animation
        spriteBatch = new SpriteBatch();
        stateTime = 0f;

        random = new Random();

    }

	@Override
	public void render () {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT); // clear screen
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = walkAnimation.getKeyFrame(stateTime, true);  // get next frame
        spriteBatch.begin();
        spriteBatch.draw(currentFrame, 1300, 300, 16, 16, 32, 32, 8, 8, 0);
        spriteBatch.end();

	}
}
