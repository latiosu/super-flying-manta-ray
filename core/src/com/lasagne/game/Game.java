package com.lasagne.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.Random;


public class Game extends ApplicationAdapter {

    private static final int        FRAME_COLS = 5;
    private static final int        FRAME_ROWS = 1;

    boolean firstTouch = false;

    Animation walkAnimation;          // animation obj
    Texture                         walkSheet;              // sprite sheet
    TextureRegion[]                 walkFrames;             // frames of animation
    SpriteBatch                     spriteBatch;            // put texture on screen
    TextureRegion                   currentFrame;

    float stateTime;                                        // seconds from start of animation
    Random random;

    Bird player;
    Camera camera;
    Image water;
    Texture waterTex;
    int waterTileRepeats;
    float oldX = 0;
    BitmapFont font;
    SpriteBatch batch;
    boolean camera_paused = false;

    Label score;
    Label.LabelStyle scoreStyle;
    Label welcome;
    Label.LabelStyle welcomeStyle;

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
        batch = new SpriteBatch();
        stateTime = 0f;

        random = new Random();

        // Font stuff
        font = new BitmapFont();
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        // Score (font stuff)
        scoreStyle = new Label.LabelStyle();
        scoreStyle.font = font;
        score = new Label("", scoreStyle);
        score.setBounds(100, 100, Gdx.graphics.getWidth(), 20);
        score.setFontScale(10f, 10f);

        // Welcome (font stuff)
        welcomeStyle = new Label.LabelStyle();
        welcomeStyle.font = font;
        welcome = new Label("TAP to fly", welcomeStyle);
        welcome.setBounds(Gdx.graphics.getWidth() / 2f - welcome.getWidth() / 2f,
                Gdx.graphics.getHeight() / 2f - welcome.getHeight() / 2f, Gdx.graphics.getWidth(), 20);
        welcome.setFontScale(10f, 10f);

        player = new Bird();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        waterTex = new Texture(Gdx.files.internal("water.png"));
        waterTex.setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.Repeat);

        waterTileRepeats = Math.round(Gdx.graphics.getWidth() / waterTex.getWidth() + 4) * 2;
        TextureRegion waterRegion = new TextureRegion(waterTex);
        waterRegion.setRegion(0, 0, waterTex.getWidth() * waterTileRepeats, waterTex.getHeight());

        TextureRegionDrawable waterDrawable = new TextureRegionDrawable(waterRegion);
        water = new Image();
        water.setDrawable(waterDrawable);
        water.setSize(waterTex.getWidth() * waterTileRepeats, waterTex.getHeight());
        water.setPosition((float) (player.x - (0.75 * waterTex.getWidth() * waterTileRepeats)), -Gdx.graphics.getHeight() / 2f);


        sr = new ShapeRenderer();

        // This handles player movement
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown (int x, int y, int pointer, int button) {
                if (!firstTouch) {
                    firstTouch = true;
                }

                player.upMove = true;
                player.getPlayerTouch(x,y);

                if (camera_paused && x >= 0 && x <= 200 && y >= Gdx.graphics.getHeight() - 200 && y <= Gdx.graphics.getHeight()) {
                    restartGame();
                }

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

        Gdx.gl.glClearColor(0.77f, 0.77f, 0.98f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT); // clear screen

        if (player.y <= -10) {
            camera_paused = true;
        }

        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = walkAnimation.getKeyFrame(stateTime, true);  // get next frame

        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        water.draw(spriteBatch, 1f); // Render water
        spriteBatch.draw(currentFrame, (float) player.x, (float) player.y, 16, 16, 32, 32, 8, 8, (float) player.rotation); // Render Player
        spriteBatch.end();

        if (!camera_paused) {
            batch.begin();
            font.setColor(Color.BLACK);
            score.setText(calcScore(player.distance));
            score.draw(batch, 1f);
            batch.end();

            if (camera.position.x <= oldX - camera.viewportWidth) {
                water.setPosition((float) (player.x - (0.75 * waterTex.getWidth() * waterTileRepeats)), -Gdx.graphics.getHeight() / 2f);
                oldX = (float)player.x;
            }

            camera.translate((float) ((player.x - camera.position.x) / 10.0), (float) ((player.y - camera.position.y) / 10.0), 0);
            camera.update();
        } else {
            // If camera is paused - assume game ended
            sr.begin(ShapeRenderer.ShapeType.Filled);
//            sr.setColor(0, 0, 0, 1.3f);
            sr.setColor(Color.YELLOW);
            sr.rect(0, 0, 200, 200);
            sr.end();
        }

        if (!firstTouch) {
            batch.begin();
            font.setColor(Color.BLACK);
            welcome.draw(batch, 1f);
            batch.end();
            return;
        }
        player.updateMotion();
    }

    public void restartGame() {
        // reset player position
        player.init();

        // reset bg position
        water.setPosition((float) (player.x - (0.75 * waterTex.getWidth() * waterTileRepeats)), -Gdx.graphics.getHeight() / 2f);

        // reset camera position
        camera.position.x =  (float) player.x;
        camera.position.y = (float) player.y;
        camera.update();

        // re-enable camera
        camera_paused = false;

    }

    public String calcScore(double distance) {
        return String.valueOf(Math.abs((int) player.distance/100000));
    }

}
