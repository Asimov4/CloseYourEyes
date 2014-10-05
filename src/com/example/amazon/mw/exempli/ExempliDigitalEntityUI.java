/*
 * ExempliDigitalEntityUI.java
 *
 * Copyright (c) 2014 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Use is subject to license terms.
 */
package com.example.amazon.mw.exempli;

import android.content.Intent;

import com.amazon.mw.entity.DigitalEntity;
import com.amazon.mw.entity.Facet;
import com.amazon.mw.entity.FacetType;
import com.amazon.mw.plugin.DigitalEntityUI;
import com.amazon.mw.plugin.Label;
import com.amazon.mw.plugin.SimpleLabel;

import java.util.ArrayList;

// Define the DigitalEntityUI class for the Exempli plugin.
public class ExempliDigitalEntityUI extends DigitalEntityUI {
    // Generate a DigitalEntityUI given a digitalEntity.
    public ExempliDigitalEntityUI(DigitalEntity digitalEntity) {
        super(digitalEntity);
    }

    /**
     * Create a label for a given DigitalEntity.
     *
     * This method gets a Facet that contains concert location and date data for an identified performer.
     */
    @Override
    public Label getLabel() {
        SimpleLabel label = new SimpleLabel();
        Facet facet = getVideoInfo();

        String concertInfo = getContext().getString(R.string.concert_label,
                facet.getAttribute(ExempliPlugin.EXEMPLI_FACET_ATTRIBUTE_VIDEO_TITLE),
                facet.getAttribute(ExempliPlugin.EXEMPLI_FACET_ATTRIBUTE_ADVISORY_RATING));

        // Populate a label with the attributes of a given digitalEntity.
        label.setExperienceDescriptor("Advisory rating: " + facet.getAttribute(ExempliPlugin.EXEMPLI_FACET_ATTRIBUTE_ADVISORY_RATING));

        return label;
    }

    // Define an onClick() intent to send data to the demo activity defined in ExempliClient.java.
    @Override
    public void onClick() {
        // Create the text to send to the Activity.
        Facet facet = getVideoInfo();

        String concertInfo = getContext().getString(R.string.concert_label,
                facet.getAttribute(ExempliPlugin.EXEMPLI_FACET_ATTRIBUTE_VIDEO_TITLE),
                facet.getAttribute(ExempliPlugin.EXEMPLI_FACET_ATTRIBUTE_ADVISORY_RATING));

        // Call the mocked external service.
        ExempliClient exClient = new ExempliClient();
        ArrayList<String> guides = exClient.getImdbData(facet.getAttribute(ExempliPlugin.EXEMPLI_FACET_ATTRIBUTE_ASIN));

        // Create an intent to send the concert info to the ExempliActivity.
        Intent sendConcert = new Intent(getContext(), ExempliActivity.class);
        // Ensure that this activity is marked as new, bringing it to focus.
        sendConcert.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sendConcert.putExtra(Intent.EXTRA_TEXT, concertInfo);
        sendConcert.putExtra("guides", guides);

        // Start the ExempliActivity.
        getContext().startActivity(sendConcert);
    }

    /**
     * Get the performer name from the identified ExempliFacet, from which
     * the concert attributes can be acquired.
     *
     * These attributes are set in the ExempliResolver.
     */
    private Facet getVideoInfo() {
        Facet facet = getDigitalEntity().getFacet(FacetType.MY_FACET);
        return facet;
    }
}
