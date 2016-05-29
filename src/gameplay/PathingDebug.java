package gameplay;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import engine.Point;

class PathingDebug extends JFrame {
	private JPanel canvas;
	private Dimension gridSize = new Dimension(20,20);
	private int scale = 20;
	boolean[][] collision = new boolean[gridSize.height][gridSize.width];
	ArrayList<Point> path;
	Point start;
	Point end;
	
	PathingDebug() {
		this.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				doStuff();
				canvas.repaint();
				
			}
		});
		for (int y = 0; y < collision.length; y++) {
			for (int x = 0; x < collision[0].length; x++) {
				collision[y][x] = (Math.random() > .65);
			}
		}
		doStuff();
		
		canvas = new specialPanel();
		canvas.setPreferredSize(new Dimension(gridSize.width * scale, gridSize.height * scale));
		this.getContentPane().add(canvas);
		this.pack();
		this.setVisible(true);
	}
	
	void doStuff(){
		start = new Point((int) Math.floor(Math.random() * gridSize.width), 
				(int) Math.floor(Math.random() * gridSize.width));
		end = new Point((int) Math.floor(Math.random() * gridSize.width), 
				(int) Math.floor(Math.random() * gridSize.width));
		path = Pathing.getPath(start, end, collision);
	}
	
	@SuppressWarnings("serial")
	class specialPanel extends JPanel {
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.black);
			for (int y = 0; y < collision.length; y++) {
				for (int x = 0; x < collision[0].length; x++) {
					if(collision[y][x]){
						g.fillRect(x*scale, y*scale, scale, scale);
					}
				}
			}
			g.setColor(Color.yellow);
			for(Point p : path){
				g.fillRect(p.x*scale, p.y*scale, scale, scale);
			}
			g.setColor(Color.GREEN);
			g.fillRect(start.x*scale, start.y*scale, scale, scale);
			g.setColor(Color.RED);
			g.fillRect(end.x*scale, end.y*scale, scale, scale);
		}
	}
}