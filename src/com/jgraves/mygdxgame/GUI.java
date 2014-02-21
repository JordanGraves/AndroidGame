package com.jgraves.mygdxgame;

import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;


public class GUI {
	public Stage mStage;
	private BitmapFont font;
	private Skin skin;
	private TextureAtlas buttonAtlas;
	private TextButtonStyle textButtonStyle;
	private TextButton button;
	private ChangeListener cListener;
	private TextButton button2;

	float time;
	Label timerText;
	
	int moveButtonWidth = Gdx.graphics.getWidth() / 3;
	
	public GUI() {
		mStage = new Stage();
	}

	public void setup() {
		Gdx.input.setInputProcessor(mStage);
		
		font = new BitmapFont();
        skin = new Skin();
		
		setupJumpButton();
		setupMoveButton();
		setupTimerText();
	}
	
	void setupTimerText() {
		LabelStyle mLabelStyle = new LabelStyle();
		BitmapFont mFont = new BitmapFont();
		mFont.scale(3);
		mLabelStyle.font = mFont;
		timerText = new Label("0", mLabelStyle);
		timerText.setPosition(0, Gdx.graphics.getHeight() - 1 * Gdx.graphics.getHeight()/10);
		timerText.setHeight(25);
		timerText.setWidth(Gdx.graphics.getWidth()/2); 
		mStage.addActor(timerText);		
	}
	
	private void setupJumpButton() {
        buttonAtlas = new TextureAtlas(Gdx.files.internal("data/button_jump/button_jump.pack"));
        skin.addRegions(buttonAtlas);
        textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable("jump_button_up");
        textButtonStyle.down = skin.getDrawable("jump_button_down");
        button = new TextButton("JUMP", textButtonStyle);
        button.setPosition(0, 0); //** Button location **//
        button.setHeight(Gdx.graphics.getHeight() / 5); //** Button Height **//
        button.setWidth(Gdx.graphics.getWidth()/5); //** Button Width **//
        cListener = new ChangeListener() {

			// Called when button comes up
        	public void changed (ChangeEvent event, Actor actor) {
                Log.d("INPUT", "Jump");
                
                if (Game.player.isJumping) {
                	return;
                }
                else {
                	Game.player.jump();
					
                }
        	}
        };
        button.addListener(cListener);
        
        mStage.addActor(button);		
	}

	public void setupMoveButton() {
	    button2 = new TextButton("MOVE", textButtonStyle);
	    button2.setPosition( 2 * Gdx.graphics.getWidth() / 3, 0); //** Button location **//
	    button2.setHeight(Gdx.graphics.getHeight() / 5); //** Button Height **//
	    button2.setWidth(Gdx.graphics.getWidth() / 3); //** Button Width **//
	    button2.setBounds( 2 * Gdx.graphics.getWidth() / 3, 0, moveButtonWidth, Gdx.graphics.getHeight() / 5);
	    button2.addListener(new DragListener() {
	
	    	public void touchDragged(InputEvent event, float x, float y, int pointer)  	{
	    		//Log.d(Basic3DTest.TAG, "TouchDragged x: " + x + " y: " + y);
	    		Game.player.slideTo(toPlayerSpace(x));
	    	}
	    	
	    	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
	    		Log.d(Game.TAG, "TouchDown x: " + x + " y: " + y);
	    		Log.d(Game.TAG, "TouchDown x: " + Gdx.input.getX());
	    		
				return true;
	    	}
	    	
	    	public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
	    		Game.player.resetSlide();
	    	}
	    	@Override
	    	public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
	    		Game.player.resetSlide();
	    	}
	    });
	    
	    mStage.addActor(button2);	
	}
	
	public float toPlayerSpace(float x) {
		return x * 20f / moveButtonWidth - 10;
	}
	
	void updateTimer() {
		time += System.nanoTime() / 1000000000 / 10000;
		timerText.setText(time /100  + " ");
	}
	
	void resetTime() {
		time = 0f;
	}

	public void draw() {
		updateTimer();
		mStage.draw();
	}
	
	

}
