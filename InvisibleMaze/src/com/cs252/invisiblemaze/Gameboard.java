package com.cs252.invisiblemaze;

import java.util.Random;

public class Gameboard {
	Space start;
	Space finish;
	Space spaces[][];
	int gridSize;
	
	public Gameboard(int n){
		spaces = new Space[n][n];
		start = spaces[0][0];
		start.setInPath(true);
		gridSize = n;
	}
	
	public void makePath(){
		int count = 0;
		int x = 0;
		int y = 0;
		while(count < 8){
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
						count++;
						set = 0;
					}
				}
				tries++;
				if(tries > 3){
					makePath();
				}
			}
		}
	}
	
	public boolean isValidIndex(int x, int y){
		if(x < 0) return false;
		if(y < 0) return false;
		if(x > gridSize) return false;
		if(y > gridSize) return false;
		return true;
	}
}

