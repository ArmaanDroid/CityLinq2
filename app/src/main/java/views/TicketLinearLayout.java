package views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import sanguinebits.com.citylinq.R;

/**
 * Created by Armaan on 14-11-2017.
 */

public class TicketLinearLayout extends LinearLayout {
    private final int circleRadius;
    private Paint eraser;
    private int holesBottomMargin = 70;

    public TicketLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        circleRadius = getContext().getResources().getDimensionPixelOffset(R.dimen.ten_dp);
        Init();
    }

    private void Init() {
        eraser = new Paint();
        eraser.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.OVERLAY));
        eraser.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        View view = getChildAt(2);
        View view1 = getChildAt(1);

        Point lineStartPoint = new Point(view.getLeft(), view.getTop());
        Point lineEndPoint = new Point(view.getRight(), view.getTop());

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        paint.setStrokeWidth(0);
        paint.setStyle(Paint.Style.FILL);
//        canvas.drawRect(0, view.getTop(), 100, view.getTop() - pxFromDp(getContext(), holesBottomMargin), paint);
        eraser.setStyle(Paint.Style.FILL_AND_STROKE);
        eraser.setColor(ContextCompat.getColor(getContext(), R.color.activityBackground));
        // adding punching holes on the ticket by erasing them
        canvas.drawCircle(0, view.getTop(), circleRadius, eraser); // top-left hole
        canvas.drawCircle(getWidth(), view.getTop(), circleRadius, eraser); // top-left hole
//        canvas.drawCircle(100 / 2, 0, circleRadius, eraser); // top-middle hole
//        canvas.drawCircle(100, 0, circleRadius, eraser); // top-right
//        canvas.drawCircle(0, 100 - pxFromDp(getContext(), holesBottomMargin), circleRadius, eraser); // bottom-left hole
//        canvas.drawCircle(100, 100 - pxFromDp(getContext(), holesBottomMargin), circleRadius, eraser); // bottom right hole

        //line draw
        paint.setColor(ContextCompat.getColor(getContext(), R.color.dotColor));
        paint.setStyle(Paint.Style.STROKE);

        int lineWidth = getContext().getResources().getDimensionPixelOffset(R.dimen.one_point_five_dp);
        paint.setColor(ContextCompat.getColor(getContext(), R.color.lineGrey));
        paint.setStrokeWidth(lineWidth);
        paint.setPathEffect(new DashPathEffect(new float[]{10, 10,}, 0));

        Path path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        path.moveTo(lineStartPoint.x, lineStartPoint.y);
        path.lineTo(lineEndPoint.x, lineEndPoint.y);
        path.close();
        canvas.drawPath(path, paint);

        lineStartPoint = new Point(view1.getLeft(), view1.getTop());
        lineEndPoint = new Point(view1.getRight(), view1.getTop());

        Path path2 = new Path();
        path2.setFillType(Path.FillType.EVEN_ODD);
        path2.moveTo(lineStartPoint.x, lineStartPoint.y);
        path2.lineTo(lineEndPoint.x, lineEndPoint.y);
        path2.close();
        canvas.drawPath(path2, paint);


        super.onDraw(canvas);
    }

    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

}
