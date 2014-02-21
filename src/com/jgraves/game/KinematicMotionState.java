package com.jgraves.game;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.bullet.linearmath.btMotionState;


public class KinematicMotionState extends btMotionState {
	private Matrix4 worldTransform;
    public KinematicMotionState() {
        worldTransform = new Matrix4();
        worldTransform.idt();
    }

    @Override
    public void getWorldTransform(Matrix4 worldTrans) 
    {
        worldTrans.set(worldTransform);
        return;
    }

    @Override
    public void setWorldTransform(Matrix4 worldTrans) 
    {
        worldTransform.set(worldTrans);
    }
}
