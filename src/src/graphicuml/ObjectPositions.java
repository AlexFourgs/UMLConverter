package graphicuml;

import java.io.Serializable;

public class ObjectPositions implements Serializable {
	private int width ;
	private int height ;
	private int x ;
	private int y ;
	
	public ObjectPositions() {
		width = 0 ;
		height = 0 ;
		x = 0 ;
		y = 0 ;
	}
	
	public void setWidth (int width){
		this.width = width ;
	}
	
	public int getWidth() {
		return width ;
	}
	
	public void setHeight (int height){
		this.height = height ;
	}
	
	public int getHeight() {
		return height ;
	}
	
	public void setX (int posInitX) {
		this.x = posInitX ;
	}
	
	public int getX () {
		return x ;
	}
	
	public void setY (int posInitY) {
		this.y = posInitY ;
	}
	
	public int getY () {
		return y ;
	}
}
