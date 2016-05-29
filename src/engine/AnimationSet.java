package engine;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import engine.DynamicEntity.Direction;

public class AnimationSet {
		private ArrayList<BufferedImage> north;
		private ArrayList<BufferedImage> south;
		private ArrayList<BufferedImage> east;
		private ArrayList<BufferedImage> west;
		private ArrayList<BufferedImage> idle;
		
		private ArrayList<BufferedImage> getByName(String s){
			switch(s){
			case "up":
				return this.north;
			case "down":
				return this.south;
			case "left":
				return this.west;
			case "right":
				return this.east;
			default:
				return null;
			}
		}
		/*
		void setWest(ArrayList<BufferedImage> img) {
			this.west = img;
		}
		void setEast(ArrayList<BufferedImage> img) {
			this.east = img;
		}
		void setNorth(ArrayList<BufferedImage> img) {
			this.north = img;
		}
		void setSouth(ArrayList<BufferedImage> img) {
			this.south = img;
		}
		void setIdle(ArrayList<BufferedImage> img) {
			this.south = img;
		}
		*/
		
		public ArrayList<BufferedImage> get(Direction direction) {
			switch(direction){
			case EAST:
				return east;
			case NONE:
				return idle;
			case NORTH:
				return north;
			case SHOUTH:
				return south;
			case WEST:
				return west;
			default:
				return null;
			
			}
		}
		
		private AnimationSet() {
			north = new ArrayList<BufferedImage>();
			south = new ArrayList<BufferedImage>();
			east = new ArrayList<BufferedImage>();
			west = new ArrayList<BufferedImage>();
			idle = new ArrayList<BufferedImage>();
		}
		
		public AnimationSet(final String directory, final String name, final int numFrames) {
			this();
			
			final String[] directions = {"up", "down", "left", "right"};
			String file = "dynamic_entities/"+directory+"/"+name+"/";
			for(String s : directions) {
				String dir = file + name + "_" + s + "/" + name + "_" + s + "_";
				ArrayList<BufferedImage> frames = new ArrayList<BufferedImage>();
				for(int i = 0; i < numFrames; i++) {
					frames.add(Main.loadImage(dir + i));
				}
				this.getByName(s).addAll(frames);
			}
			
		}
		
		public AnimationSet(final String spritesheet, final int numFrames) {
			this();
			
			final String[] directions = {"left", "right", "down", "up"};
			final BufferedImage sheet = Main.loadImage("dynamic_entities/"+spritesheet);
			
			for(int y = 0; y < 4; y++) {
				ArrayList<BufferedImage> frames = new ArrayList<BufferedImage>();
				for(int x = 0; x < numFrames; x++) {
					frames.add(sheet.getSubimage(x*32, y*32, 32, 32));
				}
				this.getByName(directions[y]).addAll(frames);
			}
		}
		
		public static BufferedImage getSingleFrame(final String spritesheet) {
			final BufferedImage sheet = Main.loadImage("dynamic_entities/"+spritesheet);
			return sheet.getSubimage(0, 0, 32, 32);
		}
	}