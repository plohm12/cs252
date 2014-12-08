package com.cs252.invisiblemaze;

import java.util.ArrayList;
import java.util.Random;

import android.view.View;

public class Gameboard {
	private static final int UP = 0;
	private static final int DOWN = 1;
	private static final int RIGHT = 2;
	private static final int LEFT = 3;
	private static final int PATHSIZE = 16; //CHANGE IN FUTURE
	private static final int GRIDSIZE = 6;
	private Space start;
	private Space finish;
	private Space player;
	private ArrayList<Space> spaces;

	public Gameboard(){
		start = new Space(0, 0);
		makePath();
	}
	
	/**
	 * Create a new random maze path
	 */
	public void makePath(){
		System.out.println("Trying to make a new path...");
		spaces = new ArrayList<Space>();
		Space current = start;
		player = start;
		int count = 0;
		while(count < PATHSIZE){
			spaces.add(current);
			int x = current.getX();
			int y = current.getY();
			System.out.println("At ("+x+", "+y+")");
			boolean valid[] = {false, false, false, false}; //key=direction, value=valid
			int numChoices = 0;
			//Set UP, DOWN, LEFT, RIGHT to true if each is a valid space
			if( isValidSpace(x, y+1) && !spaces.contains(new Space(x, y+1))){
				valid[UP] = true;
				numChoices++;
			}
			if( isValidSpace(x, y-1) && !spaces.contains(new Space(x, y-1))){
				valid[DOWN] = true;
				numChoices++;
			}
			if( isValidSpace(x+1, y) && !spaces.contains(new Space(x+1, y))){
				valid[RIGHT] = true;
				numChoices++;
			}
			if( isValidSpace(x-1, y) && !spaces.contains(new Space(x-1, y))){
				valid[LEFT] = true;
				numChoices++;
			}
			
			
			if(numChoices < 1){
				// Dead end. Retry.
				makePath();
				return;
			}
			
			int[] spaceChoices = new int[numChoices]; //key=negligible, value=direction
			int n = 0;
			for(int direction = 0; direction < 4; direction++){
				if(valid[direction]){
					spaceChoices[n] = direction;
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
		}
		System.out.println("Finished at ("+finish.getX()+", "+finish.getY()+")");
		
	}
	
	public boolean isValidSpace(int x, int y){
		if(x < 0) return false;
		if(y < 0) return false;
		if(x >= GRIDSIZE) return false;
		if(y >= GRIDSIZE) return false;
		return true;
	}
	
	/**
	 * Move the player
	 * @return true if the player has finished, false otherwise
	 */
	public boolean move(View view) {
		int x, y;
		switch(view.getId()) {
		case(R.id.right_button):
			x = player.getX();
			y = player.getY() + 1;
			break;
		case(R.id.left_button):
			x = player.getX();
			y = player.getY() - 1;
			break;
		case(R.id.down_button):
			x = player.getX() + 1;
			y = player.getY();
			break;
		case(R.id.up_button):
			x = player.getX() - 1;
			y = player.getY();
			break;
		default:
			x = -1;
			y = -1;
			break;
		}
		Space current = start;
		while(!current.equals(player)){
			current = current.getNext();
		}
		Space temp = new Space(x, y);
		if( current.getNext().equals(temp))
			player = temp;
		else
			player = start;
		
		if(player.equals(finish)){
			//PLAYER HAS WON!!
			return true;
		}
		return false;
	}
	
	public Space getFinish(){
		return finish;
	}
	
	public Space getPlayer(){
		return player;
	}
}

