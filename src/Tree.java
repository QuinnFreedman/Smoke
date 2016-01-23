import java.awt.Dimension;
import java.awt.image.BufferedImage;

class Tree extends Entity {

	private BufferedImage sprite;
	private boolean[][] collisionMatrix;
	public Tree(Level level, Point mapPosition, int number) {
		super(level, mapPosition, new Dimension(0,0));
		
		this.sprite = Main.loadImage("static_entities/tree_"+number);
		
		assert sprite != null;
		assert sprite.getWidth() % 32 == 0;
		assert sprite.getHeight() % 32 == 0;
		this.size = new Dimension(sprite.getWidth()/32, sprite.getHeight()/32);
		
		this.collisionMatrix = new boolean[this.size.height][this.size.width];
		
		if(this.size.width % 2 == 0) {
			this.collisionMatrix[this.size.height - 1][this.size.width / 2] = true;
			this.collisionMatrix[this.size.height - 1][this.size.width / 2 + 1] = true;
		} else {
			this.collisionMatrix[this.size.height - 1][this.size.width / 2 + 1] = true;
		}
		
	}
	
	@Override
	public BufferedImage getSprite(int t) {
		return this.sprite;
	}
	
	@Override
	public boolean[][] getCollsionMatrix(){
		return collisionMatrix;
	}
	
}