package com.cs252.invisiblemaze;

public class Space {
	private int X, Y;
	private Space next;
	
	public Space(int X, int Y){
		this.X = X;
		this.Y = Y;
		next = null;
	}
	
	public int getX(){
		return X;
	}
	
	public int getY(){
		return Y;
	}
	
	public void setNext(Space next){
		this.next = next;
	}
	
	public Space getNext(){
		return next;
	}
	
	public boolean equals(Object o){
		if(!(o instanceof Space))
			return false;
		if(this == o)
			return true;
		Space s = (Space)o;
		if(this.X != s.getX()) return false;
		if(this.Y != s.getY()) return false;
		return true;
	}
}
