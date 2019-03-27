package com.example.cmp309;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button startButton = findViewById(R.id.start_button);
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
                Toast.makeText(getApplicationContext(), "Ex_Button", Toast.LENGTH_SHORT).show();
            }
        });

    }










}
