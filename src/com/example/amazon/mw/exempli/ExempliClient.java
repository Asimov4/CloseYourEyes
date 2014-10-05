/*
 * ExempliClient.java
 *
 * Copyright (c) 2014 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Use is subject to license terms.
 */
package com.example.amazon.mw.exempli;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ExempliClient is demo code that simulates a concert venue search service
 * to which a simple plugin sends a performer name.
 *
 * Concert locations and dates are randomly generated.
 */
public class ExempliClient {
    // Helper class to hold mocked concert data.
    public static class ExempliResponse {
        // Concert Date
        private String mConcertDate;
        // Concert location
        private String mConcertLocation;

        // Constructs a response with given date and location.
        public ExempliResponse(String concertDate, String concertLocation) {
            mConcertDate = concertDate;
            mConcertLocation = concertLocation;
        }

        // Get the concert date.
        public String getConcertDate() {
            return mConcertDate;
        }

        // Get the concert location.
        public String getConcertLocation() {
            return mConcertLocation;
        }

        // If a response exists, a concert is found.
        public boolean foundConcert() {
            return true;
        }

    }

    // Demo concert locations for getConcertLocation to return.
    private static Map<String,String> asinToImdbId = new HashMap<String, String>();
    static {
        asinToImdbId.put("B005T5MYXC","tt0110912");
        asinToImdbId.put("B0030MBX56","tt0116282"); // fargo
    }

    // Method to return mocked concert date and concert location (static info for demo purposes).
    public ArrayList<String> getImdbData(String videoAsin) {
        ArrayList<String> guides = new ArrayList<String>();
        try {
            String imdbId = asinToImdbId.get(videoAsin);
            Log.e("Asin: ",videoAsin);
            Log.e("Parent guide page: ","http://www.imdb.com/title/" + imdbId + "/parentalguide");
            Document doc = Jsoup.connect("http://www.imdb.com/title/" + imdbId + "/parentalguide").get();
            Elements parentGuideElements = doc.select(".display p");

            for (Element element: parentGuideElements) {
                guides.add(element.text());
                Log.e("retrieved guide: ",element.text());
            }
        } catch (IOException e) {
            Log.e("exception","Client failure: ",e);
        }
        return guides;
    }

    // Method to return mocked concert dates.
    static String getConcertDate() {
        long newTime = System.currentTimeMillis() + 7*24*60*60*1000; // Add 7 days to current time.
        Date futureDate = new Date();
        futureDate.setTime(newTime);
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        return format.format(futureDate);
    }

}
