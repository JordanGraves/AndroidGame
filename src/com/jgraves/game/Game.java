package com.jgraves.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btDbvtBroadphase;
import com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class Game implements ApplicationListener {
	public static String TAG = "SpaceNinja";
	
	ContactResolver mContactListener;
	private btDefaultCollisionConfiguration collisionConfiguration;
	private btCollisionDispatcher dispatcher;
	private btDbvtBroadphase broadphase;
	private btSequentialImpulseConstraintSolver solver;
			
	public static Texture backgroundTexture;
    public static Sprite backgroundSprite;
    private SpriteBatch spriteBatch;

    static GameObject mGround;
	static Cube mObstacle1;
	static Cube mObstacle2;

	static Player player;
	
	Stage stage;
    TextButton button;
    TextButton button2;
    TextButtonStyle textButtonStyle;
    BitmapFont font;
    Skin skin;
    TextureAtlas buttonAtlas;

	private Cube mObstacle3;
	private GUI mGui;
	private Cube mWall;
	private float wall_z = 12f;
	private World mWorld;
	
    private void loadTextures() {
        backgroundTexture = new Texture(Gdx.files.internal("data/background_512_1024.jpg"));
        backgroundSprite = new Sprite(backgroundTexture);
    }

    public void renderBackground() {
        backgroundSprite.draw(spriteBatch);
    }
    
	@Override
	public void create() {
		Bullet.init();
		collisionConfiguration = new btDefaultCollisionConfiguration();
	    dispatcher = new btCollisionDispatcher(collisionConfiguration);
	    broadphase = new btDbvtBroadphase();
	    solver = new btSequentialImpulseConstraintSolver();
	   
		mWorld = new World(dispatcher, broadphase, solver, collisionConfiguration);
	    mWorld.create();
				
		spriteBatch = new SpriteBatch();
		
		loadTextures();
		
		mGui = new GUI();
        mGui.setup();
		
        createObstacles();
        createGround();
        createWall();
        player = new Player();
        mWorld.addObstacle(player);
        player.collisionObject.setUserValue(2);
        mObstacle1.collisionObject.setUserValue(4);
        mObstacle1.collisionObject.setContactCallbackFilter(2);
        mObstacle2.collisionObject.setUserValue(8);
        mObstacle2.collisionObject.setContactCallbackFilter(2);
        mObstacle3.collisionObject.setUserValue(16);
        mObstacle3.collisionObject.setContactCallbackFilter(2);
        mGround.collisionObject.setUserValue(32);
        mGround.collisionObject.setContactCallbackFilter(0);
        mWall.collisionObject.setContactCallbackFlag(2);
        
        mContactListener = new ContactResolver();
        mContactListener.setArray(mWorld.mObstacles);
	}
	
	private void createObstacles() {
        mObstacle1 = new Cube(3f, 3f, 3f,
        						3, 7, -50, 1, Color.RED);
        mObstacle2 = new Cube(3, 3, 3, 
        						5, 5, -20, 1, Color.BLUE);
        mObstacle3 = new Cube(2, 5, 2f, 
								-2, 8, -17, 1, Color.YELLOW);
        mWorld.addObstacle(mObstacle1);
        mWorld.addObstacle(mObstacle2);
        mWorld.addObstacle(mObstacle3);
        mObstacle1.randomizeRotation();
        mObstacle2.randomizeRotation();
        mObstacle3.randomizeRotation();
    }
	
	private void createGround() {
		mGround = new Cube(20f, 0.1f, 600f, 0, 0, 0, 0, Color.BLUE);
		mGround.collisionObject.setUserValue(8);
		mWorld.addObstacle(mGround);
		mWorld.mModelInstances.add(mGround.instance);
		mWorld.addRigidBody(mGround.body);        
	}
	

	private void createWall() {
		mWall = new Cube(100, 100, 1, 0, 0, wall_z , 0, Color.BLACK);
		mWorld.addObstacle(mWall);
		mWorld.mModelInstances.add(mWall.instance);
		mWorld.addRigidBody(mWall.body);      
	}
	
	@Override
	public void render() {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
                               		
        spriteBatch.begin();
        	renderBackground();
        spriteBatch.end();        
        
        mWorld.update();
        
        mGui.draw();
        // camController.update();
        mWorld.stepSimulation(Gdx.graphics.getDeltaTime(), 10, 1/60f);
	}

	void gameOver() {
	}
	
	@Override
	public void dispose() {
		mWorld.modelBatch.dispose();
		for (GameObject gameObject : mWorld.mObstacles) {
			gameObject.body.dispose();
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}

