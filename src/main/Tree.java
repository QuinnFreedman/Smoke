package main;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

class Tree extends Entity {

	private BufferedImage sprite;
	private boolean[][] collisionMatrix;
	Tree(Level level, Point mapPosition, int number, int zindex) {
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
	BufferedImage getSprite(int t) {
		return this.sprite;
	}
	
	@Override
	boolean[][] getCollsionMatrix(){
		return collisionMatrix;
	}
	
}