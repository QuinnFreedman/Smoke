import java.awt.Dimension;
import java.awt.image.BufferedImage;

class Tree extends Entity {

	private static BufferedImage sprite;
	private static final boolean[][] collisionMatrix = new boolean[][]{{false},{true}};
	public Tree(Level level, Point mapPosition, Dimension size) {
		super(level, mapPosition, size);
		if(sprite == null) {
			sprite = Main.loadImage("Tree");
		}
		
	}
	
	@Override
	public BufferedImage getSprite(int t) {
		return Tree.sprite;
	}
	
	@Override
	public boolean[][] getCollsionMatrix(){
		return collisionMatrix;
	}
	
}