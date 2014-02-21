package com.jgraves.mygdxgame;

import android.util.Log;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBodyConstructionInfo;


public class Player extends GameObject {
	float jumpHeight = 6.5f;
	float jumpSpeed = .075f;
	

	float x = 1;
	float y = 3;
	float z = 1;
	float mass = 1;
	
	float xpos, to_x = 0;
	float ypos = y/2 + .2f;
	float zpos = -3;
	Color color = Color.BLACK;
	public boolean isJumping = false;
	
	Matrix4 mTransform = new Matrix4();
	private Vector3 position = new Vector3();
	
	
	
	public Player() {	
		
			super();
		
			this.model = World.modelBuilder.createBox(x, y, z, 
			            new Material(
			            ColorAttribute.createDiffuse(color)),
			            Usage.Position | Usage.Normal);
			this.instance = new ModelInstance(model);
				 
			this.shape = new btBoxShape(new Vector3(x/2,y/2,z/2));
				 Vector3 inertia = new Vector3();
			this.shape.calculateLocalInertia(mass, inertia);
			this.motionState = new KinematicMotionState();
			this.constructInfo = new btRigidBodyConstructionInfo(mass, motionState, shape, inertia);
			this.constructInfo.setRestitution(.80f);
			this.constructInfo.setFriction(1f); 
			this.mTransform = new Matrix4(new Vector3(xpos, ypos, zpos), new Quaternion(), new Vector3(1,1,1));
			this.motionState.setWorldTransform(mTransform);
			this.body = new btRigidBody(constructInfo);
			this.body.setCollisionFlags(btCollisionObject.CollisionFlags.CF_KINEMATIC_OBJECT | body.getCollisionFlags());
	}
		
	public void jump() {
		isJumping = true;
	}
	
	public void handleJump() {
		
		if (position.y >= jumpHeight)
		{
			Log.d(Game.TAG, "Reached Top of Jump");
			
			mTransform.setToTranslation(position.x, jumpHeight, position.z);
			jumpSpeed = -Math.abs(jumpSpeed);
		}
		if (position.y <= y/2.0 +.1) {
			Log.d(Game.TAG, "Landed");
			
			jumpSpeed = Math.abs(jumpSpeed);
			mTransform.setToTranslation(position.x, ypos, position.z);
			isJumping = false;
		}
		//Log.d(Basic3DTest.TAG, position.toString() + "    jumpSpeed: " + jumpSpeed);
	    mTransform.translate(0 , jumpSpeed, 0);
	}


	@Override
	public void update() {
		mTransform.getTranslation(position);
		
		if (isJumping) {
			Log.d(Game.TAG, "Jumping");
			handleJump();
		}
		handleSliding();
		
		move();
		
		this.body.setWorldTransform(mTransform);
		this.instance.transform.set(this.body.getWorldTransform());
	}

	private void move() {
		
	}

	private void handleSliding() {
		// float speed = Math.abs(xpos) - Math.abs(position.x);
		if (position.x < 10 && to_x > position.x) {
			mTransform.translate((float) .1,0,0);	// Move Right
		}
		else if (position.x > -10 && to_x < position.x) {
			mTransform.translate((float) -.1,0,0);	// Move Left
		}
	}

	public void slideTo(float _x) {
		//Log.d(Basic3DTest.TAG, "Setting toX: " + _x);
		to_x = _x;
	}

	public void resetSlide() {
		to_x = position.x;
	}

}
