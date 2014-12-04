package com.cs252.invisiblemaze;

public class Space {
	private int X, Y;
	private Space next;
	private boolean inPath;
	
	public Space(int X, int Y){
		this.X = X;
		this.Y = Y;
		next = null;
		inPath = false;
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
	
	public void setInPath(boolean inPath){
		this.inPath = inPath;
	}
	
	public boolean isInPath(){
		return inPath;
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
