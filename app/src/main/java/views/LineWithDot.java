package views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import sanguinebits.com.citylinq.R;

/**
 * Created by Armaan on 07-11-2017.
 */

public class LineWithDot extends TextView {


    private final int circleRadius;
    private  Point c;
    private Point mid1;
    private Point mid2;

    public LineWithDot(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        circleRadius= getContext().getResources().getDimensionPixelOffset(R.dimen.four_dp);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        c = new Point(getWidth(), getHeight() / 2);
        mid1 = new Point(getWidth()/2-12, getHeight() / 2);
        mid2 = new Point(getWidth()/2+12, getHeight() / 2);

            paint.setColor(ContextCompat.getColor(getContext(), R.color.dotColor));
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, circleRadius, paint);

        int lineWidth = getContext().getResources().getDimensionPixelOffset(R.dimen.one_point_five_dp);
        paint.setColor(ContextCompat.getColor(getContext(), R.color.lineGrey));
        paint.setStrokeWidth(lineWidth);

        Path path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        path.moveTo(0, getHeight() / 2);
        path.lineTo(mid1.x,mid1.y);
        path.moveTo(mid2.x,mid2.y);
        path.lineTo(c.x, c.y);

        path.close();
        canvas.drawPath(path, paint);

        super.onDraw(canvas);
    }
}
