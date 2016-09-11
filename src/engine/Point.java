package engine;

public class Point{
	public int x;
	public int y;
	@Override
	public String toString(){
		return "{"+x+","+y+"}";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
	public Point scale(float scalar){
		return new Point(Math.round(this.x * scalar), Math.round(this.y * scalar));
	}

	public Point scale(int scalar){
		return new Point(this.x * scalar, this.y * scalar);
	}
	
	public Point translate(int x, int y) {
		return new Point(this.x + x, this.y + y);
	}

	public Point translate(Direction direction, int scalar) {
		switch (direction) {
			case NORTH:
				return translate(0, -scalar);
			case SOUTH:
				return translate(0, scalar);
			case EAST:
				return translate(scalar, 0);
			case WEST:
				return translate(-scalar, 0);
			default:
				return copy();
		}
	}
	
	public Point add(Point p) {
		return new Point(this.x + p.x, this.y + p.y);
	}
	
	public Point subtract(Point p) {
		return new Point(this.x - p.x, this.y - p.y);
	}

	public Point(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public Point(float x, float y) {
		this.x = Math.round(x);
		this.y = Math.round(y);
	}
	
	public Point(){
		
	}
	public Point(Point p) {
		this.x = p.x;
		this.y = p.y;
	}
	public Point copy() {
		return new Point(this);
	}
	
	public static class Float {
		public float x;
		public float y;
		@Override
		public String toString(){
			return "{"+x+","+y+"}";
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Point other = (Point) obj;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}

		Float(float x, float y){
			this.x = x;
			this.y = y;
		}
		
		Float(){
			
		}
		Float(Point p) {
			this.x = p.x;
			this.y = p.y;
		}
		
		Float(Point.Float p) {
			this.x = p.x;
			this.y = p.y;
		}
	}
}