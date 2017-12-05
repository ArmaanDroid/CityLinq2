package views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.TextView;

import sanguinebits.com.citylinq.R;

/**
 * Created by Armaan on 17-11-2017.
 */

public class DottedLineVertical extends TextView{
    private static final int DEFAULT_WIDTH = 4;
    private final int lineColor;
    private final int dashWidth;

    public DottedLineVertical(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.DottedLineVertical,
                0, 0
        );
        dashWidth = a.getDimensionPixelOffset(
                R.styleable.DottedLineVertical_dashWidth
                , DEFAULT_WIDTH);
        lineColor = a.getColor(R.styleable.DottedLineVertical_lineColor, ContextCompat.getColor(getContext(), R.color.lineGrey));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();

        int lineWidth = getContext().getResources().getDimensionPixelOffset(R.dimen.one_dp);
        paint.setColor(lineColor);
        paint.setStrokeWidth(lineWidth);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        paint.setPathEffect(new DashPathEffect(new float[]{dashWidth, dashWidth,}, 0));

        Path path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        path.moveTo(getWidth()/2, 0);
        path.lineTo(getWidth()/2, getHeight());
        path.close();
        canvas.drawPath(path, paint);
        super.onDraw(canvas);
    }
}
