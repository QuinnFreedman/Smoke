package gameplay;

import java.awt.image.BufferedImage;

import engine.AnimationSet;

public class Item {
	protected AnimationSet femaleAnimationSet;
	protected AnimationSet maleAnimationSet;
	private BufferedImage icon;

	public AnimationSet getFemaleAnimationSet() {
		return femaleAnimationSet;
	}

	public AnimationSet getMaleAnimationSet() {
		return maleAnimationSet;
	}

	public BufferedImage getIcon() {
		return icon;
	}
	
	//assassin
	public static class AssassinChest extends Item {
		public AssassinChest() {
			this.femaleAnimationSet = new AnimationSet("character_tiles/armor_assassin/assassin_chest_female");
			this.maleAnimationSet = new AnimationSet("character_tiles/armor_assassin/assassin_chest_male");
		}
	}
	
	public static class AssassinHead extends Item {
		public AssassinHead() {
			this.femaleAnimationSet = new AnimationSet("character_tiles/armor_assassin/assassin_head_female");
			this.maleAnimationSet = new AnimationSet("character_tiles/armor_assassin/assassin_head_male");
		}
	}
	
	public static class AssassinFeet extends Item {
		public AssassinFeet() {
			this.femaleAnimationSet = new AnimationSet("character_tiles/armor_assassin/assassin_feet_female");
			this.maleAnimationSet = new AnimationSet("character_tiles/armor_assassin/assassin_feet_male");
		}
	}
	
	//knight
	public static class KnightChest extends Item {
		public KnightChest() {
			this.femaleAnimationSet = new AnimationSet("character_tiles/armor_knight/knight_chest_female");
			this.maleAnimationSet = new AnimationSet("character_tiles/armor_knight/knight_chest_male");
		}
	}
	
	public static class KnightHead extends Item {
		public KnightHead() {
			this.femaleAnimationSet = new AnimationSet("character_tiles/armor_knight/knight_head_female");
			this.maleAnimationSet = new AnimationSet("character_tiles/armor_knight/knight_head_male");
		}
	}
	
	public static class KnightFeet extends Item {
		public KnightFeet() {
			this.femaleAnimationSet = new AnimationSet("character_tiles/armor_knight/knight_feet_female");
			this.maleAnimationSet = new AnimationSet("character_tiles/armor_knight/knight_feet_male");
		}
	}
	
	//mage
	public static class MageChest extends Item {
		public MageChest() {
			this.femaleAnimationSet = new AnimationSet("character_tiles/armor_mage/mage_chest_female");
			this.maleAnimationSet = new AnimationSet("character_tiles/armor_mage/mage_chest_male");
		}
	}
	
	public static class MageHead extends Item {
		public MageHead() {
			this.femaleAnimationSet = new AnimationSet("character_tiles/armor_mage/mage_head_female");
			this.maleAnimationSet = new AnimationSet("character_tiles/armor_mage/mage_head_male");
		}
	}
	
	public static class MageFeet extends Item {
		public MageFeet() {
			this.femaleAnimationSet = new AnimationSet("character_tiles/armor_mage/mage_feet_female");
			this.maleAnimationSet = new AnimationSet("character_tiles/armor_mage/mage_feet_male");
		}
	}
}