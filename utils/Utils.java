package utils;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Utils {
	/**
	 * Checks if this entity is in range of the target
	 * @param e1 - location of the first object
	 * @param e2 - location of the second object
	 * @param range - the range of the observer
	 * @return
	 */
	public static boolean inRange(Vector2 e1, Vector2 e2, float range) {
		if(e1.dst(e2) <= range) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Returns whether or not the die roll was successful
	 * @param chance - chance of die roll being successful in percent; eg 43%
	 * @return
	 */
	public static boolean chance(int chance) {
		if(MathUtils.random(100) < chance) {
			return true;
		} else {
			return false;
		}
	}
}
