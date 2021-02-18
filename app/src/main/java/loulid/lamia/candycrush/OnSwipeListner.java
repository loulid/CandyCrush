package loulid.lamia.candycrush;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class OnSwipeListner implements View.OnTouchListener {
    public GestureDetector gestureDetector;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    public OnSwipeListner(Context context) {
        gestureDetector = new GestureDetector(context, new GestureListner());
    }

    private final class GestureListner extends GestureDetector.SimpleOnGestureListener
    {
        public static final int SWIPE_THRESSOLD = 100;
        public static final int SWIPE_VELOCITY_THRESSOLD = 100;

        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            //Log.i("DEBUG", e1 + "-"+ e2);
            boolean result = false;
            float yDiff = e2.getY() - e1.getY();
            float xDiff = e2.getX() - e1.getX();
            //déplacer  candies vers la droite ou gauche  sur  direction horizontale
            if (Math.abs(xDiff) > Math.abs(yDiff)) {
                if (Math.abs(xDiff) > SWIPE_THRESSOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESSOLD) {
                    //prsk axe droit de x est positif
                    if (xDiff > 0) {
                        OnSwipeRight();
                    } else {
                        OnSwipeLeft();
                    }
                    result = true;
                }
            } else if (Math.abs(yDiff) > SWIPE_THRESSOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESSOLD) {
                if (yDiff > 0) {
                    OnSwipeBottom();
                } else {
                    OnSwipeTop();
                }
                result = true;
            }


            return result;
        }

    }
    void OnSwipeLeft(){}
    void OnSwipeRight(){}
    void OnSwipeTop(){}
    void OnSwipeBottom(){}



}
