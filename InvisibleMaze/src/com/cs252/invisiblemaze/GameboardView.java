package com.cs252.invisiblemaze;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameboardView extends SurfaceView implements SurfaceHolder.Callback {

	GameboardViewThread _thread;
	public Rect[][] _squares;
	int GRID_WIDTH;  // Amount of columns
	int GRID_HEIGHT; // Amount of rows
	Gameboard gb;
	public GameboardView(Context context, int gameSize) { 
		super(context);
		getHolder().addCallback(this);
		this.setBackgroundColor(Color.WHITE);
		this.GRID_HEIGHT = gameSize;
		this.GRID_WIDTH = gameSize;
			gb = new Gameboard();
	        _squares = new Rect[GRID_HEIGHT][GRID_WIDTH];

	}
	
	@Override
    public void onDraw(Canvas canvas)
    {
		super.onDraw(canvas);
		
		System.out.println("IN ONDRAW");
		
        Paint paint = new Paint();
        paint.setColor(Color.LTGRAY);
        
        int GRID_SIZE = ((canvas.getWidth() - ((GRID_WIDTH - 1) * 5)) / GRID_WIDTH) + 1;  // Width and height of a cell
        
		
		for(int i = 0; i < GRID_WIDTH; i++) {
		    for(int j = 0; j < GRID_HEIGHT; j++) {
		    	int left = i * (GRID_SIZE + 5);
		    	int top = j * (GRID_SIZE + 5);
		    	int right = left + GRID_SIZE;
		    	int bottom = top + GRID_SIZE;
		        Rect rect = new Rect(left, top, right, bottom);
		        _squares[j][i] = rect;
		        canvas.drawRect(rect, paint);
		    }
		   
		}
		Paint finishP = new Paint();
		finishP.setColor(Color.YELLOW);
		System.out.println("finish:"+ gb.finish.getX()+gb.finish.getY());
		canvas.drawRect(_squares[gb.finish.getX()][gb.finish.getY()],finishP);
		
    }

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		setWillNotDraw(false); //Allows us to use invalidate() to call onDraw()


	     _thread = new GameboardViewThread(getHolder(), this); //Start the thread that
	     _thread.setRunning(true);                     //will make calls to 
	     _thread.start();                              //onDraw()
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
	     try {
	            _thread.setRunning(false);                //Tells thread to stop
	            _thread.join();                           //Removes thread from mem.
	     } catch (InterruptedException e) {}	
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	    this.setMeasuredDimension(widthMeasureSpec, widthMeasureSpec);
	}
}
