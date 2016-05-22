package menu;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import menu.Menu.MenuItem;

public class StartMenu {
	private static Menu menu;
	StartMenu() {
		String CHOOSE_YOUR_CLASS = "Choose Your Class";
		MenuItem[] chooseClass = new MenuItem[] {
				new MenuItem("Assassin"),
				new MenuItem("Warrior"),
				new MenuItem("Mage")
			};
		String CHOOSE_YOUR_RACE = "Choose Your Race";
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
		// TODO Auto-generated method stub
		
	}

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
				menu.select();
				break;
			case KeyEvent.VK_ESCAPE:
				menu.moveBack();
				break;
			default:
				break;
			}
		}
		
	}
}
