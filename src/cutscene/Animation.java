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
	private int t = 0;
	private boolean paused = false;
	private int delayTime = 0;
	private Point position;
	private Dimension size;
	private int startTime = 0;
	private int endTime = -1;
	
	public Animation(String path, mode animationMode, Point position, Dimension size) {
		this.animationMode = animationMode;
		this.position = position;
		this.size = size;
		
		File dir = new File("./assets/images/"+path);
		assert dir.exists() && dir.isDirectory();
		
		File[] files = dir.listFiles();
		for(File f : files) {
			String str = f.getPath();
			frames.add(main.Main.loadImage(str.substring(16, str.lastIndexOf('.'))));
		}
	}
	
	int getFrames() {
		return frames.size();
	}
	
	void step() {
		if(paused) {
			return;
		}
		
		switch(animationMode){
		case FIRST_FRAME:
			currentFrame = 0;
			break;
		case LOOP:
			currentFrame = (currentFrame + 1) % getFrames();
			break;
		case LOOP_DELAY:
			t = (t + 1) % (getFrames() + delayTime);
			currentFrame = (t >= getFrames()) ? 0 : t;
			break;
		case LOOP_ONCE:
			currentFrame++;
			if(currentFrame >= getFrames()) {
				currentFrame = 0;
				paused = true;
			}
			break;
		case PLAY_ONCE:
			currentFrame++;
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
	
	void setDelayTime(int time) {
		this.delayTime = time;
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
		if(t < this.startTime) {
			return;
		}
		this.step();
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