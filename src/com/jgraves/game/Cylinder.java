package com.jgraves.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.physics.bullet.collision.btCylinderShape;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBodyConstructionInfo;
import com.badlogic.gdx.physics.bullet.linearmath.btDefaultMotionState;
import com.badlogic.gdx.physics.bullet.linearmath.int4;

public class Cylinder extends GameObject {

	public Cylinder(float width, float height, float depth, int divisions,
					float xpos, float ypos, float zpos,
					int mass, Color color) {		
		
			super();
		
			this.model = World.modelBuilder.createCylinder(width, height, depth, divisions, new Material(
			            ColorAttribute.createDiffuse(color)),
			            Usage.Position | Usage.Normal);
			this.instance = new ModelInstance(model);
				 
			this.shape = new btCylinderShape(new Vector3(width/2,height/2,depth/2));
			Vector3 inertia = new Vector3();
			this.shape.calculateLocalInertia(mass, inertia);
			this.motionState = new btDefaultMotionState();
			this.constructInfo = new btRigidBodyConstructionInfo(mass, motionState, shape, inertia);
			this.constructInfo.setRestitution(.80f);
			this.constructInfo.setFriction(0.8f); 
			this.motionState.setWorldTransform(new Matrix4(new Vector3(xpos, ypos, zpos), new Quaternion(), new Vector3(1,1,1)));
			this.body = new btRigidBody(constructInfo);
			this.body.setRollingFriction(.5f);
						
	}
}
