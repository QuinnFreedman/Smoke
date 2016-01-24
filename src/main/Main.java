package main;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.DebugGraphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class Main{
	private static boolean gamePaused = false;
	private static int t = 0;
	private static final int FRAMES_PER_SECOND = 32;
	private static final int DELAY = 1000/FRAMES_PER_SECOND;
	protected static final int KEYFRAME_INTERVAL = 6;
	private static PlayerCharacter player;
	private static Level level;
	static JPanel canvas;
	
	static Level getLevel() {
		return level;
	}
	static int getTime() {
		return t;
	}
	static PlayerCharacter getPlayer(){
		return player;
	}
	//******************************************
	//Utility
	//******************************************	
	public static BufferedImage loadImage(String address){
		address = "/images/"+address+".png";
		System.out.println("loading "+address+"...");
		BufferedImage image = null;
		try {
			image = ImageIO.read(Main.class.getResource(address));
		} catch (Exception e) {
			System.err.println("Error: "+address+" not found!");
			e.printStackTrace();
		}
		return image;
	}
	
	//##########################################
	//SETUP
	public static void main(String[] args){
		Setup();
		
		//new PathingDebug();
		gameLoop();
	}
	
	private static void Setup(){
		//make window
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		canvas = new Renderer.RederPanel();
		window.getContentPane().add(canvas);
		canvas.setPreferredSize(new Dimension(
				(int) (TopDownGraphics.getViewportSize().width * 
						TopDownGraphics.tileWidthHeight_Pixels * Renderer.scale),
				(int) (TopDownGraphics.getViewportSize().height * 
						TopDownGraphics.tileWidthHeight_Pixels * Renderer.scale)));
		window.pack();
		window.setFocusTraversalKeysEnabled(false);
		window.addKeyListener(new KeyboardHandler());
		window.setVisible(true);
		
		//make world
		/*int[][] map = new int[100][100];
		for(int y = 0; y < map.length; y++){
			for (int x = 0; x < map[0].length; x++) {
				map[y][x] = (int) Math.round(Math.random()+0.3);
			}
		}*/
		
		TopDownGraphics.loadTextures();
		WorldBuilder.WorldData map = WorldBuilder.buildWorld();
		level = new Level(map.levelTextures, map.staticSprites);
		//make world
		
		player = new PlayerCharacter(level,
				new Point(5, 4), 
				Character.Race.HUMAN, 
				"Mage");
		
		//initialize graphics
		Renderer.initGraphics();
		
		////DEBUG
		/*NPCCharacter testNPC = new NPCCharacter(level,
				new Point((int) (Math.random()*8), (int) (Math.random()*9)), 
				Character.Race.HUMAN, 
				"Assassin");
		testNPC.setTarget(player);
		testNPC.setFollowMode(NPCCharacter.AIMode.FLEE);
		NPCCharacter testNPC2 = new NPCCharacter(level,
				new Point((int) (Math.random()*8), (int) (Math.random()*9)), 
				Character.Race.HUMAN, 
				"Assassin");
		testNPC2.setTarget(player);
		testNPC2.setFollowMode(NPCCharacter.AIMode.FLEE);*/
		/*NPCCharacter testNPC3 = new NPCCharacter(level,
				new Point((int) (Math.random()*9), (int) (Math.random()*9)), 
				Character.Race.HUMAN, 
				"Knight");
		testNPC3.setTarget(player);
		testNPC3.setFollowMode(NPCCharacter.AIMode.TRAIL);*/
		
		new Tree(level, new Point(3,4), 1, 10);

	}
	//##########################################
	
	
	//##########################################
	//Game Loop
	static void gameLoop(){
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
		ArrayList<Chunk> activeChunks = level.getAdjacentChunks(player.getMapLocation());
		
		for (Chunk chunk : activeChunks) {
			chunk.updateEntities();
			for(Entity entity : chunk.getEntities()){
				if(entity instanceof Character){
					((Character) entity).simulate();
				}
			}
			chunk.updateEntities();
		}
	}
	
	//draw sprites
	private static void render(){
		Renderer.render();
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