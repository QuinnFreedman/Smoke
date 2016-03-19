package world;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

import main.Entity;
import main.Level;
import main.Main;
import main.Point;

public class Tree extends Entity {

	private BufferedImage sprite;
	private boolean[][] collisionMatrix;
	/**
	 * 
	 * @param level the game level the tree should be added too
	 * @param mapPosition the coordinates on the map where the tree will be added(upper left corner)
	 * @param number the number of the sprite of the tree
	 * @param zindex the zindex of the tree (player character zindex is 0)
	 */
	public Tree(Level level, Point mapPosition, int number, int zindex) {
		super(level, mapPosition, new Dimension(0,0));
		
		this.sprite = Main.loadImage("static_entities/tree_"+number);
		this.zindex = zindex;
		
		assert sprite != null;
		assert sprite.getWidth() % 32 == 0;
		assert sprite.getHeight() % 32 == 0;
		this.size = new Dimension(sprite.getWidth()/32, sprite.getHeight()/32);
		
		this.collisionMatrix = new boolean[this.size.height][this.size.width];
		
		/*
		if(this.size.width == 1) {
			this.collisionMatrix[this.size.height - 1][0] = true;
		} else if(this.size.width % 2 == 0) {
			this.collisionMatrix[this.size.height - 1][this.size.width / 2] = true;
			this.collisionMatrix[this.size.height - 1][this.size.width / 2 - 1] = true;
		} else {
			this.collisionMatrix[this.size.height - 1][this.size.width / 2 + 1] = true;
		}
		
		this.getChunk().modifyChunkCollider(this, 1);
		*/
		
	}
	
	@Override
	protected BufferedImage getSprite(int t) {
		return this.sprite;
	}
	
	@Override
	protected boolean[][] getCollsionMatrix(){
		return collisionMatrix;
	}
	
}