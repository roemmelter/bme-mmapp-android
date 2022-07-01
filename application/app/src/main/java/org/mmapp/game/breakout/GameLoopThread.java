package org.mmapp.game.breakout;

import android.graphics.Canvas;

/**
 * MIT License (http://choosealicense.com/licenses/mit/)
 * <p><br>
 * <b>GameLoopThread</b><br>
 * Description
 * </p><br>
 *
 * @author Erik Roemmelt
 */
public class GameLoopThread extends Thread {
    private final long     DELAY       = 75;
    private final GameView view;
    private       boolean  isRunning = false;

    public GameLoopThread(GameView view) {
        this.view = view;
    }

    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    @Override
    public void run() {
        long startTime;
        long sleepTime;

        while (isRunning) {
            Canvas canvas = null;
            startTime = System.currentTimeMillis();
            try {
                canvas = view.getHolder().lockCanvas();
                synchronized (view.getHolder()) {
                    view.draw(canvas);
                }
            } finally {
                if (canvas != null) {
                    view.getHolder().unlockCanvasAndPost(canvas);
                }
            }
            sleepTime = DELAY - (System.currentTimeMillis() - startTime);
            try {
                if (sleepTime > 0) {
                    sleep(sleepTime);
                } else {
                    sleep(10);
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
