package gameplay;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import engine.AnimationSet;
import engine.DynamicEntity;
import engine.Level;
import engine.Point;
import engine.TopDownGraphics;

public class Character extends DynamicEntity {
	private Race race;
	private String cclass;
	private boolean male;
	private ItemSet itemSet;
	
	private BufferedImage compositeSprite;
	private Graphics2D compositeGraphics;
	
	
	Race getRace(){ return this.race; }
	String getCclass(){ return this.cclass; }
	
	protected Character(Level level, Point mapPosition, Boolean male, Race race, String cclass){
		super(level, mapPosition);
		this.male = male;
		this.race = race;
		this.cclass = cclass;
		
		compositeSprite = new BufferedImage(TopDownGraphics.tileWidthHeight_Pixels, 
				TopDownGraphics.tileWidthHeight_Pixels, BufferedImage.TYPE_INT_ARGB);
		compositeGraphics = compositeSprite.createGraphics();
		compositeGraphics.setBackground(new Color(0,0,0,0));
		
		//TODO switch on race
		if(male) {
			this.sprite = new AnimationSet("character_tiles/male_spritesheet");
		} else {
			this.sprite = new AnimationSet("character_tiles/female_spritesheet");
		}
		
		itemSet = new ItemSet();
		switch(cclass) {
		case "Assassin":
			itemSet.put(ItemSet.HEAD, new Item.AssassinHead());
			itemSet.put(ItemSet.CHEST, new Item.AssassinChest());
			itemSet.put(ItemSet.FEET, new Item.AssassinFeet());
			break;
		case "Knight":
			itemSet.put(ItemSet.HEAD, new Item.KnightHead());
			itemSet.put(ItemSet.CHEST, new Item.KnightChest());
			itemSet.put(ItemSet.FEET, new Item.KnightFeet());
			break;
		case "Mage":
			itemSet.put(ItemSet.HEAD, new Item.MageHead());
			itemSet.put(ItemSet.CHEST, new Item.MageChest());
			itemSet.put(ItemSet.FEET, new Item.MageFeet());
			break;
		}
	}
	
	@Override
	protected BufferedImage getSprite(int t) {
		final int size = TopDownGraphics.tileWidthHeight_Pixels;
		BufferedImage base = super.getSprite(t);
		compositeGraphics.clearRect(0, 0, size, size);
		compositeGraphics.drawImage(base, 0, 0, size, size, null);
		drawItem(t, ItemSet.HEAD);
		drawItem(t, ItemSet.CHEST);
		drawItem(t, ItemSet.FEET);
		
		return compositeSprite;
	}
	
	private void drawItem(int t, int itemSlot) {
		Item item = itemSet.get(itemSlot);
		if(item != null) {
			AnimationSet animSet = male ? item.getMaleAnimationSet() : item.getFemaleAnimationSet();
			if(animSet != null) {
				BufferedImage image;
				if(getFramesPerStep() > 0) {
					t = Math.round((float) t/(float) inverseSpeed);
				}
				
				if(this.getMoveDirection() == Direction.NONE || this.getMoveDirection() == null) {
					image = animSet.get(this.getFacingDirection()).get(0);
				} else {
					image = animSet.get(this.getMoveDirection()).get((t) % this.animFrames);
				}
				if(image != null) {
					compositeGraphics.drawImage(image, 0, 0, 
							TopDownGraphics.tileWidthHeight_Pixels, 
							TopDownGraphics.tileWidthHeight_Pixels, null);
				}
			}
		}
	}
	
	public static enum Race{
		HUMAN,ELF,ORC
	}
}