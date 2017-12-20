package com.amuyu.whattoeat.view.random;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.animation.DynamicAnimation;
import android.support.animation.FlingAnimation;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.amuyu.logger.Logger;
import com.amuyu.whattoeat.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.support.animation.DynamicAnimation.MIN_VISIBLE_CHANGE_SCALE;

public class WheelView extends View {
    private WheelView wheelView;
    private List<Item> items = new ArrayList<Item>();
    private RectF bounds;

    private GestureDetector mDetector;
    private FlingAnimation mFlingAnimation;

    private Paint mStroke;
    private Listener callback;
    private boolean useCallback;

    public WheelView(Context context) {
        super(context);
        init(context);
    }

    public WheelView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        wheelView = this;
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        mStroke = new Paint();
        mStroke.setColor(getResources().getColor(R.color.white));
        mStroke.setStrokeWidth(2);
        mStroke.setStyle(Paint.Style.STROKE);


        mDetector = new GestureDetector(context, new GestureListener());
        mDetector.setIsLongpressEnabled(false);
        mFlingAnimation = new FlingAnimation(this, DynamicAnimation.ROTATION);
        mFlingAnimation.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
            @Override
            public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {
                if (!useCallback && velocity < 2) {
                    useCallback = true;
                    callback.onSelectedItem(getItem((int)wheelView.getRotation()));
                }
            }
        });
        
        
        mFlingAnimation.addEndListener(new DynamicAnimation.OnAnimationEndListener() {
            @Override
            public void onAnimationEnd(DynamicAnimation animation, boolean canceled, float value, float velocity) {
                if (callback != null) {
                    useCallback = false;
                    callback.onSelectedItem(getItem((int)wheelView.getRotation()));
                }
            }
        });



        Resources res = getResources();
        addItem(res.getColor(R.color.red), "초밥", R.drawable.a);
        addItem(res.getColor(R.color.blue), "함박스테이크", R.drawable.b);
        addItem(res.getColor(R.color.green), "샐러드", R.drawable.c);
        addItem(res.getColor(R.color.yellow), "닭강정", R.drawable.d);
        onDataChanged();
    }

    public void setCallback(Listener callback) {
        this.callback = callback;
    }

    public void addItem(int color, String value, int resource) {
        Item i = new Item();
        i.mColor = color;
        i.value = value;
        i.bitmap = BitmapFactory.decodeResource(getResources(), resource);
        items.add(i);
    }

    private void onDataChanged() {
        int currentAngle = 0;
        float sweepAngle = getSweepAngle();
        for (Item it : items) {
            it.mStartAngle = currentAngle;
            currentAngle = (int) ((float) currentAngle + sweepAngle);
        }
    }

    private float getSweepAngle() {
        return 1 * 360.0f / items.size();
    }


    private String getItem(int rotation) {
        int rotateAngle = (rotation + 90) % 360;
        rotateAngle = 360 - rotateAngle;
        float sweepAngle = getSweepAngle();
        for (int i = 0; i < items.size(); ++i) {
            Item it = items.get(i);
            if (it.mStartAngle <= rotateAngle &&
                    rotateAngle < it.mStartAngle + sweepAngle ) {
                Logger.d(it.toString());
                return it.value;
            }
        }
        return null;
    }



    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        bounds = new RectF(0,0,w,h);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = mDetector.onTouchEvent(event);
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float sweepAngle = getSweepAngle();
        for (Item item : items) {
            Paint paint = new Paint();
            paint.setColor(item.mColor);

            canvas.drawArc(bounds, item.mStartAngle, sweepAngle, true, paint);

            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            Rect arcRect = getArcRect(bounds, item.mStartAngle, (int)(item.mStartAngle + sweepAngle));
            canvas.drawBitmap(item.bitmap, null, arcRect, paint);

            canvas.drawArc(bounds, item.mStartAngle, sweepAngle, true, mStroke);
            Logger.d(item.toString());
        }


    }

    private Rect getArcRect(RectF circle, int startAngle, int endAngle) {
        int r = (int)(circle.width() / 2);

        int centerX = (int)circle.centerX();
        int centerY = (int)circle.centerY();

        int x1 = (int)(r * Math.cos(Math.toRadians(startAngle)));
        int y1 = (int)(r * Math.sin(Math.toRadians(startAngle)));

        int x2 = (int)(r * Math.cos(Math.toRadians(endAngle)));
        int y2 = (int)(r * Math.sin(Math.toRadians(endAngle)));


        List<Integer> list = new ArrayList<>();
        list.add(centerX);
        list.add(centerX + x1);
        list.add(centerX + x2);

        Collections.sort(list);
        int left = list.get(0);
        int right = list.get(2);

        list.clear();
        list.add(centerY);
        list.add(centerY+y1);
        list.add(centerY+y2);
        Collections.sort(list);

        int top = list.get(0);
        int bottom = list.get(2);

        return new Rect(left, top, right, bottom);
    }

    private class Item {
        public int mColor;
        // computed values
        public int mStartAngle;
        public String value;
        public Bitmap bitmap;

        @Override
        public String toString() {
            return "Item{" +
                    "mStartAngle=" + mStartAngle +
                    ", value='" + value + '\'' +
                    '}';
        }
    }

    public interface Listener {
        void onSelectedItem(String item);
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Logger.d("");

            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Logger.d("");
            float scrollTheta = vectorToScalarScroll(velocityX, velocityY,
                    e2.getX() - bounds.centerX(),
                    e2.getY() - bounds.centerY());

            mFlingAnimation.setStartVelocity(scrollTheta/4)
                    .setMinimumVisibleChange(MIN_VISIBLE_CHANGE_SCALE)
                    .setFriction(0.5f)
                    .start();

            return true;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        /**
         * Helper method for translating (x,y) scroll vectors into scalar rotation of the pie.
         *
         * @param dx The x component of the current scroll vector.
         * @param dy The y component of the current scroll vector.
         * @param x  The x position of the current touch, relative to the pie center.
         * @param y  The y position of the current touch, relative to the pie center.
         * @return The scalar representing the change in angular position for this scroll.
         */
        private float vectorToScalarScroll(float dx, float dy, float x, float y) {
            // get the length of the vector
            float l = (float) Math.sqrt(dx * dx + dy * dy);

            // decide if the scalar should be negative or positive by finding
            // the dot product of the vector perpendicular to (x,y).
            float crossX = -y;
            float crossY = x;

            float dot = (crossX * dx + crossY * dy);
            float sign = Math.signum(dot);

            return l * sign;
        }
    }
}
