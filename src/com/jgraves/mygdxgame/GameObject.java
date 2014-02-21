package com.jgraves.mygdxgame;

import java.util.Random;

import android.util.Log;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBodyConstructionInfo;
import com.badlogic.gdx.physics.bullet.linearmath.btMotionState;


public class GameObject {
	public Model model;
	public ModelInstance instance;

	public btRigidBodyConstructionInfo constructInfo;
	public btCollisionShape shape;
	public btRigidBody body;
	public btCollisionObject collisionObject;
	public btMotionState motionState;

	Matrix4 transform = new Matrix4();
	
	public GameObject() {
		this.collisionObject = new btCollisionObject();
		this.collisionObject.setUserValue(4);

	}
	
	public void update() {
		this.instance.transform.set(this.body.getWorldTransform());
	}

	public void randomizeRotation() {
		this.body.getWorldTransform(transform);
		// Usually this can be a field rather than a method variable
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int xangle = rand.nextInt((360 - 0) + 1) + 0;
	    
	    Log.d(Game.TAG, "angle: " + xangle);
		
	    transform.setToRotation(1, 1, 1, xangle);
	    
	    //this.body.setWorldTransform(transform);
		
	}
	
	public void setUserValue(int v) {
		this.collisionObject.setUserValue(v);
	}
	
	public int getUserValue() {
		return this.collisionObject.getUserValue();
	}
	
	public void reset() {
	}
}
