package abassawo.c4q.nyc.ecquo.Model;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by c4q-Abass on 9/9/15.
 */


//These methods can be moved to Planner Class.
public class QueryPreferences {
    private static final String PREF_SEARCH_QUERY = "searchQuery";

    public static String getStoredQuery(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(PREF_SEARCH_QUERY, null);
    }

    public static void setStoredQuery(Context context, String query) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(PREF_SEARCH_QUERY, query).apply();
    }

}
