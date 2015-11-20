import java.awt.Dimension;
import java.util.ArrayList;

public class Chunk{
	private static Dimension chunkSize = new Dimension(TopDownGraphics.getViewportSize());
	private Point position;
	private ArrayList<Entity> entities;
	public ArrayList<Entity> getEntities() {
		return entities;
	}
	
	Chunk(Point position, Level level){
		
	}
	
}