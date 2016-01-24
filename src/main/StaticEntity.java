package main;

import java.awt.Dimension;

class Structure extends Entity {
	Structure(Level level, Point position, Dimension size) {
		super(level, position, size);
	}

	private boolean[][] collisionMatrix;
	
	boolean[][] getCollsionMatrix(){
		return collisionMatrix;
	}
}