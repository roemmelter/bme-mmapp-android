package org.mmapp.game.breakout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import org.mmapp.game.DrawingObject;
import org.mmapp.util.MathUtil;

/**
 * MIT License (http://choosealicense.com/licenses/mit/)
 * <p><br>
 * <b>Ball</b><br>
 * Description
 * </p><br>
 *
 * @author Erik Roemmelt
 */
public class Ball extends DrawingObject {
    private final Point  SCREEN_SIZE;
    private       int    velocityFactor = 1;
    private       int    randomAngleMin = 1;
    private       int    randomAngleMax = 179;
    private       Paddle paddle;

    public Ball(Context context, int radius) {
        super(context, 0, 0, 2 * radius, 2 * radius, Color.WHITE);
        SCREEN_SIZE = getScreenSize();

        final int START_POS_X = getRandomPositionX();
        final int START_POS_Y = SCREEN_SIZE.y / 2;
        setPosition(new Point(START_POS_X, START_POS_Y));
    }

    @Override
    public void updatePhysics() {
        Point curPosition = getPosition();
        Point curVelocity = getVelocity();
        setPosition(new Point(curPosition.x + curVelocity.x, curPosition.y + curVelocity.y));

        // X-Collision with wall
        if (isColliding(0, SCREEN_SIZE.x, true)) {
            invertVelocityX();
        }
        // Y-Collision with ceiling
        if (isCollidingVertically(0, false)) {
            invertVelocityY();
        } else if (paddle != null && isColliding(paddle) &&
                   isCollidingVertically(paddle.getPosition().y, true)) {
            // Y-Collision with paddle
            invertVelocityY();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = getPaint();
        Point pos   = getPosition();
        float size  = getSize().x;

        canvas.drawCircle(pos.x + size / 2, pos.y + size / 2, size / 2, paint);
    }

    public void spawn() {
        final int RANDOM_ANGLE = MathUtil.nextRandomInt(randomAngleMin, randomAngleMax + 1);
        final int START_VX     = (int) (Math.cos(RANDOM_ANGLE) * velocityFactor);
        final int START_VY     = (int) (Math.sin(RANDOM_ANGLE) * velocityFactor);

        setVelocity(new Point(START_VX, START_VY));
        setColor(Color.BLACK);
    }

    public void setVelocityFactor(int factor) {
        if (factor > 0) {
            this.velocityFactor = factor;
        }
    }

    public void setRandomAngleBoundaries(int minAngle, int maxAngle) {
        if (minAngle > maxAngle) {
            int tmp = minAngle;
            minAngle = maxAngle;
            maxAngle = tmp;
        }
        this.randomAngleMin = minAngle;
        this.randomAngleMax = maxAngle;
    }

    public void setPaddleForCollisionDetection(Paddle paddle) {
        this.paddle = paddle;
    }

    private int getRandomPositionX() {
        return MathUtil.nextRandomInt((SCREEN_SIZE.x - 2 * getSize().x) + 1);
    }
}
