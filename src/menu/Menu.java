package menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Menu {
	private MenuItem rootItem;
	private int selectedIndex;
	private MenuItem view;
	
	public Menu(String heading, MenuItem... items) {
		if(items.length == 0) {
			throw new IllegalArgumentException("A menu must contain at least one MenuItem");
		}
		rootItem = new MenuItem("root", heading, items);
		view = rootItem;
		selectedIndex = 0;
		rootItem.children.get(0).selected = true;
	}
	
	public void moveDown() {
		if(selectedIndex + 1 < view.children.size()) {
			view.children.get(selectedIndex).selected = false;
			selectedIndex++;
			view.children.get(selectedIndex).selected = true;
		}
	}
	
	public void moveUp() {
		if(selectedIndex > 0) {
			view.children.get(selectedIndex).selected = false;
			selectedIndex--;
			view.children.get(selectedIndex).selected = true;
		}
	}
	
	public Selection select() {
		String value = view.children.get(selectedIndex).getText();
		if(view.children.get(selectedIndex).children != null) {
			view.children.get(selectedIndex).selected = false;
			view = view.children.get(selectedIndex);
			selectedIndex = 0;
			view.children.get(0).selected = true;
			return new Selection(value, false);
		} else {
			return new Selection(value, true);
		}
	}
	
	public void moveBack() {
		view.children.get(selectedIndex).selected = false;
		selectedIndex = 0;
		
		MenuItem selected = view;
		view = view.parent;
		selected.selected = true;
		selectedIndex = view.children.indexOf(selected);
		assert selectedIndex != -1;
	}
	
	public String getHeading() {
		return view.heading;
	}
	
	public List<MenuItem> getItems() {
		return Collections.unmodifiableList(view.children);
	}
	
	public String getSelected() {
		return view.children.get(selectedIndex).text;
	}
	
	public static class MenuItem {
		private String text;
		private boolean selected = false;
		private String heading = "";
		private ArrayList<MenuItem> children = null;
		private MenuItem parent = null;

		public boolean isSelected() {
			return selected;
		}

		public String getText() {
			return text;
		}
		
		public MenuItem(String text) {
			this.text = text;
		}
		
		public MenuItem(String text, String heading, MenuItem... items) {
			this(text);
			this.heading = heading;
			this.children = new ArrayList<MenuItem>(Arrays.asList(items));
			for(MenuItem child : children) {
				child.parent = this;
			}
		}
		
	}
	
	public static class Selection {
		private String value;
		private boolean finalResult;

		public boolean isFinalResult() {
			return finalResult;
		}

		public String getValue() {
			return value;
		}
		
		private Selection(String value, boolean isLast) {
			this.value = value;
			this.finalResult = isLast;
		}
		
	}
}
