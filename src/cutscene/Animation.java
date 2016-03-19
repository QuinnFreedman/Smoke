package cutscene;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import main.Point;

public class Animation {
	private ArrayList<BufferedImage> frames = new ArrayList<BufferedImage>();
	private int currentFrame = 0;
	private mode animationMode;
	private boolean paused = false;
	private int delayTime = 0;
	private Point position;
	private Dimension size;
	private int startTime = 0;
	private int endTime = -1;
	private int t_start = 0;
	private File framesDir;
	boolean loaded = false;
	private float animSpeed = .5f;
	
	public Animation(String path, mode animationMode, Point position, Dimension size) {
		this.animationMode = animationMode;
		this.position = position;
		this.size = size;
		
		framesDir = new File(getClass().getClassLoader().getResource("images").getFile());
		framesDir = new File(framesDir.getPath()+"/"+path);
		if(!(framesDir.exists() && framesDir.isDirectory())) {
			System.err.println("Directory \""+framesDir.getPath()+"\" does not exsist");
		}
		
		if(size == null) {
			this.load();
			this.size = new Dimension(
					this.frames.get(0).getWidth(), this.frames.get(0).getHeight());
		}
	}
	
	void load(){
		if(!this.loaded){
			System.out.println("loading animation frames from: "+framesDir.getPath());
			File[] files = framesDir.listFiles();
			System.out.println(files);
			for(File f : files) {
				String str = f.getPath();
				frames.add(main.Main.loadImage(
						str.substring(str.lastIndexOf("images") + 7, str.lastIndexOf('.'))));
			}
			this.loaded = true;
		}
	}
	
	int getFrames() {
		return frames.size();
	}
	
	void incrementCurrentFrame(int t) {
		currentFrame = (t - t_start);
	}
	
	void step(int t) {
		if(paused) {
			return;
		}
		t = Math.round(t * animSpeed);
		
		if(t_start == 0) {
			t_start = t;
		}
		
		switch(animationMode){
		case FIRST_FRAME:
			currentFrame = 0;
			break;
		case LOOP:
			incrementCurrentFrame(t);
			currentFrame %= getFrames();
			break;
		case LOOP_DELAY:
			incrementCurrentFrame(t);
			int t2 = t % (getFrames() + delayTime);
			currentFrame = (t2 >= getFrames()) ? 0 : t2;
			break;
		case LOOP_ONCE:
			incrementCurrentFrame(t);
			if(currentFrame >= getFrames()) {
				currentFrame = 0;
				paused = true;
			}
			break;
		case PLAY_ONCE:
			incrementCurrentFrame(t);
			if(currentFrame >= getFrames()) {
				currentFrame = getFrames() - 1;
				paused = true;
			}
			break;
		default:
			break;
		}
	}
	
	void reset() {
		currentFrame = 0;
		paused = false;
	}
	
	Animation setDelayTime(int time) {
		this.delayTime = time;
		return this;
	}
	
	boolean hasExpired(int t) {
		return (endTime == t);
	}

	void setEndTime(int endTime) {
		this.endTime = endTime;
	}
	
	void setStartTime(int time) {
		this.startTime = time;
	}
	
	void draw(Graphics2D g, int t) {
		if(t < this.startTime || !this.loaded) {
			return;
		}
		this.step(t);
		g.drawImage(frames.get(currentFrame), 
				position.x, position.y, 
				size.width, size.height, 
				null);
	}

	enum mode {
		LOOP,
		LOOP_DELAY,
		LOOP_ONCE,
		PLAY_ONCE,
		FIRST_FRAME
	}
}