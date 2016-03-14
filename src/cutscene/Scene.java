package cutscene;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;

class Scene {
	private ArrayList<Animation> animations = new ArrayList<Animation>();
	private int duration;
	int getDuration() {
		return duration;
	}
	
	Scene(int durration) {
		this.duration = durration;
	}
	
	void draw(Graphics2D g, int t) {
		Iterator<Animation> itr = animations.iterator();
		while(itr.hasNext()) {
			Animation anim = itr.next();
			anim.draw(g, t);
			if(anim.hasExpired(t)) {
				itr.remove();
			}
		}
		
	}
	
	void load() {
		for(Animation anim : animations) {
			anim.load();
		}
	}
	
	void addAnimation(Animation animation) {
		animations.add(animation);
	}
	
	void addAnimation(Animation animation, int time) {
		animation.setStartTime(time);
		addAnimation(animation);
	}
}