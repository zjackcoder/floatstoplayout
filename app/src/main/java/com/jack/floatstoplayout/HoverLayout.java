package com.jack.floatstoplayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by jack on 16/3/17.
 */
public class HoverLayout extends LinearLayout {


    final String tag = "HoverLayout";

    public HoverLayout(Context context) {
        this(context, null, 0);
    }
    public HoverLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HoverLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public void init(){
        this.setOrientation(VERTICAL);
    }


    private View headerView;
    private View hoverView;
    private View contentView;

    public void setHesaderView(View headerView){
        this.headerView = headerView;
        this.addView(headerView,new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
    }
    public void setHoverView(View hoverView){
        this.hoverView = hoverView;
        this.addView(hoverView,new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
    }
    public void setContentView(View contentView){
        this.contentView = contentView;
        this.addView(contentView,new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
    }


    float lastY = -1;
    float delta = 0;
    float distanceY = 0;//向上滚动的距离,负值;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean dispatch = true;
        int action = ev.getAction();
        if (action == MotionEvent.ACTION_DOWN){
            Log.i(tag, "dispatchTouchEvent - ACTION_DOWN ");
            lastY = -1;
        }else if (action == MotionEvent.ACTION_MOVE){
            if (!isContentViewTop()){
                //contentView  不是到顶端, 应该让contentview 获取触摸事件
                return super.dispatchTouchEvent(ev);

            }else{
                // 处理当前layout的事件
                if (lastY == -1){
                    lastY = ev.getY();
                    return dispatch;
                }
                delta = ev.getY() - lastY;
                distanceY += delta;
                if (distanceY < 0){
                    if (distanceY + headerView.getHeight() > 0){
                        LayoutParams lp = (LayoutParams) headerView.getLayoutParams();
                        lp.setMargins(0, (int) distanceY,0,0);
                        headerView.setLayoutParams(lp);
                        lastY = ev.getY();
                    }else{
                        lastY = -1;
                        distanceY = -headerView.getHeight();
                        LayoutParams lp = (LayoutParams) headerView.getLayoutParams();
                        lp.setMargins(0, (int) distanceY, 0, 0);
                        headerView.setLayoutParams(lp);
                        dispatch = super.dispatchTouchEvent(ev);
                    }
                }else{
                    distanceY = 0;
                }


            }


        }else if (action == MotionEvent.ACTION_UP){
            Log.i(tag,"dispatchTouchEvent - ACTION_UP ");


        }

        if (distanceY + headerView.getHeight() <= 0){
            dispatch = super.dispatchTouchEvent(ev) ;
        }

        return dispatch;
    }

    private boolean isContentViewTop() {
        if (scrollJudge == null){
            Log.w(tag,"no set ScrollJudge!");
            return true;
        }
        return scrollJudge.isContentViewTop();
    }




    private ScrollJudge scrollJudge;
    public interface ScrollJudge{
        public boolean isContentViewTop();
    }
    public void setScrollJudge(ScrollJudge scrollJudge) {
        this.scrollJudge = scrollJudge;
    }

}
