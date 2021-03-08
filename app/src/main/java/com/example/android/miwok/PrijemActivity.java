/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.miwok;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * třída pro načítání a zápis NFC
 */
public class PrijemActivity extends AppCompatActivity {
    public static final String Error_Detected = "No NFC Tag Detected";
    public static final String Write_Success = "Text written successfully!";
    public static final String Write_Error = "Error during writing try again";
    private static final String TAG = "MyActivity";

    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    IntentFilter writeTagFilters[];
    boolean writeBol;
    Tag myTag;//poslouzi pro zjisteni daneho cip
    Context context;
    TextView edit_message;
    TextView nfc_contents;
    Button activateButton;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nfc_layout);
        edit_message = (TextView) findViewById(R.id.editNFC);
        nfc_contents = (TextView) findViewById(R.id.readNFC);
        activateButton = (Button) findViewById(R.id.ActivateButton);
        context = this;
        activateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (myTag == null) {
                        Toast.makeText(context, "Error_Detected", Toast.LENGTH_LONG).show();
                    } else {
                        write("&:" + edit_message.getText().toString(), myTag);
                        Toast.makeText(context, "Write_Success", Toast.LENGTH_LONG).show();
                    }
                } catch (IOException e) {
                    Toast.makeText(context, "Write_Error", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                } catch (FormatException e) {
                    Toast.makeText(context, "Write_Error", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

        /**
         * zjisteni zda zarizeni podporuje NFC
         */
        nfcAdapter = NfcAdapter.getDefaultAdapter(context);


        if (!nfcAdapter.isEnabled()) {
            Toast.makeText(context, "You should be enable NFC", Toast.LENGTH_LONG).show();
            finish();
        }

        if (nfcAdapter == null) {
            Toast.makeText(context, "This device not supported NFC", Toast.LENGTH_LONG).show();
            finish();
        }


        readfromIntent(getIntent());
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
        writeTagFilters = new IntentFilter[]{tagDetected};

    }

    public void readfromIntent(Intent intent) {

        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action) || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage[] msgs = null;
            if (rawMsgs != null) {
                msgs = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }
            }
            buildTagView(msgs);
        }
    }

    private void buildTagView(NdefMessage[] msgs) {
        if (msgs == null || msgs.length == 0) {
            return;
        }
        String text = "";
        byte[] payload = msgs[0].getRecords()[0].getPayload();
        String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";
        int languageCodeLength = payload[0] & 0063;//get the language code

        try {
            //get the text
            text = new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
        } catch (UnsupportedEncodingException e) {
            Log.e("UnsupportedEncoding", e.toString());
            e.printStackTrace();
        }
        nfc_contents.setText("Váš text: " + text);
    }

    @SuppressLint("MissingPermission")
    private void write(String text, Tag tag) throws IOException, FormatException {
        NdefRecord[] records = {createRecord(text)};
        NdefMessage message = new NdefMessage(records);
        //get an instance
        Ndef ndef = Ndef.get(tag);
        //enable I/O
        ndef.connect();
        //write text message
        ndef.writeNdefMessage(message);
        //close connection
        ndef.close();

    }

    private NdefRecord createRecord(String text) throws UnsupportedEncodingException {
        String lang = "en";
        byte[] textBytes = text.getBytes();
        byte[] langBytes = lang.getBytes("US-ASCII");
        int langLength = langBytes.length;
        int textLength = textBytes.length;
        byte[] payload = new byte[1 + langLength + textLength];
        //set status byte
        payload[0] = (byte) langLength;
        System.arraycopy(langBytes, 0, payload, 1, langLength);
        System.arraycopy(textBytes, 0, payload, 1 + langLength, textLength);


        NdefRecord recordNfc = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], payload);

        return recordNfc;
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        readfromIntent(intent);
        if (!intent.hasExtra(NfcAdapter.EXTRA_TAG)) {//Jestliže chceme v této metodě zpracovávat pouze NFC záměr
            return;
        } else if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            myTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);


        }
    }

    @Override
    public void onPause() {
        super.onPause();
        WriteModeOff();
    }

    @Override
    public void onResume() {
        super.onResume();
        WriteModeOn();
    }

    @SuppressLint("MissingPermission")
    private void WriteModeOn() {
        writeBol = true;
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, writeTagFilters, null);
    }

    @SuppressLint("MissingPermission")
    private void WriteModeOff() {
        writeBol = false;
        nfcAdapter.disableForegroundDispatch(this);
    }

}


//        // Create a list of padaky
//        ArrayList<Padak> padaky = new ArrayList<Padak>();
//        padaky.add(new Padak("father", "әpә", R.drawable.family_father));
//        padaky.add(new Padak("mother", "әṭa", R.drawable.family_mother));
//        padaky.add(new Padak("son", "angsi", R.drawable.family_son));
//        padaky.add(new Padak("daughter", "tune", R.drawable.family_daughter));
//        padaky.add(new Padak("older brother", "taachi", R.drawable.family_older_brother));
//        padaky.add(new Padak("younger brother", "chalitti", R.drawable.family_younger_brother));
//        padaky.add(new Padak("older sister", "teṭe", R.drawable.family_older_sister));
//        padaky.add(new Padak("younger sister", "kolliti", R.drawable.family_younger_sister));
//        padaky.add(new Padak("grandmother ", "ama", R.drawable.family_grandmother));
//        padaky.add(new Padak("grandfather", "paapa", R.drawable.family_grandfather));
//
//        // Create an {@link WordAdapter}, whose data source is a list of {@link Padak}s. The
//        // adapter knows how to create list items for each item in the list.
//        WordAdapter adapter = new WordAdapter(this, padaky, R.color.category_family);
//
//        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
//        // There should be a {@link ListView} with the view ID called list, which is declared in the
//        // word_list.xml layout file.
//        ListView listView = (ListView) findViewById(R.id.list);
//
//        // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
//        // {@link ListView} will display list items for each {@link Padak} in the list.
//        listView.setAdapter(adapter);