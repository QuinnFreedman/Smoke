package cutscene;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;

class Scene {
	
	ArrayList<Animation> animations = new ArrayList<Animation>();
	
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
	
	void addAnimation(Animation animation) {
		animations.add(animation);
	}
}