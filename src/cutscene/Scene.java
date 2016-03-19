package cutscene;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;

class Scene {
	private ArrayList<Animation> animations = new ArrayList<Animation>();
	private ArrayList<PostProcess> effects = new ArrayList<PostProcess>();
	private int duration;
	int getDuration() {
		return duration;
	}
	
	Scene(int durration) {
		this.duration = durration;
	}
	
	void draw(Graphics2D g, int t) {
		{
			Iterator<Animation> itr = animations.iterator();
			while(itr.hasNext()) {
				Animation anim = itr.next();
				anim.draw(g, t);
				if(anim.hasExpired(t)) {
					itr.remove();
				}
			}
		}
		
		{
			Iterator<PostProcess> postItr = effects.iterator();
			while(postItr.hasNext()) {
				PostProcess effect = postItr.next();
				if(effect.render(g, t)){
					postItr.remove();
				}
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
	
	void addPostProcess(PostProcess effect) {
		effects.add(effect);
	}
	
	void addAnimation(Animation animation, int time) {
		animation.setStartTime(time);
		addAnimation(animation);
	}
}