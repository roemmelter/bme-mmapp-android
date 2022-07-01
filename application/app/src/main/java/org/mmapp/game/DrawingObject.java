package org.mmapp.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import org.mmapp.util.ScreenUtility;

/**
 * MIT License (http://choosealicense.com/licenses/mit/)
 * <p><br>
 * <b>DrawingObject</b><br>
 * Description
 * </p><br>
 *
 * @author Erik Roemmelt
 */
public class DrawingObject {
    private final Point   SCREEN_SIZE;
    private       Context context;
    private       Paint   paint;
    private Point position;
    private Point size;
    private Point velocity;

    public DrawingObject(Context context, int x, int y, int width, int height, int color) {
        this.context = context;
        this.SCREEN_SIZE = new ScreenUtility(context).getRealScreenSize();
        this.position = new Point(x, y);
        this.size = new Point(width, height);
        this.velocity = new Point(0, 0);
        setupPaint(color);
    }
    public void updatePhysics() {
        position.x += velocity.x;
        position.y += velocity.y;
    }

    /**
     * defaults to call: drawRect(pos.x, pos.y, (pos.x + size.x), (pos.y +size.y), paint);
     */
    public void draw(Canvas canvas) {
        canvas.drawRect(position.x, position.y, position.x + size.x, position.y + size.y,
                        paint);
    }
    public boolean isColliding(DrawingObject collisionObject) {
        if (collisionObject != null) {
            Point objPos  = collisionObject.getPosition();
            Point objSize = collisionObject.getSize();
            return ((position.x + size.x) >= objPos.x) && (position.x <= (objPos.x + objSize.x))
                   && ((position.y + size.y) >= objPos.y) && (position.y <= (objPos.y + objSize.y));
        }
        return false;
    }
    // Collision with two boundaries
    public boolean isColliding(int minBoundary, int maxBoundary, boolean isVelocityHorizontal) {
        if (isVelocityHorizontal) {
            if (position.x <= minBoundary || (position.x + size.x) >= maxBoundary) {
                return true;
            }
        } else {
            if (position.y <= minBoundary || (position.y + size.y) >= maxBoundary) {
                return true;
            }
        }
        return false;
    }
    // Collision with horizontal boundary
    public boolean isCollidingHorizontally(int boundary, boolean isCollisionWithLeftBoundary) {
        if (isCollisionWithLeftBoundary) {
            return ((position.x + size.x) >= boundary);
        } else {
            return (position.x <= boundary);
        }
    }
    // Collision with vertical boundary
    public boolean isCollidingVertically(int boundary, boolean isCollisionWithUpperBoundary) {
        if (isCollisionWithUpperBoundary) {
            return ((position.y + size.y) >= boundary);
        } else {
            return (position.y <= boundary);
        }
    }

    //region GETTER
    public Context getContext() {
        return this.context;
    }
    public Point getScreenSize() {
        return this.SCREEN_SIZE;
    }
    public Point getPosition() {
        return this.position;
    }
    public Point getSize() {
        return this.size;
    }
    public Point getVelocity() {
        return  this.velocity;
    }
    public Paint getPaint() {
        return this.paint;
    }
    //endregion

    //region SETTER
    public void setPosition(Point position) {
        this.position = position;
    }
    public void setSize(Point size) {
        this.size = size;
    }
    public void setVelocity(Point velocity) {
        this.velocity = velocity;
    }
    public void invertVelocityX() {
        this.velocity.x = -this.velocity.x;
    }
    public void invertVelocityY() {
        this.velocity.y = -this.velocity.y;
    }
    public void setColor(int color) {
        this.paint.setColor(color);
    }
    //endregion

    private void setupPaint(int color) {
        paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(0);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }
}
