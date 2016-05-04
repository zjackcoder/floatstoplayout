package com.jack.floatstoplayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by jack on 16/3/16.
 */
public class StopFloatLayout extends LinearLayout {

    final String tag = "StopFloatLayout";

    public StopFloatLayout(Context context) {
        super(context);
        init();
    }

    public StopFloatLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StopFloatLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){
        this.setOrientation(VERTICAL);
    }




    View hideView;
    public void setHideView(int resId){
        hideView = LayoutInflater.from(getContext()).inflate(resId,null,false);
        this.addView(hideView, 0);


    }
    View floatView;
    public void setFloatView(int resId){
        floatView = LayoutInflater.from(getContext()).inflate(resId,null,false);
        this.addView(floatView, 1);
    }

    ContentView contentView;
    public void setContentView(int resId){
        contentView = (ContentView) LayoutInflater.from(getContext()).inflate(resId,null,false);
        this.addView(contentView,2,new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
    }



    float downX = -1;
    float downY = -1;

    float distanceY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {



        boolean dispatch = true;
        int action = ev.getAction();
        if (action == MotionEvent.ACTION_DOWN){
            Log.i(tag, "dispatchTouchEvent - ACTION_DOWN ");
            downX = -1;
            downY = -1;


        }else if (action == MotionEvent.ACTION_MOVE){

            Log.e(tag, "isFirstItemVisible -> " + contentView.isFirstItemVisible());
            if (!contentView.isFirstItemVisible()){
                return super.dispatchTouchEvent(ev);
            }else{

                Log.e(tag,"  >>>>>   ev.getY()->"+ev.getY() + "downY->" + downY);
                if (downY == -1) {
                    downY = ev.getY();
                    return dispatch;
                }

                Log.i(tag, "dispatchTouchEvent - ACTION_MOVE ");
                float littleMove = ev.getY() - downY;
                Log.e(tag, "littleMove ->" + littleMove);
                distanceY = distanceY + littleMove;
                Log.e(tag, "distanceY ->" + distanceY);

                if (distanceY < 0){
                    if (Math.abs(distanceY) < hideView.getHeight()){
                        LayoutParams lp = (LayoutParams) hideView.getLayoutParams();
                        lp.setMargins(0, (int) distanceY, 0,0);
                        hideView.setLayoutParams(lp);
                        downY = ev.getY();
                    }else if (Math.abs(distanceY) >= hideView.getHeight()){
                        downY = -1;
                        distanceY = -hideView.getHeight();
                        LayoutParams lp = (LayoutParams) hideView.getLayoutParams();
                        lp.setMargins(0, (int) distanceY, 0,0);
                        hideView.setLayoutParams(lp);
                        dispatch = super.dispatchTouchEvent(ev) ;
                    }

                }else{
                    distanceY = 0;
                }

                Log.e(tag,"  <<<<<<   ev.getY()->"+ev.getY() + "downY->" + downY);

            }



        }else if (action == MotionEvent.ACTION_UP){
            Log.i(tag,"dispatchTouchEvent - ACTION_UP ");
            if (Math.abs(distanceY) >= hideView.getHeight()){
                dispatch = super.dispatchTouchEvent(ev) ;
            }


        }
        if (Math.abs(distanceY) >= hideView.getHeight()){
            dispatch = super.dispatchTouchEvent(ev) ;
        }
        Log.e(tag,"dispatchTouchEvent - dispatch " + dispatch);
        return dispatch;

    }













}
