import java.awt.Dimension;
import java.util.ArrayList;

public class Level{
	private ArrayList<Chunk> chunks;
	private int[][] textureMap;
	private boolean[][] collisionMap;
	private Dimension size;
	
	public Level(int[][] map) {
		this.textureMap = map;
		this.size = new Dimension((map.length > 0) ? map[0].length : 0, map.length);
		this.collisionMap = new boolean[this.size.height][this.size.width];
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[0].length; x++) {
				collisionMap[y][x] = textureMap[y][x] <= 0;
			}
		}
	}
	public Dimension getSize() { return size; }
	public int[][] getTextureMap() { return textureMap; }
	public boolean[][] getCollsionMap() { return collisionMap; }
	public ArrayList<Chunk> getChunks() { return chunks; }
}