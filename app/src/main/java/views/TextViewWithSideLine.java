package views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.text.TextUtilsCompat;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;
import android.widget.TextView;

import sanguinebits.com.citylinq.R;

/**
 * Created by Armaan on 13-11-2017.
 */

public class TextViewWithSideLine extends TextView {
    private final int circleRadius;
    private Point c;
    private Point mid2;
    private Point mid1;
    private boolean isFirstItem = false;
    private boolean isLastItem;

    public TextViewWithSideLine(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        circleRadius = getContext().getResources().getDimensionPixelOffset(R.dimen.four_dp);
        isFirstItem = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(ContextCompat.getColor(getContext(), R.color.textColorLabelGrey));
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawCircle(circleRadius, getHeight() / 2, circleRadius, paint);

        c = new Point(circleRadius, getHeight());
        mid1 = new Point(circleRadius, getHeight() / 2 - circleRadius);
        mid2 = new Point(circleRadius, getHeight() / 2 + circleRadius);

        Path path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);

        int lineWidth = getContext().getResources().getDimensionPixelOffset(R.dimen.one_dp);
        if(lineWidth==0)
            lineWidth=1;
        paint.setColor(ContextCompat.getColor(getContext(), R.color.textColorLabelGrey));
        paint.setStrokeWidth(lineWidth);

        if (!isFirstItem)
            path.moveTo(circleRadius, 0);
        else
            path.moveTo(circleRadius, getHeight() / 2);

        path.lineTo(mid1.x, mid1.y);
        path.moveTo(mid2.x, mid2.y);
        if (!isLastItem)
            path.lineTo(c.x, c.y);

        path.close();
        canvas.drawPath(path, paint);
        super.onDraw(canvas);
    }

    public void customSetText(String stationName, String estimateTime) {
        SpannableString string = new SpannableString(stationName);
        SpannableString string1 = new SpannableString(estimateTime);

        //size span
        string.setSpan(new RelativeSizeSpan(1.2f), 0, string.length(), 0);
        string1.setSpan(new RelativeSizeSpan(0.8f), 0, string1.length(), 0);
        //color span
        string.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.textColorDark)), 0, string.length(), 0);
        string1.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.textColorLabelGrey)), 0, string.length(), 0);


            setText(TextUtils.concat(string,"\n" ,string1));

    }

    public void setIsFirst(boolean isFirst) {
        isFirstItem = isFirst;
        invalidate();
        requestLayout();
    }

    public void setIsLast(boolean isLast) {
        isLastItem = isLast;
        invalidate();
        requestLayout();
    }
}
