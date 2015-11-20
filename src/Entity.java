import java.awt.Dimension;
import java.awt.image.BufferedImage;

class Entity{
	protected Dimension size;
	protected Point position;

	public Point getPosition() {
		return position;
	}
	public void setPosition(Point position) {
		this.position = position;
	}
	public Dimension getSize() {
		return size;
	}
	public void setSize(Dimension size) {
		this.size = size;
	}
	public BufferedImage getSprite(int t){
		return null;
	}
	
}