package menu;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import engine.AnimationSet;
import engine.Main;
import engine.TopDownGraphics;
import menu.Menu.MenuItem;
import menu.Menu.Selection;

public class StartMenu {
	private static Menu menu;
	private static final String CHOOSE_YOUR_CHARACTER = "Choose Your Character";
	private static final String CHOOSE_YOUR_CLASS = "Choose Your Class";
	private static final String CHOOSE_YOUR_RACE = "Choose Your Race";
	
	private static List<List<BufferedImage>> preview = new ArrayList<>();
	
	private static HashMap<String, BufferedImage> preLoadedImages = new HashMap<>();
	
	static {
		preLoadedImages.put("Male", AnimationSet.getSingleFrame("character_tiles/male_spritesheet"));
		preLoadedImages.put("Female", AnimationSet.getSingleFrame("character_tiles/female_spritesheet"));
		
		//assassin
		preLoadedImages.put("assassin_chest_male", 
				AnimationSet.getSingleFrame("character_tiles/armor_assassin/assassin_chest_male"));
		preLoadedImages.put("assassin_chest_female", 
				AnimationSet.getSingleFrame("character_tiles/armor_assassin/assassin_chest_female"));
		preLoadedImages.put("assassin_feet_male", 
				AnimationSet.getSingleFrame("character_tiles/armor_assassin/assassin_feet_male"));
		preLoadedImages.put("assassin_feet_female", 
				AnimationSet.getSingleFrame("character_tiles/armor_assassin/assassin_feet_female"));
		preLoadedImages.put("assassin_head_male", 
				AnimationSet.getSingleFrame("character_tiles/armor_assassin/assassin_head_male"));
		preLoadedImages.put("assassin_head_female", 
				AnimationSet.getSingleFrame("character_tiles/armor_assassin/assassin_head_female"));

		//knight
		preLoadedImages.put("knight_chest_male", 
				AnimationSet.getSingleFrame("character_tiles/armor_knight/knight_chest_male"));
		preLoadedImages.put("knight_chest_female", 
				AnimationSet.getSingleFrame("character_tiles/armor_knight/knight_chest_female"));
		preLoadedImages.put("knight_feet_male", 
				AnimationSet.getSingleFrame("character_tiles/armor_knight/knight_feet_male"));
		preLoadedImages.put("knight_feet_female", 
				AnimationSet.getSingleFrame("character_tiles/armor_knight/knight_feet_female"));
		preLoadedImages.put("knight_head_male", 
				AnimationSet.getSingleFrame("character_tiles/armor_knight/knight_head_male"));
		preLoadedImages.put("knight_head_female", 
				AnimationSet.getSingleFrame("character_tiles/armor_knight/knight_head_female"));
		
		//mage
		preLoadedImages.put("mage_chest_male", 
				AnimationSet.getSingleFrame("character_tiles/armor_mage/mage_chest_male"));
		preLoadedImages.put("mage_chest_female", 
				AnimationSet.getSingleFrame("character_tiles/armor_mage/mage_chest_female"));
		preLoadedImages.put("mage_feet_male", 
				AnimationSet.getSingleFrame("character_tiles/armor_mage/mage_feet_male"));
		preLoadedImages.put("mage_feet_female", 
				AnimationSet.getSingleFrame("character_tiles/armor_mage/mage_feet_female"));
		preLoadedImages.put("mage_head_male", 
				AnimationSet.getSingleFrame("character_tiles/armor_mage/mage_head_male"));
		preLoadedImages.put("mage_head_female", 
				AnimationSet.getSingleFrame("character_tiles/armor_mage/mage_head_female"));
		
		preview.add(Arrays.asList(preLoadedImages.get("Male")));
		
		
		MenuItem[] chooseClass = new MenuItem[] {
				new MenuItem("Assassin"),
				new MenuItem("Knight"),
				new MenuItem("Mage")
			};
		MenuItem[] chooseRace = new MenuItem[] {
				new MenuItem("Human", CHOOSE_YOUR_CLASS, chooseClass),
				new MenuItem("Elf", CHOOSE_YOUR_CLASS, chooseClass),
				new MenuItem("Orc", CHOOSE_YOUR_CLASS, chooseClass)
			};
		
		menu = new Menu("Choose Your Character",
				new MenuItem("Male", CHOOSE_YOUR_RACE, chooseRace),
				new MenuItem("Female", CHOOSE_YOUR_RACE, chooseRace)
			);
	}
	public static void render(Graphics2D graphics) {
		graphics.drawString(menu.getHeading(), 100, 100);
		List<MenuItem> items = menu.getItems();
		for(int i = 0; i < items.size(); i++) {
			String s = items.get(i).getText();
			String prefix = items.get(i).isSelected() ? " * " : "      ";
			graphics.drawString(prefix+s+prefix, 100, 100 + (i + 1) * 14);
		}
		
		for(int i = 0; i < preview.size(); i++) {
			List<BufferedImage> list = preview.get(i);
			if(list != null) {
				for(BufferedImage image : list) {
					graphics.drawImage(image, 300, 100, TopDownGraphics.tileWidthHeight_Pixels * 2,
							TopDownGraphics.tileWidthHeight_Pixels * 2, null);
				}
			}
		}
		
	}

	private static boolean male;
	private static String race;
	private static String cClass;
	public static void handleKeyboardInput(KeyEvent e, boolean keyPressed) {
		if(keyPressed) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				menu.moveUp();
				break;
			case KeyEvent.VK_DOWN:
				menu.moveDown();
				break;
			case KeyEvent.VK_ENTER:
				String page = menu.getHeading();
				Selection selection = menu.select();
				switch(page) {
				case CHOOSE_YOUR_CHARACTER:
					male = selection.getValue().equals("Male");
					preview.add(null);
					break;
				case CHOOSE_YOUR_RACE:
					race = selection.getValue();
					preview.add(null);
					break;
				case CHOOSE_YOUR_CLASS:
					cClass = selection.getValue();
					Main.setPlayer(male, race, cClass);
					Main.start();
					//unload images
					preLoadedImages = null;
					preview = null;
					return;
				}
				break;
			case KeyEvent.VK_ESCAPE:
				menu.moveBack();
				preview.remove(preview.size() - 1);
				break;
			default:
				break;
			}
			
			//update preview
			String page = menu.getHeading();
			String selected = menu.getSelected();
			switch(page) {
			case CHOOSE_YOUR_CHARACTER:
				if(selected.equals("Male")) {
					preview.set(0, Arrays.asList(preLoadedImages.get("Male")));
				} else {
					preview.set(0, Arrays.asList(preLoadedImages.get("Female")));
				}
				break;
			case CHOOSE_YOUR_RACE:
				break;
			case CHOOSE_YOUR_CLASS:
				String sex = male ? "male" : "female";
				switch(selected) {
				case "Assassin":
					preview.set(2, Arrays.asList(
							preLoadedImages.get("assassin_chest_"+sex),
							preLoadedImages.get("assassin_foot_"+sex),
							preLoadedImages.get("assassin_head_"+sex)));
					break;
				case "Knight":
					preview.set(2, Arrays.asList(
							preLoadedImages.get("knight_chest_"+sex),
							preLoadedImages.get("knight_foot_"+sex),
							preLoadedImages.get("knight_head_"+sex)));
					break;
				case "Mage":
					preview.set(2, Arrays.asList(
							preLoadedImages.get("mage_chest_"+sex),
							preLoadedImages.get("mage_foot_"+sex),
							preLoadedImages.get("mage_head_"+sex)));
					break;
				}
				break;
			}
			
		}
		
	}
}
