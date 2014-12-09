package com.cs252.invisiblemaze;

public class Player {
	private int currentX;
	private int currentY;
	private int totalSteps;
	private boolean violation;
	
	public Player() {
		this.currentX = 0;
		this.currentY = 0;
		this.totalSteps = 0;
		this.violation = false;			//This is for available step
	}
	
	public int getCurrentX() {
		return currentX;
	}
	
	public int getCurrentY() {
		return currentY;
	}
	
	public int getTotalSteps() {
		return totalSteps;
	}
	
	public boolean getViolation() {
		return violation;
	}
	
	public void setViolation(boolean v) {
		this.violation = v;
	}
	public void setCurrentX(int n) {
		this.currentX = n;
	}
	
	public void setCurrentY(int n) {
		this.currentY = n;
	}
	
	public void setTotalSteps(int n) {
		this.totalSteps = n;
	}
	
	
	
}
