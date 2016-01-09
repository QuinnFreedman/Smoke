import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class PlayerCharacter extends Character{
	static boolean leftPressed  = false;
	static boolean rightPressed = false;
	static boolean upPressed    = false;
	static boolean downPressed  = false;
	private static Direction moveDirection = Direction.NONE;
	private static Direction facingDirection = Direction.SHOUTH;
	
	private Character.AnimationSet sprite;
	
	public BufferedImage getSprite(int t) {
		if(PlayerCharacter.moveDirection == Direction.NONE) {
			return this.sprite.get(PlayerCharacter.facingDirection).get(0);
		} else {
			return this.sprite.get(PlayerCharacter.moveDirection)
							  .get((t) % this.animFrames);
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
			facingDirection = moveDirection;
		} else if(downPressed) {
			moveDirection = Direction.SHOUTH;
			facingDirection = moveDirection;
		} else if(leftPressed) {
			moveDirection = Direction.WEST;
			facingDirection = moveDirection;
		} else if(rightPressed) {
			moveDirection = Direction.EAST;
			facingDirection = moveDirection;
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
		
	}
	
	@Override
	void simulate() {
		super.simulate();
		final float dx = (this.targetPosition.x - this.previousPosition.x)/inverseSpeed;
		final float dy = (this.targetPosition.y - this.previousPosition.y)/inverseSpeed;
		Atmosphere.move(dx * -0.5f, dy * -0.5f);
	}
	
	static void handleKeyboardInput(KeyEvent e, boolean keyPressed){
		int c = e.getKeyCode();
		if(c == KeyEvent.VK_LEFT){
			leftPressed = keyPressed;
			if(keyPressed){
				facingDirection = Direction.WEST;
				rightPressed = false;
				downPressed = false;
				upPressed = false;
			}
		}else if(c == KeyEvent.VK_RIGHT){
			rightPressed = keyPressed;
			if(keyPressed){
				facingDirection = Direction.EAST;
				leftPressed = false;
				downPressed = false;
				upPressed = false;
			}
		}else if(c == KeyEvent.VK_UP){
			upPressed = keyPressed;
			if(keyPressed){
				facingDirection = Direction.NORTH;
				leftPressed = false;
				rightPressed = false;
				downPressed = false;
			}
		}else if(c == KeyEvent.VK_DOWN){
			downPressed = keyPressed;
			if(keyPressed){
				facingDirection = Direction.SHOUTH;
				leftPressed = false;
				rightPressed = false;
				upPressed = false;
			}
		}
	}
}