package views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Build;
import android.support.design.widget.TextInputEditText;
import android.util.AttributeSet;

import sanguinebits.com.citylinq.R;

/**
 * Created by Armaan on 13-10-2017.
 */

public class MyEditTextUnderlineGreen extends TextInputEditText {

    private final int linewidth;

    public MyEditTextUnderlineGreen(Context context, AttributeSet attrs) {
        super(context, attrs);
        linewidth = getResources().getDimensionPixelOffset(R.dimen.editText_line_width);
        isInEditMode();
        setHintTextColor(getResources().getColor(R.color.textColorGrey));
        setTextColor(getResources().getColor(R.color.textColorDark));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(getResources().getColor(android.R.color.transparent));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            paint.setColor(getResources().getColor(android.R.color.transparent, null));
        }
        setBackground(null);
        canvas.drawPaint(paint);
        paint.setStrokeWidth(linewidth);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setAntiAlias(true);
        Path path = new Path();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (hasFocus())
                paint.setColor(getResources().getColor(R.color.colorAccent, null));
            else
                paint.setColor(getResources().getColor(R.color.lineGrey, null));
        } else {
            if (hasFocus())
                paint.setColor(getResources().getColor(R.color.colorAccent));
            else
                paint.setColor(getResources().getColor(R.color.lineGrey));
        }
        Point c = new Point((getWidth() - getPaddingRight()), getHeight());
        path.setFillType(Path.FillType.EVEN_ODD);
        path.moveTo(getPaddingLeft() - 5, getHeight());
        path.lineTo(c.x, c.y);
        path.close();

        canvas.drawPath(path, paint);
        super.onDraw(canvas);
    }
}
