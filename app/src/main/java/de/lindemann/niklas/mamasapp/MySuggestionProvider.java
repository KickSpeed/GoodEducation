package de.lindemann.niklas.mamasapp;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by Niklas on 01.10.2015.
 */
public class MySuggestionProvider extends SearchRecentSuggestionsProvider{
    public final static String AUTHORITY = "de.lindemann.niklas.MySuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;
    public MySuggestionProvider() {
        setupSuggestions(AUTHORITY,MODE);

    }

}
