public class NPCCharacter extends Character{
	private Object target = null;
	private int disposition;
	private int alertness;
	
	NPCCharacter(Point position, Race race, String cclass) {
		super(position, race, cclass);
	}
	
	@Override
	void doLogic() {
		
	}
}