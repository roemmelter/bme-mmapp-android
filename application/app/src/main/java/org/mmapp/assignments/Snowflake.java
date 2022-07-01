package org.mmapp.assignments;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import org.mmapp.util.MathUtil;
import org.mmapp.util.ScreenUtility;

/**
 * MIT License (http://choosealicense.com/licenses/mit/)
 * <p><br>
 * <b>Snowflake</b><br>
 * Draws a koch snow flake as a path on a canvas. It can be animated.
 * </p><br>
 *
 * @author Erik Roemmelt
 */
public class Snowflake implements Runnable {
    private static final int   VELOCITY_Y = 3;
    private static final        int   MAX_SIZE = 100;
    private final        Point SCREEN_SIZE;
    private final        Paint PAINT;
    private final        Path  PATH;
    private final        int   DELAY_MOVE;
    private final        int   START_POS_Y;
    private final        int   NR_OF_ITERATIONS;

    public Snowflake(Context context, int size, int startPosX) {
        ScreenUtility screenUtil = new ScreenUtility(context);
        SCREEN_SIZE      = screenUtil.getRealScreenSize();
        DELAY_MOVE       = MathUtil.nextRandomInt(40, 150);
        START_POS_Y      = calculateStartPosY(size);
        PAINT            = initPaint();
        NR_OF_ITERATIONS = 5;
        PATH             = initSnowflake(size, startPosX, -START_POS_Y);
    }

    @Override
    public void run() {
        // animate the snowflake across the screen
        for (int i = 0; i < (SCREEN_SIZE.y + START_POS_Y - MAX_SIZE) / VELOCITY_Y; i++) {
            try {
                Thread.sleep(DELAY_MOVE);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            int vx = MathUtil.nextRandomInt(-2, 2);
            moveBy(vx, VELOCITY_Y);
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawPath(PATH, PAINT);
    }

    private Paint initPaint() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(0);
        return paint;
    }

    private Path initSnowflake(int size, int startPosX, int startPosY) {
        Path path = new Path();
        path.reset();
        path.moveTo(startPosX, startPosY);
        createKochSnowflake(path, startPosX, startPosY, size, NR_OF_ITERATIONS);
        path.close();
        path.setFillType(Path.FillType.WINDING);
        return path;
    }

    private void createKochSnowflake(Path path, int x, int y, int length, int nrOfIterations) {
        drawKochLine(path, x, y, length, 0, nrOfIterations);
        drawKochLine(path, x + length, y, length, -120, nrOfIterations);
        double x1 = x + length * Math.cos(-60 * Math.PI / 180);
        double y1 = y - length * Math.sin(-60 * Math.PI / 180);
        drawKochLine(path, x1, y1, length, 120, nrOfIterations);
    }

    private void drawKochLine(Path path, double x0, double y0, double length, double angle,
                              double nrOfIterations) {
        // base case:
        if (nrOfIterations == 0) {
            double x1 = x0 + length * Math.cos(angle * Math.PI / 180);
            double y1 = y0 - length * Math.sin(angle * Math.PI / 180);
            path.lineTo((float) x1, (float) y1);
        } else {
            // recursive case:
            double len = length / 3;
            double ang = angle;
            drawKochLine(path, x0, y0, len, ang + 0, nrOfIterations - 1);
            double x1 = x0 + len * Math.cos(ang * Math.PI / 180);
            double y1 = y0 - len * Math.sin(ang * Math.PI / 180);
            drawKochLine(path, x1, y1, len, ang + 60, nrOfIterations - 1);
            ang = ang + 60;
            double x2 = x1 + len * Math.cos(ang * Math.PI / 180);
            double y2 = y1 - len * Math.sin(ang * Math.PI / 180);
            drawKochLine(path, x2, y2, len, ang - 120, nrOfIterations - 1);
            ang = ang - 120;
            double x3 = x2 + len * Math.cos(ang * Math.PI / 180);
            double y3 = y2 - len * Math.sin(ang * Math.PI / 180);
            drawKochLine(path, x3, y3, len, ang + 60, nrOfIterations - 1);
        }
    }

    private int calculateStartPosY(int size) {
        // calculate height of an equilateral triangle with a side length of size
        int heightSnowflake = (int) Math.ceil(Math.sqrt((size * size) * 3 / 4f));
        int startPosY       = MathUtil.nextRandomInt(heightSnowflake, SCREEN_SIZE.y);
        return startPosY;
    }

    private void moveBy(float vx, float vy) {
        PATH.offset(vx, vy);
    }
}
