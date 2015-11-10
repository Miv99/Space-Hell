package com.miv;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import components.ShieldComponent;

public class GUI extends Stage {
	private Stage stage;
	private Main main;
	
	private ComponentMapper<ShieldComponent> sm = ComponentMapper.getFor(ShieldComponent.class);

	public void create(InputMultiplexer im, Main main) {
		this.main = main;
	    stage = new Stage();
	    
		/*
		TextButton startButton = new TextButton("New Game",skin);
		TextButton quitButton = new TextButton("Quit Game",skin);
		*/
		
		/*
		startButton.addListener(new ClickListener() {
		   @Override
		   public void clicked(InputEvent event, float x, float y) {
		      System.out.println("asd");
		      event.stop();
		   }
		});
		*/
				
		Bars bars = new Bars();
		
		stage.addActor(bars);
		
		im.addProcessor(new InputMultiplexer(stage, this));
		//Gdx.input.setInputProcessor(im);
	}

	public void resize (int width, int height) {
	    stage.getViewport().update(width, height, true);
	}

	public void render(float deltaTime) {
	    //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	    stage.act(deltaTime);
	    stage.draw();
	}

	public void dispose() {
	    stage.dispose();
	}
	
	public class Bars extends Actor {

	    private Sprite border, hp, shield, brokenShield;

	    public Bars() {
	        Texture HPBar = new Texture(Gdx.files.internal("assets\\GUI\\Bars.png"));
	        border = new Sprite(new TextureRegion(HPBar, 0, 0, 400, 128));
	        hp = new Sprite(new TextureRegion(HPBar, 448, 16, 204, 16));
	        shield = new Sprite(new TextureRegion(HPBar, 448, 56, 204, 16));
	        brokenShield = new Sprite(new TextureRegion(HPBar, 448, 96, 204, 16));
	        
	        hp.setOrigin(0, 0);
	        shield.setOrigin(0, 0);
	        brokenShield.setOrigin(0, 0);
	        
	        border.setPosition(0, Gdx.graphics.getHeight() - border.getHeight());
	        hp.setPosition(border.getX() + 144, border.getY() + border.getHeight() - 32);
	        shield.setPosition(border.getX() + 144, border.getY() + border.getHeight() - 72);
	        brokenShield.setPosition(border.getX() + 144, border.getY() + border.getHeight() - 72);
	    }

	    @Override
	    public void draw(Batch batch, float parentAlpha) {
	        float hpPercent = main.getPlayerHPPercent();
	        float shieldPercent = main.getPlayerShieldPercent();
	        	        	        	        
	        border.draw(batch);
	        
	        // Draw HP Bar
	        hp.setScale(hpPercent, 1f);
	        hp.draw(batch);
	        
	        // Draw shield bar
	        if(sm.get(main.player).shieldBroken) {
		        brokenShield.setScale(shieldPercent, 1f);
	        	brokenShield.draw(batch);
	        } else {
		        shield.setScale(shieldPercent, 1f);
		        shield.draw(batch);
	        }
	    }
	}
}
