import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class PlayerCharacter extends Character{
	static boolean leftPressed  = false;
	static boolean rightPressed = false;
	static boolean upPressed    = false;
	static boolean downPressed  = false;
	private static Direction moveDirection = Direction.NONE;
	
	private Character.AnimationSet sprite;
	
	public BufferedImage getSprite(int t) {
		if(PlayerCharacter.moveDirection == Direction.NONE) {
			return this.sprite.get(Direction.SHOUTH)
					   .get(0);
		} else {
			return this.sprite.get(PlayerCharacter.moveDirection)
							  .get((t / 2) % this.animFrames);
		}
	}
	
	PlayerCharacter(Level level, Point mapPosition, Race race, String cclass) {
		super(level, mapPosition, race, cclass);
		this.animFrames = 7;
		this.sprite = new Character.AnimationSet("Harper", this.animFrames);
		
	}
	
	@Override
	void doLogic() {
		super.doLogic();
		int dx = 0;
		int dy = 0;
		if(upPressed) {
			moveDirection = Direction.NORTH;
		} else if(downPressed) {
			moveDirection = Direction.SHOUTH;
		} else if(leftPressed) {
			moveDirection = Direction.WEST;
		} else if(rightPressed) {
			moveDirection = Direction.EAST;
		} else {
			moveDirection = Direction.NONE;
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
		
		
		Point tilePoint = new Point(
				this.previousPosition.x/TopDownGraphics.tileWidthHeight_Pixels + dx,
				this.previousPosition.y/TopDownGraphics.tileWidthHeight_Pixels + dy);
		
		if(!Main.getLevel().collides(tilePoint)){
			this.moveTo(tilePoint);
		} else {
			this.moveDirection = Direction.NONE;
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
	
	static void handleKeyboardInput(KeyEvent e, boolean keyPressed){
		int c = e.getKeyCode();
		if(c == KeyEvent.VK_LEFT){
			leftPressed = keyPressed;
			if(keyPressed){
				//moveDirection = moveDirection == Direction.EAST ? Direction.NONE : Direction.WEST;
				rightPressed = false;
				downPressed = false;
				upPressed = false;
			}
		}else if(c == KeyEvent.VK_RIGHT){
			rightPressed = keyPressed;
			if(keyPressed){
				//moveDirection = moveDirection == Direction.WEST ? Direction.NONE : Direction.EAST;
				leftPressed = false;
				downPressed = false;
				upPressed = false;
			}
		}else if(c == KeyEvent.VK_UP){
			upPressed = keyPressed;
			if(keyPressed){
				//moveDirection = moveDirection == Direction.SHOUTH ? Direction.NONE : Direction.NORTH;
				leftPressed = false;
				rightPressed = false;
				downPressed = false;
			}
		}else if(c == KeyEvent.VK_DOWN){
			downPressed = keyPressed;
			if(keyPressed){
				//moveDirection = moveDirection == Direction.NORTH ? Direction.NONE : Direction.SHOUTH;
				leftPressed = false;
				rightPressed = false;
				upPressed = false;
			}
		}
	}
}