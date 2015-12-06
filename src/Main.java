import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

abstract public class Main{
	private static boolean gamePaused = false;
	private static int t = 0;
	private static final int FRAMES_PER_SECOND = 32;
	private static final int DELAY = 1000/FRAMES_PER_SECOND;
	protected static final int KEYFRAME_INTERVAL = 6;
	private static PlayerCharacter player;
	private static Level level;
	static JPanel canvas;
	
	//DEBUG
	static NPCCharacter testNPC;
	
	public static Level getLevel() {
		return level;
	}
	public static int getTime() {
		return t;
	}
	public static PlayerCharacter getPlayer(){
		return player;
	}
	//******************************************
	//Utility
	//******************************************	
	public static BufferedImage loadImage(String address){
		address = "images/"+address+".png";
		System.out.println(address+"...");
		BufferedImage image = null;
		try {
			image = ImageIO.read(Main.class.getResource(address));
		} catch (IOException e) {
			System.out.println(address+" not found!");
			e.printStackTrace();
		}
		return image;
	}
	
	//##########################################
	//SETUP
	public static void main(String[] args){
		Setup();
	}
	
	private static void Setup(){
		//make window
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		canvas = new Renderer.RederPanel();
		window.getContentPane().add(canvas);
		canvas.setPreferredSize(new java.awt.Dimension(
				(int) (TopDownGraphics.getViewportSize().width * 
						TopDownGraphics.tileWidthHeight_Pixels * Renderer.scale),
				(int) (TopDownGraphics.getViewportSize().height * 
						TopDownGraphics.tileWidthHeight_Pixels * Renderer.scale)));
		window.pack();
		window.setFocusTraversalKeysEnabled(false);
		window.addKeyListener(new KeyboardHandler());
		window.setVisible(true);
		
		player = new PlayerCharacter(new Point(
				5*TopDownGraphics.tileWidthHeight_Pixels,
				5*TopDownGraphics.tileWidthHeight_Pixels), 
			Character.Race.HUMAN, 
			"Mage");
		testNPC = new NPCCharacter(new Point(
				((int) (Math.random()*9))*TopDownGraphics.tileWidthHeight_Pixels,
				((int) (Math.random()*9))*TopDownGraphics.tileWidthHeight_Pixels), 
			Character.Race.HUMAN, 
			"Assassin");
		testNPC.setTarget(player);
		testNPC.setFollowMode(NPCCharacter.AIMode.FLEE);
		int[][] map = new int[10][10];
		for(int y = 0; y < 10; y++){
			for (int x = 0; x < 10; x++) {
				map[y][x] = (int) Math.round(Math.random()+0.3);
			}
		}
		TopDownGraphics.loadTextures();
		level = new Level(map);
		//make world
		//pass bits of world to Chunk()
		gameLoop();
	}
	//##########################################
	
	
	//##########################################
	//Game Loop
	public static void gameLoop(){
		while(!gamePaused){
			simulate();
			render();
			
			t++;
			
			try {
				Thread.sleep(DELAY);
			} catch (InterruptedException e) {e.printStackTrace();}
		}
	}
	
	//move sprites
	private static void simulate(){
		player.simulate();
		testNPC.simulate();
	}
	
	//draw sprites
	private static void render(){
		canvas.repaint();
	}
	
	private static class KeyboardHandler implements KeyListener{

		@Override
		public void keyPressed(KeyEvent e) {
			switch(Renderer.renderMode){
			case COMBAT:
				break;
			case CUTSCENE:
				break;
			case INVENTORY:
				break;
			case MENU:
				break;
			case WORLD:
				PlayerCharacter.handleKeyboardInput(e, true);
				break;
			default:
				break;
			}
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			switch(Renderer.renderMode){
			case COMBAT:
				break;
			case CUTSCENE:
				break;
			case INVENTORY:
				break;
			case MENU:
				break;
			case WORLD:
				PlayerCharacter.handleKeyboardInput(e, false);
				break;
			default:
				break;
			}
		}

		@Override
		public void keyTyped(KeyEvent e){}
		
	}
}