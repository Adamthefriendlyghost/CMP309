package com.example.cmp309;

import android.support.v7.app.AppCompatActivity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import java.io.UnsupportedEncodingException;
import android.widget.Button;

public class ScanScreen extends AppCompatActivity {

    //Set up Intent for the NFC Contact
    PendingIntent pendingIntent;
    IntentFilter writeTagFilters[];
    Context context;

    //Normal OnCreate function for this activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_screen);

        //Setting up the Context and Intent for the NFC Contact
        context = this;
        readFromIntent(getIntent());

        //Using pending intents, tagDetected and TagFilters to receive the NFC Input properly
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
        writeTagFilters = new IntentFilter[] { tagDetected };

        //Set up the back button
        final Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }


    //Function to deal with reading from the NFC intent
    private void readFromIntent(Intent intent) {
        String action = intent.getAction();
        //If the Tag that is discovered is any one of the three technologies; tag, tech or ndef, then read from it
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action) || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action) || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            //Read the raw message in as a parcelable array
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            //Set up the message array
            NdefMessage[] msgs = null;

            //If the message actually contains anything, find the length and read it into an array
            if (rawMsgs != null) {
                msgs = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }
            }
            buildTagViews(msgs);
        }
    }

    //Function to build the message from the previous function
    private void buildTagViews(NdefMessage[] msgs) {

        String text="";

        //Reading the input as an array of bytes (cause that's how it's held in NFC)
        byte[] payload = msgs[0].getRecords()[0].getPayload();

        //Ensuring that the text can be encoded
        try {
            // Get the Text
            text = new String(payload, 3, payload.length - 3, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.e("UnsupportedEncoding", e.toString());
        }

        int inputInt = Integer.parseInt(text);

        final Intent intent = new Intent(ScanScreen.this, InformationView.class);
        intent.putExtra("throughInt", inputInt);
        startActivity(intent);
     }
}