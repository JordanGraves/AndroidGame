package com.jgraves.mygdxgame;

import java.util.ArrayList;

import android.util.Log;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.ContactListener;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;

public class ContactResolver extends ContactListener {

	Matrix4 m = new Matrix4();
	Vector3 pos = new Vector3();
	ArrayList<GameObject> obstacles = new ArrayList<GameObject>();
	
	Matrix4 playerTransform = new Matrix4();
	Vector3 playerPosition = new Vector3();
	
	private Matrix4 m2 = new Matrix4();;
	private Matrix4 m1 = new Matrix4();
	Vector3 p1 = new Vector3();
	Vector3 p2 = new Vector3();
	
	@Override
	public void onContactStarted (btCollisionObject collider0, btCollisionObject collider1) {
        
		Game.player.body.getWorldTransform(playerTransform);
		playerTransform.getTranslation(playerPosition);
		
		for (GameObject obstacle : obstacles) {
			m = obstacle.body.getWorldTransform();
			m.getTranslation(pos);
			
			m1 = collider0.getWorldTransform();
			m2 = collider1.getWorldTransform();
			m1.getTranslation(p1);
			m2.getTranslation(p2);
			
			if (p1.equals(playerPosition) || p2.equals(playerPosition)) {
				Log.d(Game.TAG, "Player Hit");
				collider0.setCollisionFlags(btCollisionObject.CollisionFlags.CF_KINEMATIC_OBJECT ^ collider0.getCollisionFlags());
				collider1.setCollisionFlags(btCollisionObject.CollisionFlags.CF_KINEMATIC_OBJECT ^ collider1.getCollisionFlags());
				//gameOver();
			}
			if(pos.z > 8 && pos.z != 12) {
				Log.d(Game.TAG, "Found");
				pos.set(0, 5, -50);
				m.setToTranslation(pos);
				obstacle.body.setWorldTransform(m);
				obstacle.body.setLinearVelocity(new Vector3(0, 0, 0));
				return;
			}
		}
	}
	
	public void setArray(ArrayList<GameObject> a) {
		obstacles = a;
	}
}
