package main;

import java.awt.Dimension;
import java.util.ArrayList;

public class Level{
	private Chunk[][] chunks;
	private int[][] textureMap;
	private int[][] spriteMap;
	private boolean[][] collisionMap;
	private Dimension size;
	private static Dimension chunkSize = TopDownGraphics.getViewportSize();
	
	Level(int[][] map, int[][] staticSprites) {
		this.textureMap = map;
		this.spriteMap = staticSprites;
		this.size = new Dimension((map.length > 0) ? map[0].length : 0, map.length);
		
		this.collisionMap = new boolean[this.size.height][this.size.width];
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[0].length; x++) {
				collisionMap[y][x] = textureMap[y][x] <= 0 || spriteMap[y][x] < 0;
			}
		}
		int xChunks = Math.max(1, Math.round(((float) this.size.width) / chunkSize.width));
		int yChunks = Math.max(1, Math.round(((float) this.size.height) / chunkSize.height));
		chunks = new Chunk[yChunks][xChunks];
		for(int y = 0; y < yChunks; y++){
			for(int x = 0; x < xChunks; x++){
				/*Dimension specialSize = new Dimension(chunkSize);
				if(y == yChunks - 1){
					specialSize.height = this.size.height - (y * chunkSize.height);
				}
				if(x == xChunks - 1){
					specialSize.width = this.size.width - (x * chunkSize.width);
				}*/
				chunks[y][x] = new Chunk(new Point(x * chunkSize.width, y * chunkSize.height),
						this, chunkSize);
			}
		}
	}
	public Dimension getSize() { return size; }
	int[][] getTextureMap() { return textureMap; }
	int[][] getStaticSprites() { return spriteMap; }
	boolean[][] getCollsionMap() { return collisionMap; }
	Chunk[][] getChunks() { return chunks; }
	
	boolean collides(Point p){
		if(p.x < 0 || p.y < 0 || p.x >= size.width || p.y >= size.height){
			return true;
		}
		
		return (collisionMap[p.y][p.x] || (getChunk(p) != null && getChunk(p).collides(p)));
	}
	Chunk getChunk(Point p) {
		if(p.x < 0 || p.y < 0 || p.x >= size.width || p.y >= size.height){
			return null;
		}
		return chunks[p.y / chunkSize.height][p.x / chunkSize.width];
	}
	ArrayList<Chunk> getAdjacentChunks(Point p) {
		ArrayList<Chunk> adjacentChunks = new ArrayList<Chunk>();
		for(int y =  -1; y <= 1; y++){
			for(int x = -1; x <= 1; x++){
				int yy = p.y / chunkSize.height + y;
				int xx = p.x / chunkSize.width + x;
				if(yy >= 0 && yy < chunks.length && xx >= 0 && xx < chunks[0].length){
					adjacentChunks.add(chunks[yy][xx]);
				}
			}
		}
		return adjacentChunks;
	}
}