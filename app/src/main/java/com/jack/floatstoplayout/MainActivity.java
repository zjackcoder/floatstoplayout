package com.jack.floatstoplayout;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity implements HoverLayout.ScrollJudge{

    private HoverLayout hoverLayout;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        hoverLayout = (HoverLayout) findViewById(R.id.stop_float_layout);

        View headerView = LayoutInflater.from(this).inflate(R.layout.layout_hide,null,false);
        View hoverView = LayoutInflater.from(this).inflate(R.layout.layout_float,null,false);


        listView = new ListView(this);

        hoverLayout.setHesaderView(headerView);
        hoverLayout.setHoverView(hoverView);
        hoverLayout.setContentView(listView);

        listView.setAdapter(new Myadapter());

        hoverLayout.setScrollJudge(this);

    }


    @Override
    public boolean isContentViewTop() {
        return isAdapterViewTop(listView);
    }

    private static boolean isAdapterViewTop(AdapterView adapterView){
        if(adapterView != null){
            int firstVisiblePosition = adapterView.getFirstVisiblePosition();
            View childAt = adapterView.getChildAt(0);
            if(childAt == null || (firstVisiblePosition == 0 && childAt.getTop() == 0)){
                return true;
            }
        }
        return false;
    }


    class Myadapter extends BaseAdapter {

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
                convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_lv,null,false);

            }

            TextView txt = (TextView) convertView.findViewById(R.id.txt);
            txt.setText(data.get(position));
            return convertView;
        }
    }


}
