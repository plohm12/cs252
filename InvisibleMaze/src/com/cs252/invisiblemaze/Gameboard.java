package com.cs252.invisiblemaze;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Gameboard {
	private static final int UP = 0;
	private static final int DOWN = 1;
	private static final int RIGHT = 2;
	private static final int LEFT = 3;
	private static final int PATHSIZE = 16; //CHANGE IN FUTURE
	Space start;
	Space finish;
	private int cX, cY; // current position X and Y values
	int gridSize;
	ArrayList<Space> spaces;
	
	public Gameboard(int n){
		start = new Space(0, 0);
		start.setInPath(true);
		gridSize = n;
	}
	
	/**
	 * Create a new random maze path
	 */
	public void makePath(){
		System.out.println("Trying to make a new path...");
		spaces = new ArrayList<Space>();
		Space current = start;
		int count = 0;
		while(count < PATHSIZE){
			spaces.add(current);
			int x = current.getX();
			int y = current.getY();
			System.out.println("At ("+x+", "+y+")");
			boolean valid[] = {false, false, false, false}; //key=direction, value=valid
			int numChoices = 0;
			//Set UP, DOWN, LEFT, RIGHT to true if each is a valid space
			if( isValidIndex(x, y+1) && !spaces.contains(new Space(x, y+1))){
				valid[UP] = true;
				numChoices++;
			}
			if( isValidIndex(x, y-1) && !spaces.contains(new Space(x, y-1))){
				valid[DOWN] = true;
				numChoices++;
			}
			if( isValidIndex(x+1, y) && !spaces.contains(new Space(x+1, y))){
				valid[RIGHT] = true;
				numChoices++;
			}
			if( isValidIndex(x-1, y) && !spaces.contains(new Space(x-1, y))){
				valid[LEFT] = true;
				numChoices++;
			}
			
			
			if(numChoices < 1){
				// Dead end. Retry.
				makePath();
			}
			
			int[] spaceChoices = new int[numChoices]; //key=N/A, value=direction
			int n = 0;
			for(int i = 0; i < 4; i++){
				if(valid[i]){
					spaceChoices[n] = i;
					n++;
				}
			}
			
			// Get a random space from spaceChoices
			Random random = new Random();
			int nextSpace = random.nextInt(numChoices);
			switch(spaceChoices[nextSpace]){
			case(UP):		current.setNext(new Space(x, y+1));
							break;
			case(DOWN):		current.setNext(new Space(x, y-1));
							break;
			case(RIGHT):	current.setNext(new Space(x+1, y));
							break;
			case(LEFT):		current.setNext(new Space(x-1, y));
							break;
			default:		break; // Default should be unnecessary
			}
			current = current.getNext();
			if(count == PATHSIZE-1){
				finish = current;
			}
			count++;
			//System.out.println("Spaces so far:");
			//System.out.println(spaces.toString());
			// END OF makePath()
					
			/*		
			int set = 1;
			int tries = 0;
			while(set != 0){
				Random random = new Random();
				int nextX = random.nextInt(2);
				if(nextX==0)
					nextX= -1;
				int nextY = random.nextInt(2);
				if(nextY == 0)
					nextY = -1;
				if( isValidIndex(nextX, nextY)){
					if( !spaces[x+nextX][y+nextY].isInPath()){
						spaces[x][y].setNext(spaces[x+nextX][y+nextY]);
						spaces[x+nextX][y+nextY].setInPath(true);
						x += nextX;
						y += nextY;
						if(count == 7){
							finish = spaces[x][y]; // This is the last space
						}
						count++;
						set = 0;
					}
				}
				tries++;
				if(tries > 3){
					makePath();
				}
			}*/
		}
		System.out.println("Finished at ("+finish.getX()+", "+finish.getY()+")");
	}
	
	public boolean isValidIndex(int x, int y){
		if(x < 0) return false;
		if(y < 0) return false;
		if(x >= gridSize) return false;
		if(y >= gridSize) return false;
		return true;
	}
	/*
	public void performMove(int xOff, int yOff){
		if(spaces[cX][cY].getNext() != spaces[cX+xOff][cY+yOff]){
			// Reset to start
			cX = 0;
			cY = 0;
		}else if(spaces[cX+xOff][cY+yOff] == finish){
			// You are finished!!
			
		}else{
			cX += xOff;
			cY += yOff;
			// Update board position on screen using coordinates
		}
	}*/
}

