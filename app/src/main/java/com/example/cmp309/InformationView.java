package com.example.cmp309;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class InformationView extends AppCompatActivity {

    //Declaring the title and description text views to be edited during runtime
    TextView titleTextView;
    TextView descTextView;

    //Declaring the integer for receiving from the Scan Screen
    int inputInt;

    //Declaring variables for the ViewPager
    private ViewPager mpager;
    private static int curPage = 0;
    public static Integer[] Pics={1,2,3,4};
    public ArrayList<Integer> PicsArray = new ArrayList<Integer>();

    //Declaring variables for the Timing function
    long startTime_1;
    long startTime_2;
    long startTime_3;
    long elapsedTime_1;
    long elapsedTime_2;
    long elapsedTime_3;


    //The onCreate function for this activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_view);

        //Error catching for the data coming through the Scan Screen
        try{

            //Get the data from the intent, hopefully an int 1-3, then save as a local variable
            int data = getIntent().getExtras().getInt("throughInt");
            inputInt = data;

        }catch(Exception e){

            //If it's not 1-3, then output an error and save the variable as 0 to satisfy further functions
            Log.e("ERROR", "ERROR");
            inputInt = 0;
        }

        //Setting up the TextViews for the Title and the Description
        titleTextView = (TextView)findViewById(R.id.titleTV);
        descTextView = (TextView)findViewById(R.id.descTV);

        //Setting up the Back Button to take the user back to the ScanScreen
        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });

        //Switch to set the page to the desired exhibit
        switch(inputInt){
            case 1:

                //Start the timer
                startTime_1 = System.currentTimeMillis();

                //Sets the images into the array
                Pics[0] = R.drawable.cases;
                Pics[1] = R.drawable.default_image2;
                Pics[2] = R.drawable.default_image3;
                Pics[3] = R.drawable.default_image4;

                //Sets the text for the TextViews
                titleTextView.setText(R.string.CasketName);
                descTextView.setText(R.string.CasketInfo);

                break;

            case 2:

                startTime_2 = System.currentTimeMillis();

                Pics[0] = R.drawable.townmodel;
                Pics[1] = R.drawable.default_image2;
                Pics[2] = R.drawable.default_image3;
                Pics[3] = R.drawable.default_image4;

                titleTextView.setText(R.string.TownModelName);
                descTextView.setText(R.string.TownModelInfo);

                break;

            case 3:

                startTime_3 = System.currentTimeMillis();

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

    //Ensuring the timer is properly saved when the user leaves the activity
    protected void onPause(){
        super.onPause();

        //Get the current time on the device, for the timer
        long date = (new Date()).getTime();

        //If and else statements to calculate the time taken on each section
        if(startTime_1 > 0){
            elapsedTime_1 = date - startTime_1;
        }
        else{
            elapsedTime_1 = 0;
        }

        if(startTime_2 > 0){
            elapsedTime_2 = date - startTime_2;
        }
        else{
            elapsedTime_2 = 0;
        }

        if(startTime_3 > 0){
            elapsedTime_3 = date - startTime_3;
        }
        else{
            elapsedTime_3 = 0;
        }

        //Setting up a separate thread to save the timer into the Shared Preference
        new Thread(new Runnable() {
            @Override
            public void run() {

                //Open the Shared Preference
                SharedPreferences prefs = getSharedPreferences("timer", 0);

                //Save the current values to a temporary value
                long et1 = prefs.getLong("et1", 0);
                long et2 = prefs.getLong("et2", 0);
                long et3 = prefs.getLong("et3", 0);

                //Open the editor
                SharedPreferences.Editor editor = prefs.edit();

                //Save the new value as the current value + the value in this instance
                if(elapsedTime_1 > 0){
                    editor.putLong("et1", elapsedTime_1 + et1);
                }
                if(elapsedTime_2 > 0) {
                    editor.putLong("et2", elapsedTime_2 + et2);
                }
                if(elapsedTime_3 > 0) {
                    editor.putLong("et3", elapsedTime_3 + et3);
                }
                editor.commit();
            }
        }).start();
    }
}