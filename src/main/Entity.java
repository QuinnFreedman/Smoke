package main;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

class Entity{
	protected Dimension size;
	protected Point position;
	protected int zindex;
	private Chunk chunk;
	protected int animFrames = 0;

	Point getPosition() {
		return position;
	}
	void setPosition(Point position) {
		this.position = position;
	}
	Dimension getSize() {
		return size;
	}
	void setSize(Dimension size) {
		this.size = size;
	}
	Chunk getChunk() {
		return chunk;
	}
	boolean[][] getCollsionMatrix(){
		//TODO
		return null;
	}
	void moveChunk(Chunk c) {
		this.getChunk().scheduleRemoveEntity(this);
		this.chunk = c;
		c.scheduleAddEntity(this);
	}
	BufferedImage getSprite(int t){
		return null;
	}
	
	Point getMapLocation(){
		return new Point(this.position.x/TopDownGraphics.tileWidthHeight_Pixels,
						 this.position.y/TopDownGraphics.tileWidthHeight_Pixels);
	}
	
	Entity(Level level, Point mapPosition, Dimension size) {
		System.out.println("new "+this.getClass().getSimpleName()+" @ "+mapPosition);
		this.position = mapPosition.scale(TopDownGraphics.tileWidthHeight_Pixels);
		this.size = new Dimension(size);
		this.chunk = level.getChunk(mapPosition);
		this.chunk.scheduleAddEntity(this);
		this.chunk.updateEntities();
		this.chunk.modifyChunkCollider(this, 1);
	}
	
}