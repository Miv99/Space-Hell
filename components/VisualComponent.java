package components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.miv.Constants;

public class VisualComponent implements Component {
	public Sprite sprite;
	
	// For use by walls
	public VisualComponent(Sprite sprite, float widthMultiplier, float heightMultiplier) {
		this.sprite = new Sprite(sprite);
		this.sprite.setSize((this.sprite.getWidth() * widthMultiplier), (this.sprite.getHeight() * heightMultiplier));
		//this.sprite.setOrigin(this.sprite.getWidth()/2, this.sprite.getHeight()/2);
	}

	public VisualComponent(Sprite sprite) {
		this.sprite = new Sprite(sprite);
		this.sprite.setSize((this.sprite.getWidth() * Constants.SCALE), (this.sprite.getHeight() * Constants.SCALE));
		this.sprite.setOrigin(this.sprite.getWidth()/2, this.sprite.getHeight()/2);
	}
	
	public VisualComponent(Sprite sprite, float sizeMultiplier) {
		this.sprite = new Sprite(sprite);
		this.sprite.setSize((this.sprite.getWidth() * sizeMultiplier * Constants.SCALE), (this.sprite.getHeight() * sizeMultiplier * Constants.SCALE));
		this.sprite.setOrigin(this.sprite.getWidth()/2, this.sprite.getHeight()/2);
	}
}
