package abassawo.c4q.nyc.ecquo.Adapters;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filterable;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.maps.model.LatLngBounds;

/**
 * Created by c4q-Abass on 9/13/15.
 */
public class SearchFilterAdapter extends ArrayAdapter<SearchFilterAdapter.PlaceFilter> implements Filterable {
    private GoogleApiClient mGoogleApiClient;
    private LatLngBounds mBounds;
    private AutocompleteFilter mPlaceFilter;

    public SearchFilterAdapter(Context context, int resource, GoogleApiClient googleApiClient,
                                    LatLngBounds bounds, AutocompleteFilter filter) {
        super(context, resource);
        mGoogleApiClient = googleApiClient;
        mBounds = bounds;
        mPlaceFilter = filter;
    }


    // Holder for Places Geo Data Autocomplete API results.
    public class PlaceFilter {

        public CharSequence placeId;
        public CharSequence description;

        PlaceFilter(CharSequence placeId, CharSequence description) {
            this.placeId = placeId;
            this.description = description;
        }

        @Override
        public String toString() {
            return description.toString();
        }
    }
}
