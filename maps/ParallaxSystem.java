package maps;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.miv.Constants;

public class ParallaxSystem {
	private ParallaxLayer[] layers;
	private Camera camera;
    private SpriteBatch batch;
    public Body focus;
    public Vector2 speed = new Vector2();
   
    /**
     * @param layers - The background layers 
     * @param width - The screenWith 
     * @param height - The screenHeight
     * @param body - A Vector2 attribute to point out the x and y speed
     */
     public ParallaxSystem(ParallaxLayer[] layers, float width, float height, Body focus){
        this.layers = layers;
        this.focus = focus;
        camera = new OrthographicCamera(width, height);
        batch = new SpriteBatch();
     }
   
     public void render(float delta) {
    	 speed.set(new Vector2(focus.getLinearVelocity().x*Constants.BACKGROUND_SCROLL_SPEED, focus.getLinearVelocity().y*Constants.BACKGROUND_SCROLL_SPEED));
    	 
    	 camera.position.add(speed.x*delta,speed.y*delta, 0);
    	 for(ParallaxLayer layer : layers){
    		 batch.setProjectionMatrix(camera.projection);
    		 batch.begin();
    		 
    		 float currentX = -camera.position.x*layer.parallaxRatio.x % ( layer.region.getRegionWidth() + layer.padding.x);
    		 
    		 if (speed.x < 0) currentX += -(layer.region.getRegionWidth() + layer.padding.x);
    		 
    		 do {
    			 float currentY = - camera.position.y*layer.parallaxRatio.y % ( layer.region.getRegionHeight() + layer.padding.y);
    			 if( speed.y < 0 )currentY += -(layer.region.getRegionHeight()+layer.padding.y);
    			 do {
    				 // Normal 
    				 batch.draw(layer.region, -camera.viewportWidth/2 + currentX + layer.startPosition.x , -camera.viewportHeight/2 + currentY +layer.startPosition.y);
    				 batch.draw(layer.region, -camera.viewportWidth/2 + currentX + layer.startPosition.x , -camera.viewportHeight/2 + currentY + layer.startPosition.y);
    				 
    				 if(speed.x >= 0) batch.draw(layer.region, -camera.viewportWidth/2 + currentX - (layer.region.getRegionWidth() + layer.padding.x) + layer.startPosition.x , -camera.viewportHeight/2 + currentY +layer.startPosition.y);
    				 if(speed.x <= 0) batch.draw(layer.region, -camera.viewportWidth/2 + currentX + (layer.region.getRegionWidth() + layer.padding.x) + layer.startPosition.x , -camera.viewportHeight/2 + currentY +layer.startPosition.y);
    				 if(speed.y >= 0) batch.draw(layer.region, -camera.viewportWidth/2 + currentX + layer.startPosition.x , -camera.viewportHeight/2 + currentY + layer.startPosition.y - (layer.region.getRegionHeight() + layer.padding.y));
    				 if(speed.y <= 0) batch.draw(layer.region, -camera.viewportWidth/2 + currentX + layer.startPosition.x , -camera.viewportHeight/2 + currentY + layer.startPosition.y + (layer.region.getRegionHeight() + layer.padding.y));
                     
                     currentY += (layer.region.getRegionHeight() + layer.padding.y);
    			 } while(currentY < camera.viewportHeight);
             currentX += ( layer.region.getRegionWidth()+ layer.padding.x);
    		 } while( currentX < camera.viewportWidth);
    		 batch.end();
    	 }
     }
}
