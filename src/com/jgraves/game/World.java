package com.jgraves.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btDbvtBroadphase;
import com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btDispatcher;
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver;

public class World extends btDiscreteDynamicsWorld {

	public final Vector3 gravity = new Vector3(0, -10, 15);	

	public Environment environment;
	
	public PerspectiveCamera cam;
	public CameraInputController camController;
	
	public static ModelBatch modelBatch;
	public static ModelBuilder modelBuilder;
	
	ArrayList<ModelInstance> mModelInstances = new ArrayList<ModelInstance>();
    ArrayList<GameObject> mObstacles = new ArrayList<GameObject>();

	public World(btDispatcher dispatcher, btDbvtBroadphase broadphase, btSequentialImpulseConstraintSolver solver, btDefaultCollisionConfiguration config) {
		super(dispatcher, broadphase, solver, config);
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
	}

	public void create() {
		this.setGravity(gravity);
		setupCamera();
		modelBatch = new ModelBatch();
		modelBuilder = new ModelBuilder();
	}
	
	private void setupCamera() {
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(0f, 5f, 10f);
		cam.lookAt(0,0,-10);
		cam.near = 0.1f;
		cam.far = 300f;
		cam.update();
		//camController = new CameraInputController(cam);
        //Gdx.input.setInputProcessor(camController);
	}
	
	void addObstacle(GameObject obstacle) {
		mObstacles.add(obstacle);
		mModelInstances.add(obstacle.instance);
		addRigidBody(obstacle.body);
	}
	
	
	void update() {
		Game.player.update();
		for (GameObject gameObject : mObstacles) {
			gameObject.update();
		}
		
		modelBatch.begin(cam);
     		modelBatch.render(mModelInstances, environment);
     	modelBatch.end();
	}
}
