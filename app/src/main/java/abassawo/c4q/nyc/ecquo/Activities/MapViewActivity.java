package abassawo.c4q.nyc.ecquo.Activities;


import android.content.Intent;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import abassawo.c4q.nyc.ecquo.Adapters.FragAdapter;
import abassawo.c4q.nyc.ecquo.Adapters.PlaceAutoCompleteAdapter;
import abassawo.c4q.nyc.ecquo.Fragments.TabbedMapFragment;
import abassawo.c4q.nyc.ecquo.Model.sPlanner;
import abassawo.c4q.nyc.ecquo.R;
import butterknife.Bind;
import butterknife.ButterKnife;


public class MapViewActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    protected GoogleApiClient mGoogleApiClient;
    private ArrayList savedLocations;
    private FragAdapter adapter;
    private Geocoder geocoder;
    private LatLng searchedLocation;
    @Bind(R.id.viewpager) ViewPager viewpager;
    @Bind(R.id.current_location_textview)
    TextView currLocationTV;
    @Bind(R.id.autocompleteTV)
    AutoCompleteTextView acTextView;
    @Bind(R.id.places_listview) ListView listview;
    private static String TAG = "MapViewActivity";
    private PlaceAutoCompleteAdapter mAdapter;
    private LatLng searchLocation;
    private TextView mPlaceDetailsText;
    private TextView mPlaceDetailsAttribution;
    private static final LatLngBounds BOUNDS = new LatLngBounds(
            new LatLng(40.498425, -74.250219), new LatLng(40.792266, -73.776434));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGoogleApiClient= new GoogleApiClient.Builder(this)
                .enableAutoManage(this, 0 /* clientId */, this)
                .addApi(Places.GEO_DATA_API)
                .build();

        //savedLocations = sPlanner.get(getApplicationContext()).getSavedLocations();
        if(savedLocations == null){
            savedLocations = new ArrayList();
        }
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        geocoder = new Geocoder(getApplicationContext());
        setupViewPager(viewpager);


        acTextView.setOnItemClickListener(mAutocompleteClickListener);

        mAdapter = new PlaceAutoCompleteAdapter(this, android.R.layout.simple_list_item_1,
                mGoogleApiClient, BOUNDS, null);
        acTextView.setAdapter(mAdapter);
        acTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    searchLocation = getLatLngFromAddress(acTextView.getText().toString());
                    currLocationTV.setText(searchLocation.toString());
                    savedLocations.add(searchLocation.toString());
                    listview.setAdapter(new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, savedLocations));
                    handled = true;
                }
                return handled;
            }
        });


    }



    private void setupViewPager(ViewPager viewPager) {
        adapter = new FragAdapter(getSupportFragmentManager());
        adapter.addFragment(new TabbedMapFragment(), "New Location");
        //adapter.addFragment(new PlaceListFragment(), "Saved Locations");
        viewPager.setAdapter(adapter);
    }









    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    public LatLng getLatLngFromAddress(String address){
        LatLng coordFound = null;
        try {
            Address location = getCoordListfromSearch(address).get(0);
            coordFound = new LatLng(location.getLatitude(), location.getLongitude());
        } catch (IOException e) {
            e.printStackTrace();
        }


        return coordFound;


    }




    public List<Address> getCoordListfromSearch(String address) throws IOException { //for more results that can be set to an adapter
        List<Address> addressList = geocoder.getFromLocationName(address, 5);
        return addressList;
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_map_activity, menu);
        MenuItem searchItem = menu.findItem(R.id.map_item_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "QueryTextSubmit: " + query);
                sPlanner.setStoredQuery(getApplicationContext(), query);
                updateSearchQueryItems();


                try{
                    searchedLocation = getLatLngFromAddress(query);
                    Log.d(searchedLocation.toString(), "Location Test");
                    //setViewToLocation(searchedLocation); //FIXME
                    List<Address> results = getCoordListfromSearch(query);
                    ArrayList addressStrList = new ArrayList();
                    for(Address x : results){
                        addressStrList.add(x);
                    }
                    ArrayAdapter resultsAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, addressStrList);
                } catch(Exception e){


                }
                return false;
            }


            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = sPlanner.getStoredQuery(getApplicationContext());
                searchView.setQuery(query, false);
            }
        });
        return true;
    }


    private void updateSearchQueryItems(){
        //new FetchQueryTask().execute();
    }

    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            /*
             Retrieve the place ID of the selected item from the Adapter.
             The adapter stores each Place suggestion in a PlaceAutocomplete object from which we
             read the place ID.
              */
            final PlaceAutoCompleteAdapter.PlaceAutocomplete item = mAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            Log.i(TAG, "Autocomplete item selected: " + item.description);


            /*
             Issue a request to the Places Geo Data API to retrieve a Place object with additional
              details about the place.
              */
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);

            currLocationTV.setText(item.description);
            //TESTING
            Intent sendLocation = new Intent(MapViewActivity.this, EditActivity.class);
            sendLocation.putExtra("LOCATION", item.description);
            Log.d(TAG, item.description.toString());
            startActivityForResult(sendLocation,EditActivity.REQUEST_LOCATION);
            //savedLocations.add(item.description);


            Toast.makeText(getApplicationContext(), "Clicked: " + item.description,
                    Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Called getPlaceById to get Place details for " + item.placeId);
        }
    };

    /**
     * Callback for results from a Places Geo Data API query that shows the first place result in
     * the details view on screen.
     */
    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                // Request did not complete successfully
                Log.e(TAG, "Place query did not complete. Error: " + places.getStatus().toString());
                places.release();
                return;
            }
            // Get the Place object from the buffer.
            final Place place = places.get(0);


            // Format details of the place for display and show it in a TextView.

//            mPlaceDetailsText.setText(formatPlaceDetails(getResources(), place.getName(), //Buggy
//                    place.getId(), place.getAddress(), place.getPhoneNumber(),
//                    place.getWebsiteUri()));




            // Display the third party attributions if set.
            final CharSequence thirdPartyAttribution = places.getAttributions();
            if (thirdPartyAttribution == null) {
                // mPlaceDetailsAttribution.setVisibility(View.GONE);
            } else {
                mPlaceDetailsAttribution.setVisibility(View.VISIBLE);
                mPlaceDetailsAttribution.setText(Html.fromHtml(thirdPartyAttribution.toString()));
            }


            Log.i(TAG, "Place details received: " + place.getName()); //catch the result here. forward back to edit activity. finalize the tasks' informations.


            places.release();
        }
    };

    private static Spanned formatPlaceDetails(Resources res, CharSequence name, String id,
                                              CharSequence address, CharSequence phoneNumber, Uri websiteUri) {
//        Log.e(TAG, res.getString(R.string.place_details, name, id, address, phoneNumber,
//                websiteUri));
        return Html.fromHtml(res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri));
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}