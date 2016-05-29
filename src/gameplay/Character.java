package gameplay;

import engine.DynamicEntity;
import engine.Level;
import engine.Point;

public class Character extends DynamicEntity {
	private Race race;
	private String cclass;
	private boolean male;
	
	Race getRace(){ return this.race; }
	String getCclass(){ return this.cclass; }
	
	protected Character(Level level, Point mapPosition, Boolean male, Race race, String cclass){
		super(level, mapPosition);
		this.male = male;
		this.race = race;
		this.cclass = cclass;
	}
	
	public static enum Race{
		HUMAN,ELF,ORC
	}
}