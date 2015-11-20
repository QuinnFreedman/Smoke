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

	Point(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	Point(){
		
	}
	public Point(Point p) {
		this.x = p.x;
		this.y = p.y;
	}
}

class FloatPoint{
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

	FloatPoint(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	FloatPoint(){
		
	}
	public FloatPoint(Point p) {
		this.x = p.x;
		this.y = p.y;
	}
	
	public FloatPoint(FloatPoint p) {
		this.x = p.x;
		this.y = p.y;
	}
}