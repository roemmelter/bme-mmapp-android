package org.mmapp.game.breakout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import org.mmapp.game.DrawingObject;

/**
 * MIT License (http://choosealicense.com/licenses/mit/)
 * <p><br>
 * <b>Brick</b><br>
 * </p><br>
 *
 * @author Erik Roemmelt
 */
public class Brick extends DrawingObject {

    public Brick(Context context, int x, int y, int size, int color) {
        super(context, x, y, size, size, color);
    }

    @Override
    public void draw(Canvas canvas) {
        Point pos  = getPosition();
        Point size  = getSize();
        Paint paint = getPaint();
        canvas.drawRect(pos.x, pos.y, pos.x + size.x * 2 -1, pos.y + size.y -1, paint);
    }
}
