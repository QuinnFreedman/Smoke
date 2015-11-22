import java.awt.image.BufferedImage;

public class NPCCharacter extends Character{
	private Object target = null;
	private int disposition;
	private int alertness;
	private boolean isPathing;
	private BufferedImage sprite;
	
	NPCCharacter(Point position, Race race, String cclass) {
		super(position, race, cclass);
		this.sprite = Main.loadImage("character_tiles/"+this.getRace()+"_"+this.getCclass());
	}
	
	public BufferedImage getSprite(int t) {
		return this.sprite;
	};
	
	@Override
	void doLogic() {
		super.doLogic();
		if(target != null && target instanceof Entity){
			Point targetLocation;
			if(target instanceof Character){
				targetLocation = ((Character) target).targetPosition;
			}else{
				targetLocation = ((Entity) target).position;
			}
			
			if(!isPathing){
				int dx = (targetLocation.x - this.position.x)/TopDownGraphics.tileWidthHeight_Pixels;
				int dy = (targetLocation.y - this.position.y)/TopDownGraphics.tileWidthHeight_Pixels;
				
				Point currentLocation = this.getMapLocation();
				
				Point adjacentTarget = null;
				if(Math.abs(dy) > Math.abs(dx) && Math.abs(dy) > 1){
					adjacentTarget = new Point(currentLocation.x, 
							(int) (currentLocation.y + Math.signum(dy)));
				}
				
				if((Math.abs(dy) <= Math.abs(dx) ||
						(adjacentTarget != null && 
							Main.getLevel().getCollsionMap()[adjacentTarget.y][adjacentTarget.x]))
						 && Math.abs(dx) > 1){
					adjacentTarget = new Point(
							(int) (currentLocation.x + Math.signum(dx)), 
							currentLocation.y);
				}
				
				if(adjacentTarget != null &&
						!Main.getLevel().getCollsionMap()[adjacentTarget.y][adjacentTarget.x]){
					this.targetPosition = new Point(
							adjacentTarget.x * TopDownGraphics.tileWidthHeight_Pixels,
							adjacentTarget.y * TopDownGraphics.tileWidthHeight_Pixels);
				}
			
			}
		}
	}

	public void setTarget(Object target) {
		this.target = target;
	}
}