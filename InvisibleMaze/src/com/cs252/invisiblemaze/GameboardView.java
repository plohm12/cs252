package com.cs252.invisiblemaze;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class GameboardView extends View {

	public GameboardView(Context context) {
		super(context);
		this.setBackgroundColor(Color.WHITE);
	}
	
	@Override
    protected void onDraw(Canvas canvas)
    {
		int GRID_WIDTH = 6;  // Amount of columns
		int GRID_HEIGHT = 7; // Amount of rows
		int GRID_SIZE = ((canvas.getWidth() - ((GRID_WIDTH - 1) * 5)) / GRID_WIDTH) + 1;  // Width and height of a cell
		
        Paint paint = new Paint();
        paint.setColor(Color.LTGRAY);
        
        Rect[][] squares = new Rect[GRID_HEIGHT - 1][GRID_WIDTH];
		
		for(int i = 0; i < GRID_WIDTH; i++) {
		    for(int j = 1; j < GRID_HEIGHT; j++) {
		    	int left = i * (GRID_SIZE + 5);
		    	int top = j * (GRID_SIZE + 5);
		    	int right = left + GRID_SIZE;
		    	int bottom = top + GRID_SIZE;
		        Rect rect = new Rect(left, top, right, bottom);
		        squares[j - 1][i] = rect;
		        canvas.drawRect(rect, paint);
		    }
		}
    }

	
}
