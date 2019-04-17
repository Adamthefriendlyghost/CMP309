package com.example.cmp309;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


//Main Class
public class InformationView extends AppCompatActivity {

    //Declaring Views and Ints
    TextView titleTextView;
    TextView descTextView;
    int inputInt;

    //Declaring stuff for the ViewPager
    private ViewPager mpager;
    private static int curPage = 0;
    public static Integer[] Pics={1,2,3,4};
    public ArrayList<Integer> PicsArray = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_view);

        //Error catching for the data coming through the intent
        try{
            int data = getIntent().getExtras().getInt("throughInt");
            inputInt = data;
        }catch(Exception e){
            Log.e("ERROR", "ERROR");
        }

        //Setting up the TextViews
        titleTextView = (TextView)findViewById(R.id.titleTV);
        descTextView = (TextView)findViewById(R.id.descTV);

        //Setting up the Back Button
        final Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                onBackPressed();
            }
        });


        //Switch to set the page to the desired exhibit
        switch(inputInt){
            case 1:

                //Sets the images into the array
                Pics[0] = R.drawable.cases;
                Pics[1] = R.drawable.default_image2;
                Pics[2] = R.drawable.default_image3;
                Pics[3] = R.drawable.default_image4;

                //Sets the text to the TextViews
                titleTextView.setText(R.string.CasketName);
                descTextView.setText(R.string.CasketInfo);

                break;

            case 2:

                Pics[0] = R.drawable.townmodel;
                Pics[1] = R.drawable.default_image2;
                Pics[2] = R.drawable.default_image3;
                Pics[3] = R.drawable.default_image4;

                titleTextView.setText(R.string.TownModelName);
                descTextView.setText(R.string.TownModelInfo);

                break;

            case 3:

                Pics[0] = R.drawable.bon_scott_exhibit;
                Pics[1] = R.drawable.bon_scott;
                Pics[2] = R.drawable.bon_scott_old_exhibit;
                Pics[3] = R.drawable.bon_scott_statue;

                titleTextView.setText(R.string.BonScottName);
                descTextView.setText(R.string.BonScottInfo);

                break;

            default:

                //Set the defaults, just in case
                Pics[0] = R.drawable.default_image;
                Pics[1] = R.drawable.default_image2;
                Pics[2] = R.drawable.default_image3;
                Pics[3] = R.drawable.default_image4;

                titleTextView.setText(R.string.default_title);
                descTextView.setText(R.string.default_info);

                break;
        }
        //Call the function for the PageView
        init();
     }

     //Function to deal with the PageView
     private void init(){

        //Add all the Pictures in the Pics array to an Actual Array Object
        for(int i=0;i<Pics.length;i++)PicsArray.add(Pics[i]);

        //Set up the Viewpager, then set it to an Adapter (MyAdapter.java)
        mpager = (ViewPager)findViewById(R.id.pager);
        mpager.setAdapter(new MyAdapter( InformationView.this, PicsArray));

        //Set a Handler to update later
        final Handler handler = new Handler();
        //Set a Runnable to POST
        final Runnable Update = new Runnable() {
            //This code runs through the items one by one
            public void run() {
                 if (curPage == Pics.length) {
                     curPage = 0;
                 }
                 mpager.setCurrentItem(curPage++, true);
             }
         };

         //Setting a timer to switch pictures automatically
         Timer swipeTimer = new Timer();
         //Schedule the timers task to post the update to the handler every 4000ms
         swipeTimer.schedule(new TimerTask() {
             @Override
             public void run() {
                 handler.post(Update);
             }
         }, 4000, 4000);
    }
}
