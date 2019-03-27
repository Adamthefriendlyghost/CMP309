package com.example.cmp309;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class InformationView extends AppCompatActivity {

    ImageView headerImage;
    TextView titleTextView;
    TextView descTextView;

    int inputInt;

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

        headerImage = (ImageView)findViewById(R.id.titleImage);
        titleTextView = (TextView)findViewById(R.id.titleTV);
        descTextView = (TextView)findViewById(R.id.descTV);

        String display = Integer.toString(inputInt);
        Log.v("OUTPUT", display);
        switch(inputInt){
            case 1:
                headerImage.setImageResource(R.drawable.cases);
                titleTextView.setText(R.string.CasketName);
                descTextView.setText(R.string.CasketInfo);
                break;

            case 2:
                headerImage.setImageResource(R.drawable.townmodel);
                titleTextView.setText(R.string.TownModelName);
                descTextView.setText(R.string.TownModelInfo);
                break;

            default:
                headerImage.setImageResource(R.drawable.default_image);
                titleTextView.setText(R.string.default_title);
                descTextView.setText(R.string.default_info);
                break;
        }
     }
}
