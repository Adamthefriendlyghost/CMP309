package com.example.cmp309;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class InformationView extends AppCompatActivity {

    TextView titleTextView;
    TextView descTextView;
    int inputInt;

    private static ViewPager mpager;
    private static int curPage = 0;

    private static final Integer[] Pics={R.drawable.cases, R.drawable.townmodel, R.drawable.default_image};
    private ArrayList<Integer> PicsArray = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_view);
        try{
            int data = getIntent().getExtras().getInt("throughInt");
            inputInt = data;
        }catch(Exception e){
            Log.e("ERROR", "ERROR");
        }

        titleTextView = (TextView)findViewById(R.id.titleTV);
        descTextView = (TextView)findViewById(R.id.descTV);

        String display = Integer.toString(inputInt);
        Log.v("OUTPUT", display);

        init();

        switch(inputInt){
            case 1:

                titleTextView.setText(R.string.CasketName);
                descTextView.setText(R.string.CasketInfo);
                break;

            case 2:

                titleTextView.setText(R.string.TownModelName);
                descTextView.setText(R.string.TownModelInfo);
                break;

            default:

                titleTextView.setText(R.string.default_title);
                descTextView.setText(R.string.default_info);
                break;
        }
     }

     private void init(){

        for(int i=0;i<Pics.length;i++)PicsArray.add(Pics[i]);

        mpager = (ViewPager)findViewById(R.id.pager);
        mpager.setAdapter(new MyAdapter( InformationView.this, PicsArray));

         final Handler handler = new Handler();
         final Runnable Update = new Runnable() {
             public void run() {
                 if (curPage == Pics.length) {
                     curPage = 0;
                 }
                 mpager.setCurrentItem(curPage++, true);
             }
         };

         Timer swipeTimer = new Timer();
         swipeTimer.schedule(new TimerTask() {
             @Override
             public void run() {
                 handler.post(Update);
             }
         }, 2500, 2500);



    }



}
