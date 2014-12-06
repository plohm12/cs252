package com.cs252.invisiblemaze;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameboardViewThread extends Thread {
    private SurfaceHolder _surfaceHolder;
    private GameboardView _view;
    private boolean _run = false;

    public GameboardViewThread(SurfaceHolder surfaceHolder, GameboardView view) {
        _surfaceHolder = surfaceHolder;
        _view = view;
    }

    public void setRunning(boolean run) { //Allow us to stop the thread
        _run = run;
    }

    @Override
    public void run() {
        Canvas c;
        while (_run) {     //When setRunning(false) occurs, _run is 
            c = null;      //set to false and loop ends, stopping thread
            
            try {
                c = _surfaceHolder.lockCanvas(null);
                synchronized (_surfaceHolder) {
                	//Insert methods to modify positions of items in onDraw()
                	//postInvalidate();
                }
            } finally {
                if (c != null) {
                    _surfaceHolder.unlockCanvasAndPost(c);
                }
            }
        }
    }
}
