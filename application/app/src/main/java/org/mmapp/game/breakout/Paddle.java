package org.mmapp.game.breakout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import org.mmapp.R;
import org.mmapp.game.DrawingObject;

/**
 * MIT License (http://choosealicense.com/licenses/mit/)
 * <p><br>
 * <b>Paddle</b><br>
 * Description
 * </p><br>
 *
 * @author Erik Roemmelt
 */
public class Paddle extends DrawingObject {
    private final int   SCREEN_MARGIN = 5;
    private final Point SCREEN_SIZE;

    public Paddle(Context context, int size) {
        super(context, 0, 0, size, size / 5, Color.BLACK);
        SCREEN_SIZE = getScreenSize();

        final int START_POS_X = (SCREEN_SIZE.x - size) / 2;
        final int START_POS_Y = SCREEN_SIZE.y - (4 * size / 5);
        setPosition(new Point(START_POS_X, START_POS_Y));
        setColor(getContext().getResources().getColor(R.color.DarkSlateGray));
    }

    @Override
    public void updatePhysics() {
        Point paddlePosition = getPosition();
        if (isColliding(0, SCREEN_SIZE.x, true)) {
            if (isCollidingHorizontally(0, false)) {
                // Collision with left wall
                paddlePosition.x += SCREEN_MARGIN;
            } else if (isCollidingHorizontally(SCREEN_SIZE.x - getSize().x, true)) {
                // Collision with right wall
                paddlePosition.x -= SCREEN_MARGIN;
            }
            setPosition(new Point(paddlePosition.x, paddlePosition.y));
            setVelocity(new Point(0, 0));
        } else {
            // No Collision
            setPosition(new Point(paddlePosition.x + getVelocity().x, paddlePosition.y));
        }
    }

    @Override
    public void draw(Canvas canvas) {
        Point     pos    = getPosition();
        Point     size   = getSize();
        Paint     paint  = getPaint();
        final int RADIUS = size.y / 2;
        canvas.drawCircle(pos.x + RADIUS, pos.y + RADIUS, RADIUS, paint);
        canvas.drawRect(pos.x + RADIUS, pos.y, pos.x + size.x - RADIUS, pos.y + size.y, paint);
        canvas.drawCircle(pos.x + size.x - RADIUS, pos.y + RADIUS, RADIUS, paint);
    }

    public void setVelocityX(float vxNormalized, float vxScale) {
        setVelocity(new Point((int) (vxNormalized * vxScale), getVelocity().y));
    }
}
