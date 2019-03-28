package com.example.cmp309;

import android.support.v7.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import android.content.SharedPreferences;
import android.nfc.NdefMessage;

import android.nfc.NfcAdapter;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import android.view.View;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;

import android.widget.Button;


public class ScanScreen extends AppCompatActivity {

    PendingIntent pendingIntent;
    IntentFilter writeTagFilters[];

    Context context;

    String testString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_screen);

        context = this;

        readFromIntent(getIntent());

        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
        writeTagFilters = new IntentFilter[] { tagDetected };

        final Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                onBackPressed();
            }
        });
    }

    private void readFromIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage[] msgs = null;
            if (rawMsgs != null) {
                msgs = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }
            }
            buildTagViews(msgs);
        }
    }
    private void buildTagViews(NdefMessage[] msgs) {
        if (msgs == null || msgs.length == 0) return;

        String text = "";
        String test2 = "slide";
        Log.v("slide", test2);
        byte[] payload = msgs[0].getRecords()[0].getPayload();
        String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16"; // Get the Text Encoding
        int languageCodeLength = payload[0] & 0063; // Get the Language Code, e.g. "en"

        try {
            // Get the Text
            text = new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
        } catch (UnsupportedEncodingException e) {
            Log.e("UnsupportedEncoding", e.toString());
        }

        testString = text;
        Log.d("NFC INPUT", testString);

        int inputInt = Integer.parseInt(text);

        final Intent intent = new Intent(ScanScreen.this, InformationView.class);
        intent.putExtra("throughInt", inputInt);
        startActivity(intent);


     }





}

