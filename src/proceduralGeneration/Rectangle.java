package proceduralGeneration;

import engine.Point;

class Rectangle {
	int x;
	int y;
	int width;
	int height;
	
	Rectangle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	Rectangle(Point aBound, Point bBound) {
		this.x = Math.min(aBound.x, bBound.x);
		this.y = Math.min(aBound.y, bBound.y);
		this.width = Math.max(aBound.x, bBound.x) - this.x;
		this.height = Math.max(aBound.y, bBound.y) - this.y;
	}
	
	public Rectangle() {
		// TODO Auto-generated constructor stub
	}
}