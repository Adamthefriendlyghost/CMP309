package com.example.cmp309;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity {


    long cases;
    long townmodel;
    long bonscott;

    long casesSeconds;
    long townmodelSeconds;
    long bonscottSeconds;


    final CharSequence[] statsItems = {"Freedom Casket", "Town Model", "Bon Scott"};

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button startButton = findViewById(R.id.back_button);
        final Button aboutButton = findViewById(R.id.about_button);
        final Button statsButton = findViewById(R.id.stats_button);

        final Intent intent = new Intent(MainActivity.this, ScanScreen.class);

        startButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(intent);
            }
        });

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

    protected void onResume(){
        super.onResume();

        SharedPreferences prefs = getSharedPreferences("timer", 0);

        cases = prefs.getLong("et1", 0);
        townmodel = prefs.getLong("et2", 0);
        bonscott = prefs.getLong("et3", 0);

        casesSeconds = TimeUnit.MILLISECONDS.toSeconds(cases);
        townmodelSeconds = TimeUnit.MILLISECONDS.toSeconds(townmodel);
        bonscottSeconds = TimeUnit.MILLISECONDS.toSeconds(bonscott);


    }

    private void statsDialog(){

        final AlertDialog.Builder statsbuilder = new AlertDialog.Builder(this);
        statsbuilder.setTitle("Statistics");
        statsbuilder.setItems(statsItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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

        statsbuilder.create().show();

    }

    private void internalDialog(String Internal, long TimeFinal){
        final AlertDialog.Builder internalBuilder = new AlertDialog.Builder(this);
        internalBuilder.setTitle(Internal);
        internalBuilder.setMessage("The total time spent reading this entry is: " + Long.toString(TimeFinal) + " seconds.");
        internalBuilder.show();
    }

    private void aboutDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("About");
        builder.setMessage(R.string.about_contents);
        builder.create().show();
    }


}
