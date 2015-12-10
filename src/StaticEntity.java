import java.awt.Dimension;

class Structure extends Entity {
	public Structure(Level level, Point position, Dimension size) {
		super(level, position, size);
	}

	private boolean[][] collisionMatrix;
	
	public boolean[][] getCollsionMatrix(){
		return collisionMatrix;
	}
}