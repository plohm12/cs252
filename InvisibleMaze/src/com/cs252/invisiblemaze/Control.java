package com.cs252.invisiblemaze;

import java.util.*;
import java.lang.*;
import android.view.KeyEvent;

public class Control {
/*	
	enum Arrow {
		LEFT, RIGHT, UP, DOWN
	}
*/
	public boolean isValidMove(Player p, KeyEvent e) {
		switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
			if(p.getCurrentX() - 1 >= 0) {
				return true;
			}
			else {
				System.out.println("Out of bound. Please try another direction");
				return false;
			}
			break;
			
			case KeyEvent.VK_RIGHT:
			if(p.getCurrentX() + 1 < 6) {
				return true;
			}
			else {
				System.out.println("Out of bound. Please try another direction");
				return false;
			}
			break;
			
			case KeyEvent.VK_UP:
			if(p.getCurrentY() - 1 >= 0) {
				return true;
			}
			else {
				System.out.println("Out of bound. Please try another direction");				
				return false;
			}
			break;
			
			case KeyEvent.VK_DOWN:
			if(p.getCurrentY() + 1 < 6) {
				return true;
			}
			else {
				System.out.println("Out of bound. Please try another direction");				
				return false;
			}
			break;
		}
		
		
	}
	public void ButtonPressed(Player p, KeyEvent e) {
		//TODO :need to change this keyboard to touch screen
		switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				System.out.println("Move left");
				//TODO:update player to the map
				p.setTotalSteps(p.getCurrentX() - 1);
				p.setTotalSteps(p.getTotalSteps() + 1);
				break;
		
			case KeyEvent.VK_RIGHT:
				System.out.println("Move right");
				//TODO:update player to the map			
				p.setTotalSteps(p.getCurrentX() + 1);
				p.setTotalSteps(p.getTotalSteps() + 1);				
				break;
			
			case KeyEvent.VK_UP:
				System.out.println("Move up");
				//TODO:update player to the map
				p.setTotalSteps(p.getCurrentY() - 1);				
				p.setTotalSteps(p.getTotalSteps() + 1);
				break;
				
			case KeyEvent.VK_DOWN:
				System.out.println("Move down");
				//TODO:update player to the map	
				p.setTotalSteps(p.getCurrentY() + 1);
				p.setTotalSteps(p.getTotalSteps() + 1);
				break;			
		}
	}
}
