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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;


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

    Bird player;
    Camera camera;
    Texture background;

    ShapeRenderer sr;

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

        player = new Bird();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        background = new Texture(Gdx.files.internal("background.png"));

        sr = new ShapeRenderer();

        // This handles player movement
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown (int x, int y, int pointer, int button) {
                player.upMove = true;
                player.getPlayerTouch(x,y);
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

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT); // clear screen

        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = walkAnimation.getKeyFrame(stateTime, true);  // get next frame

        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(0, 0, 1, 1);
        sr.rect(0, 0, Gdx.graphics.getWidth(), 100);
        sr.end();

        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        spriteBatch.draw(background, 0, 0, background.getWidth(), background.getHeight());
        spriteBatch.draw(currentFrame, (float) player.x, (float) player.y, 16, 16, 32, 32, 8, 8, (float) player.rotation);
        spriteBatch.end();

        player.updateMotion();
        camera.translate((float)((player.x - camera.position.x)/10.0), (float) ((player.y - camera.position.y)/10.0), 0);
        camera.update();
	}
}
