package org.mmapp.game.breakout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import org.mmapp.R;
import org.mmapp.game.DrawingObject;
import org.mmapp.util.MathUtil;
import org.mmapp.util.ScreenUtility;

import java.util.ArrayList;
import java.util.List;

/**
 * MIT License (http://choosealicense.com/licenses/mit/)
 * <p><br>
 * <b>GameView</b><br>
 * Description
 * </p><br>
 *
 * @author Erik Roemmelt
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private final List<DrawingObject> ALL_DRAWING_OBJECTS;
    private final List<DrawingObject> DRAWING_OBJECTS_TO_BE_REMOVED;
    private final Point               SCREEN_SIZE;
    private final GameLoopThread      GAME_LOOP_THREAD;
    private final Context             CONTEXT;
    private       boolean             hasStarted    = false;
    private       SurfaceHolder       surfaceHolder = null;
    private       Paddle              paddle;
    private       Ball                ball;

    public GameView(Context context) {
        super(context);
        CONTEXT                       = context;
        GAME_LOOP_THREAD              = new GameLoopThread(this);
        ALL_DRAWING_OBJECTS           = new ArrayList<>();
        DRAWING_OBJECTS_TO_BE_REMOVED = new ArrayList<>();
        ScreenUtility screenUtil = new ScreenUtility(CONTEXT);
        SCREEN_SIZE = screenUtil.getRealScreenSize();

        if (surfaceHolder == null) {
            surfaceHolder = getHolder();
            surfaceHolder.addCallback(this);
        }
        surfaceHolder.setFormat(PixelFormat.TRANSLUCENT);

        initBricks();
        initPaddle();
        initBall();
        ball.setPaddleForCollisionDetection(paddle);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        GAME_LOOP_THREAD.setRunning(true);
        GAME_LOOP_THREAD.start();
    }
    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }
    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        boolean retry = true;
        GAME_LOOP_THREAD.setRunning(false);
        while (retry) {
            try {
                GAME_LOOP_THREAD.join();
                retry = false;
            } catch (InterruptedException ex) {
                Log.e("org.mmapp.GameView", "SurfaceView.GameView destroyed: " +
                                            "GameLoopThread.join() caused InterruptedException.");
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Bitmap bitmap = Bitmap.createBitmap(SCREEN_SIZE.x, SCREEN_SIZE.y, Bitmap.Config.ARGB_8888);
        updatePhysics();
        drawObjects(canvas, bitmap);
        bitmap.recycle();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int pointerX = Math.round(event.getX());
        synchronized (getHolder()) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (!hasStarted && ball != null) {
                        ball.spawn();
                        hasStarted = true;
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    int paddleMidPos = paddle.getPosition().x + (paddle.getSize().x / 2);
                    int pointerPaddleDistanceX = pointerX - paddleMidPos;
                    float vxNormalized = MathUtil
                            .normalizeValue(pointerPaddleDistanceX, 0, SCREEN_SIZE.x / 2f);
                    paddle.setVelocityX(vxNormalized, 65);
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    paddle.setVelocity(new Point(0, 0));
                    break;
            }
        }
        return true;
    }

    private void updatePhysics() {
        for (int i = ALL_DRAWING_OBJECTS.size() - 1; i >= 0; i--) {
            DrawingObject drawingObject = ALL_DRAWING_OBJECTS.get(i);
            drawingObject.updatePhysics();
            detectCollisionBallWithBrick(drawingObject, ball, DRAWING_OBJECTS_TO_BE_REMOVED);
        }
        ALL_DRAWING_OBJECTS.removeAll(DRAWING_OBJECTS_TO_BE_REMOVED);
        DRAWING_OBJECTS_TO_BE_REMOVED.clear();
    }

    private void detectCollisionBallWithBrick(DrawingObject drawingObject, Ball ball,
                                              List<DrawingObject> objectsToBeRemoved) {
        if (drawingObject.isColliding(ball) && drawingObject instanceof Brick) {
            Point ballPos  = ball.getPosition();
            Point ballSize = ball.getSize();
            //TODO: Need to improve this
            if (drawingObject.isCollidingHorizontally((ballPos.x + ballSize.x), true) ||
                drawingObject.isCollidingHorizontally(ballPos.x, false)) {
                ball.invertVelocityX();
            }
            if (drawingObject.isCollidingVertically((ballPos.y + ballSize.y), true) ||
                drawingObject.isCollidingVertically(ballPos.y, false)) {
                ball.invertVelocityY();
            }
            objectsToBeRemoved.add(drawingObject);
        }
    }

    private void drawObjects(Canvas canvas, Bitmap bitmap) {
        // Draw canvas background
        Paint surfaceBackground = new Paint();
        surfaceBackground.setColor(Color.WHITE);
        canvas.drawRect(0, 0, SCREEN_SIZE.x, SCREEN_SIZE.y, surfaceBackground);

        // Draw game objects on canvas
        for (DrawingObject drawingObject : ALL_DRAWING_OBJECTS) {
            drawingObject.draw(canvas);
        }

        // Draw canvas to bitmap
        canvas.drawBitmap(bitmap, 0, 0, null);
    }

    private void initPaddle() {
        final int PADDLE_WIDTH = 250;

        paddle = new Paddle(CONTEXT, PADDLE_WIDTH);
        ALL_DRAWING_OBJECTS.add(paddle);
    }

    private void initBall() {
        final int BALL_RADIUS = 24;

        ball = new Ball(CONTEXT, BALL_RADIUS);
        ball.setVelocityFactor(40);
        ball.setRandomAngleBoundaries(30, 150);
        ALL_DRAWING_OBJECTS.add(ball);
    }

    private void initBricks() {
        final int BRICK_WIDTH    = 100;
        final int ROWS_OF_BRICKS = 6;
        final int BRICKS_IN_ROW  = (SCREEN_SIZE.x - BRICK_WIDTH) / BRICK_WIDTH;

        final int WIDTH_ALL_BRICKS  = BRICKS_IN_ROW * BRICK_WIDTH;
        final int HEIGHT_ALL_BRICKS = ROWS_OF_BRICKS * (BRICK_WIDTH / 2);
        final int OFFSET_X          = (SCREEN_SIZE.x - WIDTH_ALL_BRICKS) / 2;
        final int OFFSET_Y          = BRICK_WIDTH;
        int       color;

        // Offset bricks x= (screenWidth - allBricksWidth) /2, y= brickWidth
        for (int y = OFFSET_Y; y < HEIGHT_ALL_BRICKS + OFFSET_Y; y += (BRICK_WIDTH / 2)) {
            for (int x = OFFSET_X; x < WIDTH_ALL_BRICKS + OFFSET_X; x += BRICK_WIDTH) {
                if (y % (BRICK_WIDTH) == 0) {
                    color = Color.RED;
                } else {
                    color = getResources().getColor(R.color.DarkGreen);
                }
                Brick brick = new Brick(CONTEXT, x, y, (BRICK_WIDTH / 2), color);
                ALL_DRAWING_OBJECTS.add(brick);
            }
        }
    }
}
