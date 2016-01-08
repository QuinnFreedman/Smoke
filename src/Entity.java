import java.awt.Dimension;
import java.awt.image.BufferedImage;

class Entity{
	protected Dimension size;
	protected Point position;
	private Chunk chunk;
	protected int animFrames = 0;

	public Point getPosition() {
		return position;
	}
	public void setPosition(Point position) {
		this.position = position;
	}
	public Dimension getSize() {
		return size;
	}
	public void setSize(Dimension size) {
		this.size = size;
	}
	public Chunk getChunk() {
		return chunk;
	}
	public boolean[][] getCollsionMatrix(){
		//TODO
		return null;
	}
	public void moveChunk(Chunk c) {
		this.getChunk().scheduleRemoveEntity(this);
		this.chunk = c;
		c.scheduleAddEntity(this);
	}
	public BufferedImage getSprite(int t){
		return null;
	}
	
	Point getMapLocation(){
		return new Point(this.position.x/TopDownGraphics.tileWidthHeight_Pixels,
						 this.position.y/TopDownGraphics.tileWidthHeight_Pixels);
	}
	
	public Entity(Level level, Point mapPosition, Dimension size) {
		System.out.println("new "+this.getClass().getSimpleName()+" @ "+mapPosition);
		this.position = mapPosition.scale(TopDownGraphics.tileWidthHeight_Pixels);
		this.size = new Dimension(size);
		this.chunk = level.getChunk(mapPosition);
		this.chunk.getEntities().add(this);
		this.chunk.modifyChunkCollider(this, 1);
	}
	
}