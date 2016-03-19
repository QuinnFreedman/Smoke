package main;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

class Character extends DynamicEntity {
	private Race race;
	private String cclass;
	
	Race getRace(){ return this.race; }
	String getCclass(){ return this.cclass; }
	
	protected Character(Level level, Point mapPosition, Race race, String cclass){
		super(level, mapPosition);
		this.race = race;
		this.cclass = cclass;
	}
	
	static enum Race{
		HUMAN,ELF,ORC
	}
}