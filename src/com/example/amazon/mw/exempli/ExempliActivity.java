/*
 * ExempliActivity.java
 *
 * Copyright (c) 2014 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Use is subject to license terms.
 */
package com.example.amazon.mw.exempli;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * This Activity demonstrates the Exempli application's functionality.
 *
 * It will consume the intent launched by the ExempliPlugin and display concert
 * information for an identified performer.
 */
public class ExempliActivity extends Activity {
    // Concert TextView
    private TextView mConcertInfo;
    private ListView mGuidesList;

    // Get the intent that started this Activity and display the associated concert info.
    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.main);

        // Get the intent that started this Activity.
        Intent concertIntent = getIntent();
        final String concertInfo = concertIntent.getStringExtra(Intent.EXTRA_TEXT);

        List<String> guides = (ArrayList<String>) concertIntent.getSerializableExtra("guides");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,guides);

        // Update the concert TextView.
        mConcertInfo = (TextView) this.findViewById(R.id.concert_textview);
        mGuidesList = (ListView) this.findViewById(R.id.listView);

        /* This Activity is meant to be launched from the Firefly application and associated
         * with identified music.
         *
         * If it is launched any other way, display text describing its intended usage.
         */
        if (concertInfo != null) {
            mConcertInfo.setText(concertInfo);
            mGuidesList.setAdapter(adapter);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(10000);
                }
            }, 10000);
        } else {
            mConcertInfo.setText(getString(R.string.intended_usage));
        }
    }
}
