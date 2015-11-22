import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class PlayerCharacter extends Character{
	static boolean leftPressed  = false;
	static boolean rightPressed = false;
	static boolean upPressed    = false;
	static boolean downPressed  = false;
	private static Direction moveDirection = Direction.NONE;
	//private final int diagonalSpeed = (int) (this.speed/Math.sqrt(2));
	
	//DEBUG
	private BufferedImage sprite;
	
	public BufferedImage getSprite(int t) {
		return this.sprite;
	};
	
	PlayerCharacter(Point position, Race race, String cclass) {
		super(position, race, cclass);
		this.sprite = Main.loadImage("character_tiles/"+this.getRace()+"_"+this.getCclass());
	}
	
	@Override
	void doLogic() {
		super.doLogic();
		int dx = 0;
		int dy = 0;
		if(upPressed){
			moveDirection = Direction.NORTH;
		}else if(downPressed){
			moveDirection = Direction.SHOUTH;
		}else if(leftPressed){
			moveDirection = Direction.WEST;
		}else if(rightPressed){
			moveDirection = Direction.EAST;
		}
		
		switch (moveDirection) {
		case NORTH:
			dy = -1;
			break;
		case SHOUTH:
			dy = 1;
			break;
		case EAST:
			dx = 1;
			break;
		case WEST:
			dx = -1;
			break;
		case NONE:
			break;
		}
		moveDirection = Direction.NONE;
		
		int tile_y = this.previousPosition.y/TopDownGraphics.tileWidthHeight_Pixels + dy;
		int tile_x = this.previousPosition.x/TopDownGraphics.tileWidthHeight_Pixels + dx;
		
		if(tile_x >= 0 && tile_x < Main.getLevel().getSize().width &&
				tile_y >= 0 && tile_y < Main.getLevel().getSize().height &&
				!Main.getLevel().getCollsionMap()[tile_y][tile_x]){
			this.targetPosition = new Point(this.previousPosition.x + dx * TopDownGraphics.tileWidthHeight_Pixels, this.previousPosition.y + dy * TopDownGraphics.tileWidthHeight_Pixels);
		}
		
		/*
		int y_axis = 0;
		int x_axis = 0;
		if(leftPressed && !rightPressed){
			x_axis = -1;
		}else if(rightPressed && !leftPressed){
			x_axis = 1;
		}
		
		if(upPressed && !downPressed){
			y_axis = -1;
		}else if(downPressed && !upPressed){
			y_axis = 1;
		}
		if(y_axis != 0 && x_axis != 0){
			this.targetPosition.x += x_axis*diagonalSpeed;
			this.targetPosition.y += y_axis*diagonalSpeed;
		}else{
			this.targetPosition.x += x_axis*this.speed;
			this.targetPosition.y += y_axis*this.speed;
		}
		*/
	}
	
	private static enum Direction{
		NORTH,SHOUTH,EAST,WEST,NONE
	}
	
	static void handleKeyboardInput(KeyEvent e, boolean keyPressed){
		int c = e.getKeyCode();
		if(c == KeyEvent.VK_LEFT){
			leftPressed = keyPressed;
			if(keyPressed){
				moveDirection = moveDirection == Direction.EAST ? Direction.NONE : Direction.WEST;
				rightPressed = false;
				downPressed = false;
				upPressed = false;
			}
		}else if(c == KeyEvent.VK_RIGHT){
			rightPressed = keyPressed;
			if(keyPressed){
				moveDirection = moveDirection == Direction.WEST ? Direction.NONE : Direction.EAST;
				leftPressed = false;
				downPressed = false;
				upPressed = false;
			}
		}else if(c == KeyEvent.VK_UP){
			upPressed = keyPressed;
			if(keyPressed){
				moveDirection = moveDirection == Direction.SHOUTH ? Direction.NONE : Direction.NORTH;
				leftPressed = false;
				rightPressed = false;
				downPressed = false;
			}
		}else if(c == KeyEvent.VK_DOWN){
			downPressed = keyPressed;
			if(keyPressed){
				moveDirection = moveDirection == Direction.NORTH ? Direction.NONE : Direction.SHOUTH;
				leftPressed = false;
				rightPressed = false;
				upPressed = false;
			}
		}
	}
}