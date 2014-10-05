/*
 * ExempliPlugin.java
 *
 * Copyright (c) 2014 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Use is subject to license terms.
 */
package com.example.amazon.mw.exempli;

import com.amazon.mw.entity.DigitalEntity;
import com.amazon.mw.entity.DigitalEntityFilter;
import com.amazon.mw.entity.FacetType;
import com.amazon.mw.plugin.DigitalEntityUI;
import com.amazon.mw.plugin.Resolver;
import com.amazon.mw.plugin.ResolvingPlugin;
import com.amazon.mw.plugin.PluginError;

import android.util.Log;

/**
 * This file defines a plugin service for the fictional Exempli app. This is a resolving plugin;
 * it interprets data against digital entities with video facets, calls an external service to find
 * information about related concerts close to the user's area, and adds that concert data to the
 * digital entity.
 *
 * This sample code assumes that Firefly has identified a video digital entity. It does not
 * matter exactly how the entity was identified. It could have scanned a bar code on a CD/album,
 * the cover of a CD/album, or audio from a song.
 */
public class ExempliPlugin extends ResolvingPlugin {
    // TAG used for logging.
    private static String TAG = ExempliPlugin.class.getSimpleName();
    // Performer attribute for custom facet.
    public static final String EXEMPLI_FACET_ATTRIBUTE_ADVISORY_RATING = "advisory_rating";
    public static final String EXEMPLI_FACET_ATTRIBUTE_VIDEO_TITLE = "video_title";
    public static final String EXEMPLI_FACET_ATTRIBUTE_ASIN = "asin";

    // Configure the ExempliPlugin to resolve any video identification.
    @Override
    public DigitalEntityFilter getDigitalEntityFilter() {
        return new DigitalEntityFilter().addRequiredFacets(FacetType.VIDEO);
    }

    // Create a resolver for this plugin.
    @Override
    public Resolver createDigitalEntityResolver(DigitalEntity digitalEntity) {
        return new ExempliResolver(digitalEntity);
    }

    // Define a factory method to create a DigitalEntityUI object for this plugin.
    @Override
    public DigitalEntityUI createDigitalEntityUI(DigitalEntity digitalEntity) {
        return new ExempliDigitalEntityUI(digitalEntity);
    }

    // Define an error callback in case something goes wrong; for example, the service takes
    // too long to respond and Firefly gives up.
    @Override
    public void onError(PluginError error) {
        Log.e(TAG, "Error: " + error.getMessage());
    }

    // Return a one line description of the plugin's function.
    @Override
    public String getPluginDescription() {
        return getContext().getString(R.string.description);
    }

}
