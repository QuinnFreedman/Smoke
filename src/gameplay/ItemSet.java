package gameplay;

import java.util.HashMap;

class ItemSet {
	static final int HEAD = 0;
	static final int CHEST = 1;
	static final int FEET = 2;
	static final int BACK = 3;
	static final int HANDS = 4;
	static final int NECKLACE = 5;
	static final int RING_1 = 6;
	static final int RING_2 = 7;
	
	private HashMap<Integer, Item> items = new HashMap<>();
	
	Item get(int slot) {
		return items.get(slot);
	}
	
	void put(int slot, Item item) {
		items.put(slot, item);
	}
	
}