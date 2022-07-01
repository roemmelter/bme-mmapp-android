package org.mmapp.assignments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import org.mmapp.R;
import org.mmapp.util.MathUtil;
import org.mmapp.util.ScreenUtility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MIT License (http://choosealicense.com/licenses/mit/)
 * <p><br>
 * <b>SnowflakesView</b><br>
 * </p><br>
 *
 * @author Erik Roemmelt
 */
public class SnowflakeView extends SurfaceView implements SurfaceHolder.Callback {
    private final Point                    SCREEN_SIZE;
    private final SnowflakeGeneratorThread SNOWFLAKE_GENERATOR_THREAD;
    private final Map<Thread, Snowflake>   ALL_SNOWFLAKES;
    private final Context CONTEXT;
    private final int                      NR_OF_SNOWFLAKES = 100;
    private final int                      SNOWFLAKE_SIZE   = 100;
    private       SurfaceHolder            surfaceHolder    = null;
    private       boolean                  isRunning        = true;

    public SnowflakeView(Context context) {
        super(context);
        CONTEXT = context;
        ALL_SNOWFLAKES             = new HashMap<Thread, Snowflake>();
        SNOWFLAKE_GENERATOR_THREAD = new SnowflakeGeneratorThread(this);
        ScreenUtility screenUtil = new ScreenUtility(CONTEXT);
        SCREEN_SIZE = screenUtil.getRealScreenSize();

        if (surfaceHolder == null) {
            surfaceHolder = getHolder();
            surfaceHolder.addCallback(this);
        }
        surfaceHolder.setFormat(PixelFormat.TRANSLUCENT);

        initSnowflakes(CONTEXT);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        SNOWFLAKE_GENERATOR_THREAD.setRunning(true);
        SNOWFLAKE_GENERATOR_THREAD.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        boolean retry = true;
        SNOWFLAKE_GENERATOR_THREAD.setRunning(false);
        while (retry) {
            try {
                SNOWFLAKE_GENERATOR_THREAD.join();
                retry = false;
            } catch (InterruptedException ex) {
                Log.e("org.mmapp.SnowflakeView", "SurfaceView.SnowflakeView destroyed: " +
                                                 "SnowflakeGeneratorThread.join() caused " +
                                                 "InterruptedException.");
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Bitmap bitmap = Bitmap.createBitmap(SCREEN_SIZE.x, SCREEN_SIZE.y, Bitmap.Config.ARGB_8888);
        drawObjects(canvas, bitmap);
        bitmap.recycle();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        synchronized (getHolder()) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    isRunning = false;
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_POINTER_UP:
                    isRunning = true;
                    break;
            }
        }
        return super.onTouchEvent(event);
    }

    private void initSnowflakes(Context context) {
        for (int i = 0; i <= NR_OF_SNOWFLAKES; i++) {
                createSnowflake(context);
        }
    }

    private void createSnowflake(Context context) {
        int       snowflakeSize = MathUtil.nextRandomInt(SNOWFLAKE_SIZE / 4, SNOWFLAKE_SIZE);
        int       startPosX     = MathUtil.nextRandomInt(0, (SCREEN_SIZE.x - SNOWFLAKE_SIZE / 2));
        Snowflake snowflake     = new Snowflake(context, snowflakeSize, startPosX);

        Thread thread = new Thread(snowflake);
        thread.start();
        ALL_SNOWFLAKES.put(thread, snowflake);
    }

    private void drawObjects(Canvas canvas, Bitmap bitmap) {
        // Draw canvas background
        Paint surfaceBackground = new Paint();
        surfaceBackground.setColor(getResources().getColor(R.color.MidnightBlue));
        canvas.drawRect(0, 0, SCREEN_SIZE.x, SCREEN_SIZE.y, surfaceBackground);

        // Draw game objects on canvas
        for (Thread thread : ALL_SNOWFLAKES.keySet()) {
            Snowflake snowflake = ALL_SNOWFLAKES.get(thread);
            if (snowflake != null) {
                snowflake.draw(canvas);
            }
        }

        updateAllThreads(canvas);

        // Draw canvas to bitmap
        canvas.drawBitmap(bitmap, 0, 0, null);
    }

    private void updateAllThreads(Canvas canvas) {
        List<Thread> toBeRemoved = new ArrayList<Thread>();
        for (Thread th : ALL_SNOWFLAKES.keySet()) {
            if (!th.isAlive()) {
                // remove snowflake from UI by removing it from ALL_SNOWFLAKES
                toBeRemoved.add(th);
                Log.d("M_", "Thread & snowflake "+ALL_SNOWFLAKES.get(th).toString()+" removed.");
            } else {
                ALL_SNOWFLAKES.get(th).draw(canvas);
            }
        }
        for (Thread th : toBeRemoved) {
            ALL_SNOWFLAKES.remove(th);
            createSnowflake(CONTEXT);
        }
    }


    class SnowflakeGeneratorThread extends Thread {
        private final long          DELAY     = 75;
        private final SnowflakeView view;
        private       boolean       isRunning = false;

        public SnowflakeGeneratorThread(SnowflakeView view) {
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
}
