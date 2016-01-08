import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Comparator;

public class Chunk{
	private Dimension chunkSize;
	private Point position;
	private ArrayList<Entity> entities;
	private boolean updateRequired = false;
	private ArrayList<Entity> entitiesToRemove;
	private ArrayList<Entity> entitiesToAdd;
	private int[][] entityCollision;
	public ArrayList<Entity> getEntities() {
		return entities;
	}
	public Point getPosition(){
		return position;
	}
	public Dimension getSize() {
		return chunkSize;
	}
	
	Chunk(Point position, Level level, Dimension size){
		this.position = position;
		this.chunkSize = size;
		this.entities = new ArrayList<Entity>();
		this.entitiesToRemove = new ArrayList<Entity>();
		this.entitiesToAdd = new ArrayList<Entity>();
		this.entityCollision = new int[size.height][size.width];
	}
	
	public void scheduleRemoveEntity(Entity entity){
		entitiesToRemove.add(entity);
		updateRequired = true;
	}
	public void scheduleAddEntity(Entity entity) {
		this.entitiesToAdd.add(entity);
		this.updateRequired = true;
	}
	
	public void updateEntities(){
		if(updateRequired){
			updateRequired = false;
			entities.removeAll(entitiesToRemove);
			entities.addAll(entitiesToAdd);
			entitiesToAdd.clear();
			entitiesToRemove.clear();
			sortEntites();
		}
	}
	
	public void sortEntites() {
		entities.sort(new Comparator<Entity>() {
			@Override
			public int compare(Entity o1, Entity o2) {
				int y1 = o1.getMapLocation().y + o1.getSize().height;
				int y2 = o2.getMapLocation().y + o2.getSize().height;
				
				if(y1 < y2) {
					return -1;
				} else if (y1 > y2) {
					return 1;
				}
				
				return 0;
				
			}
		});
	}
	
	public void modifyChunkCollider(Entity entity, int additive){
		if(!(additive == 1 || additive == -1)){
			throw new IllegalArgumentException();
		}

		Point p = getRelativePosition(entity.getMapLocation());
		if(entity.getCollsionMatrix() != null){
			for (int y = 0; y < entity.getCollsionMatrix().length; y++) {
				for (int x = 0; x < entity.getCollsionMatrix()[0].length; x++) {
					int xx = x + p.x;
					int yy = y + p.y;
					if(xx >= 0 && xx < this.chunkSize.width && 
							yy >= 0 && yy < this.chunkSize.height){
						entityCollision[yy][xx] += 
								entity.getCollsionMatrix()[y][x] ? additive : 0;
					}
				}
			}
		} else {
			for (int y = 0; y < entity.getSize().height; y++) {
				for (int x = 0; x < entity.getSize().width; x++) {
					int xx = x + p.x;
					int yy = y + p.y;
					if(xx >= 0 && xx < this.chunkSize.width && 
							yy >= 0 && yy < this.chunkSize.height){
						entityCollision[yy][xx] += additive;
					}
				}
			}
		}
	}
	
	private Point getRelativePosition(Point p){
		return new Point(p.x - this.position.x, p.y - this.position.y);
	}
	public boolean collides(Point p) {
		Point relativePoint = getRelativePosition(p);
		return (entityCollision[relativePoint.y][relativePoint.x] > 0);
	}
	
}