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
import com.amazon.mw.entity.VideoFacet;
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
        VideoFacet videoFacet = getDigitalEntity().getFacet(FacetType.VIDEO);
        String advisoryRating = videoFacet.getAdvisoryRating();

        // Found a related concert. Create an ExempliFacet object and get properties from it.
        // The ExempliDigitalEntityUI object will use this facet to display a label.
        Facet exempliFacet = new Facet();
        exempliFacet.saveAttribute(ExempliPlugin.EXEMPLI_FACET_ATTRIBUTE_ADVISORY_RATING, advisoryRating);
        exempliFacet.saveAttribute(ExempliPlugin.EXEMPLI_FACET_ATTRIBUTE_VIDEO_TITLE, videoFacet.getTitle());
        exempliFacet.saveAttribute(ExempliPlugin.EXEMPLI_FACET_ATTRIBUTE_ASIN, videoFacet.getAIVAsin());

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
