package com.cs252.invisiblemaze;

public class Space {
	private Space next;
	private boolean inPath;
	
	public Space(){
		next = null;
		inPath = false;
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
}
