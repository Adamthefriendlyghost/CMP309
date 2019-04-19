package com.example.cmp309;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity {

    //Variables for the Timing
    long cases;
    long townmodel;
    long bonscott;
    long casesSeconds;
    long townmodelSeconds;
    long bonscottSeconds;

    //Text for the statistics dialog box
    final CharSequence[] statsItems = {"Freedom Casket", "Town Model", "Bon Scott"};

    //Main OnCreate for this Activity, the first thing the entire app does
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialising all the Buttons on the main screen
        final Button startButton = findViewById(R.id.back_button);
        final Button aboutButton = findViewById(R.id.about_button);
        final Button statsButton = findViewById(R.id.stats_button);

        //Declaring the intent for the Scan Screen, accessed when the user presses "Start"
        final Intent intent = new Intent(MainActivity.this, ScanScreen.class);
        startButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(intent);
            }
        });

        //Setting listeners for both buttons
        aboutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){ aboutDialog(); }
        });
        statsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                statsDialog();
            }
        });
    }

    //Setting the onResume for this Activity, needed to update the Statistics section
    protected void onResume(){
        super.onResume();

        //Setting up a separate thread as loading from a shared preference can be resource intensive
        new Thread(new Runnable() {
            @Override
            public void run() {

                //Loading the timer shared preference from storage
                SharedPreferences prefs = getSharedPreferences("timer", 0);

                //Loading the three values into local variables
                cases = prefs.getLong("et1", 0);
                townmodel = prefs.getLong("et2", 0);
                bonscott = prefs.getLong("et3", 0);

                //Then converting the three values from milliseconds to seconds
                casesSeconds = TimeUnit.MILLISECONDS.toSeconds(cases);
                townmodelSeconds = TimeUnit.MILLISECONDS.toSeconds(townmodel);
                bonscottSeconds = TimeUnit.MILLISECONDS.toSeconds(bonscott);
            }
        }).start();


        //Code to disable the NFC adapter in the main menu
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);

    }

    //Releasing the NFC adapter once leaving the main menu
    public void onPause() {
        super.onPause();
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        nfcAdapter.disableForegroundDispatch(this);
    }

    //Function for the dialog containing the statistics
    private void statsDialog(){

        //Setting up the dialog with a title and the items declared earlier
        final AlertDialog.Builder statsbuilder = new AlertDialog.Builder(this);
        statsbuilder.setTitle("Statistics");
        statsbuilder.setItems(statsItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //This sends the number the user pressed on and its corresponding name to internalDialog()
                switch (which){
                    case 0:
                        internalDialog("Freedom Casket", casesSeconds);
                        break;

                    case 1:
                        internalDialog("Town Model", townmodelSeconds);
                        break;

                    case 2:
                        internalDialog("Bon Scott", bonscottSeconds);
                        break;
                }
            }
        });
        //Create the dialog and then show it
        statsbuilder.create().show();
    }

    //The statistic dialog sends information into here
    private void internalDialog(String Internal, long TimeFinal){

        //Set up the next dialog, the equivalent of going one deeper into the navigation
        //Sets the title and the message depending on what is sent by the parent function
        final AlertDialog.Builder internalBuilder = new AlertDialog.Builder(this);
        internalBuilder.setTitle(Internal);
        internalBuilder.setMessage("The total time spent reading this entry is: " + Long.toString(TimeFinal) + " seconds.");
        internalBuilder.show();
    }

    //The other dialog, about this application
    private void aboutDialog(){

        //Setting up the title and message as has been done before
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("About");
        builder.setMessage(R.string.about_contents);
        builder.create().show();
    }
}