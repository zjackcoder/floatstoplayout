package com.jack.floatstoplayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jack on 16/3/16.
 */
public class ContentView extends ListView {

    final String tag = "ContentView";

    public ContentView(Context context) {
        super(context);
        init();
    }

    public ContentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ContentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Myadapter myadapter = new Myadapter();
        this.setAdapter(myadapter);
    }



    class Myadapter extends BaseAdapter{

        ArrayList<String> data = new ArrayList<>();
        public Myadapter(){
            for (int i = 0; i < 20; i++) {
                data.add(i+"");
            }
        }


        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_lv,null,false);

            }

            TextView txt = (TextView) convertView.findViewById(R.id.txt);
            txt.setText(data.get(position));
            return convertView;
        }
    }


    public boolean isreadyUnhandelTouch(){
        if (isFirstItemVisible()){
            Log.e(tag,"isFirstItemVisible -> " +isFirstItemVisible());
            return true;
        }
//        Log.e(tag,"isFirstItemVisible -> " + isFirstItemVisible());
        return true;
    }


    public boolean isFirstItemVisible() {
        final Adapter adapter = this.getAdapter();

        if (null == adapter || adapter.isEmpty()) {
            Log.d(tag, "isFirstItemVisible. Empty View.");
            return true;

        } else {

            Log.e(tag,"this.getFirstVisiblePosition()->"+this.getFirstVisiblePosition());
            if (this.getFirstVisiblePosition() <= 0) {
                final View firstVisibleChild = this.getChildAt(0);
                if (firstVisibleChild != null) {
                    Log.e(tag,"firstVisibleChild.getTop() ->"+firstVisibleChild.getTop() + "    this.getTop()->"+this.getTop());
                    return firstVisibleChild.getTop() >= 0;
                }
            }
        }

        return false;
    }


}
