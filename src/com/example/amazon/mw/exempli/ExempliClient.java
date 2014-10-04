/*
 * ExempliClient.java
 *
 * Copyright (c) 2014 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Use is subject to license terms.
 */
package com.example.amazon.mw.exempli;

import java.text.SimpleDateFormat;
import java.util.Date;

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
    private static String[] mConcertLocations = {
            "Benaroya Hall", "Showbox at the Market", "Paramount Theatre", "The Triple Door",
            "Carnegie Hall", "The Moore Theatre"
    };

    // Method to return mocked concert date and concert location (static info for demo purposes).
    public ExempliResponse getUpcomingConcertData(String performer) {
        return new ExempliResponse(getConcertDate(), getConcertLocation());
    }

    // Method to return mocked concert dates.
    static String getConcertDate() {
        long newTime = System.currentTimeMillis() + 7*24*60*60*1000; // Add 7 days to current time.
        Date futureDate = new Date();
        futureDate.setTime(newTime);
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        return format.format(futureDate);
    }

    // Method to return mocked concert locations.
    static String getConcertLocation() {
        return mConcertLocations[(int) (Math.random()*5)];
    }
}
