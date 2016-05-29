package gameplay;

abstract class CharacterClass{
	protected int health;
	protected int mana;
	protected int energy;
	protected int damage;
	protected int throwDamage;
	protected int magicDamage;
	protected int armor;
	protected int magicResist;
	protected int dodge;
	protected int accuracy;
	protected int initiative;
	protected int healthRegen;
	protected int energyRegen;
	protected int manaRegen;
	protected String name;
	protected int percentFront; //percent chance to be in the front row
	protected String description;
	
	String getInfo(){
		String output = description;
		//TODO return starting stat values
		return output;
	}
	int getHealth(){ return health; }
	int getMana(){ return mana; }
	int getEnergy(){ return energy; }
	int getDamage(){ return damage; }
	int getThrowDamage(){ return throwDamage; }
	int getMagicDamage(){ return magicDamage; }
	int getArmor(){ return armor; }
	int getMagicResist(){ return magicResist; }
	int getDodge(){ return dodge; }
	int getAccuracy(){ return accuracy; }
	int getinitiative(){ return initiative; }
	int getHealthRegen(){ return healthRegen; }
	int getEnergyRegen(){ return energyRegen; }
	int getManaRegen(){ return manaRegen; }
	String getName() { return name; }
	int getPercentFront(){ return percentFront; }
	
	abstract int levelUpHealthRegen(int healthRegen, int level);
	abstract int levelUpManaRegen(int manaRegen, int level);
	abstract int levelUpEnergyRegen(int energyRegen, int level);
	abstract int levelUpHealth(int health, int level);
	abstract int levelUpMana(int mana, int level);
	abstract int levelUpEnergy(int energy, int level);
	abstract int levelUpDamage(int damage, int level);
	abstract int levelUpMagicDamage(int magicdamage, int level);
	abstract int levelUpArmor(int armor, int level);
	abstract int levelUpMR(int magicResist, int level);
	abstract int levelUpDodge(int dodge, int level);
	abstract int levelUpAccuracy(int accuracy, int level);
	abstract int levelUpInitiative(int initiative, int level);
}