/*
 * ExempliResolver.java
 *
 * Copyright (c) 2014 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Use is subject to license terms.
 */
package com.example.amazon.mw.exempli;

import com.amazon.mw.entity.DigitalEntity;
import com.amazon.mw.entity.Facet;
import com.amazon.mw.entity.FacetType;
import com.amazon.mw.entity.MusicFacet;
import com.amazon.mw.entity.Contributor;
import com.amazon.mw.plugin.Resolver;
import com.example.amazon.mw.exempli.ExempliClient.ExempliResponse;

import android.util.Log;

import java.util.List;

/**
 * Defines a resolver for the demo service Exempli. It consumes a digital entity with a
 * music facet, and calls an external service to search for related concerts in the area.
 */
public class ExempliResolver extends Resolver {
    // TAG used for logging.
    private static final String TAG = ExempliResolver.class.getSimpleName();

    /**
     * Construct a Resolver that consumes a digitalEntity.
     */
    public ExempliResolver(DigitalEntity de) {
        super(de);
    }

    /**
     * Resolve a given digitalEntity.
     *
     * Takes a Music Facet's performer, and mocks additional concert data.
     */
    @Override
    public Facet resolve() {
        Log.v(TAG, "Resolving de: " + getDigitalEntity().toString());

        // Get the MusicFacet from the digital entity. It should never be null,
        // as it was declared as required in ExempliPlugin.getDigitalEntityFilter.
        MusicFacet musicFacet = getDigitalEntity().getFacet(FacetType.MUSIC);
        String performers = getPerformerNames(musicFacet.getPerformers());

        // Call the mocked external service.
        ExempliClient exClient = new ExempliClient();
        final ExempliResponse exResponse = exClient.getUpcomingConcertData(performers);

        // If the service didn't find any related concerts, then don't extend the identification.
        if (!exResponse.foundConcert()) {
            return null;
        }

        // Found a related concert. Create an ExempliFacet object and get properties from it.
        // The ExempliDigitalEntityUI object will use this facet to display a label.
        Facet exempliFacet = new Facet();
        exempliFacet.saveAttribute(ExempliPlugin.EXEMPLI_FACET_ATTRIBUTE_PERFORMER, performers);
        exempliFacet.saveAttribute(ExempliPlugin.EXEMPLI_FACET_ATTRIBUTE_CONCERT_DATE, exResponse.getConcertDate());
        exempliFacet.saveAttribute(ExempliPlugin.EXEMPLI_FACET_ATTRIBUTE_CONCERT_LOCATION, exResponse.getConcertLocation());

        return exempliFacet;
    }

    /**
     * Takes a list of performers and formats them in a easily readable String.
     */
    private String getPerformerNames(List<Contributor> performers) {
        String performerNames = new String();
        for (Contributor contributor : performers) {
            performerNames += contributor.getName() + " ";
        }
        Log.d(TAG, "Performer Names: " + performerNames);
        return performerNames;
    }
}
