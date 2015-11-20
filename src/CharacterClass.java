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
	
	public String getInfo(){
		String output = description;
		//TODO return starting stat values
		return output;
	}
	public int getHealth(){ return health; }
	public int getMana(){ return mana; }
	public int getEnergy(){ return energy; }
	public int getDamage(){ return damage; }
	public int getThrowDamage(){ return throwDamage; }
	public int getMagicDamage(){ return magicDamage; }
	public int getArmor(){ return armor; }
	public int getMagicResist(){ return magicResist; }
	public int getDodge(){ return dodge; }
	public int getAccuracy(){ return accuracy; }
	public int getinitiative(){ return initiative; }
	public int getHealthRegen(){ return healthRegen; }
	public int getEnergyRegen(){ return energyRegen; }
	public int getManaRegen(){ return manaRegen; }
	public String getName() { return name; }
	public int getPercentFront(){ return percentFront; }
	
	public abstract int levelUpHealthRegen(int healthRegen, int level);
	public abstract int levelUpManaRegen(int manaRegen, int level);
	public abstract int levelUpEnergyRegen(int energyRegen, int level);
	public abstract int levelUpHealth(int health, int level);
	public abstract int levelUpMana(int mana, int level);
	public abstract int levelUpEnergy(int energy, int level);
	public abstract int levelUpDamage(int damage, int level);
	public abstract int levelUpMagicDamage(int magicdamage, int level);
	public abstract int levelUpArmor(int armor, int level);
	public abstract int levelUpMR(int magicResist, int level);
	public abstract int levelUpDodge(int dodge, int level);
	public abstract int levelUpAccuracy(int accuracy, int level);
	public abstract int levelUpInitiative(int initiative, int level);
}