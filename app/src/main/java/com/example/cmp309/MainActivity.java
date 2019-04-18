package com.example.cmp309;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity {


    long cases;
    long townmodel;
    long bonscott;

    long casesSeconds;
    long townmodelSeconds;
    long bonscottSeconds;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button startButton = findViewById(R.id.back_button);
        final Button instButton = findViewById(R.id.inst_button);
        final Button exButton = findViewById(R.id.ex_button);

        final Intent intent = new Intent(MainActivity.this, ScanScreen.class);

        startButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(intent);
            }
        });

        instButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(getApplicationContext(), "Instruction", Toast.LENGTH_SHORT).show();
            }
        });

        exButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(getApplicationContext(), Long.toString(casesSeconds), Toast.LENGTH_SHORT).show();
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


}
