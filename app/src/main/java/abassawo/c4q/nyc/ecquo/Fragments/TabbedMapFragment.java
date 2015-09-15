package abassawo.c4q.nyc.ecquo.Fragments;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

import abassawo.c4q.nyc.ecquo.Adapters.AutoCompleteAdapter;
import abassawo.c4q.nyc.ecquo.R;
import butterknife.Bind;
import butterknife.ButterKnife;


public class TabbedMapFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    private LatLng searchedLocation;
    private Geocoder geocoder;
    private AutoCompleteAdapter mAdapter;
    private String TAG = "abassawo.c4q.nyc.ecquo.Fragments.TabbedMapFragment";
    private GoogleApiClient mClient;
    private GoogleMap map;
    @Bind(R.id.fab_searchButton)
    ImageButton searchButton;

    // TODO: Rename and change types and number of parameters
    public static TabbedMapFragment newInstance() {
        TabbedMapFragment fragment = new TabbedMapFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public TabbedMapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        geocoder = new Geocoder(getActivity());
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        //mapFragment.getMapAsync(this);
    }

    protected synchronized void buildGoogleApiClient(Context context) {
        mClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
        Log.d("Map", "Connected to Google API Client");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tabbed_map, container, false);
        ButterKnife.bind(this, v);
        //setupSearchFunction();

        return v;
    }

//    public void setupSearchFunction(){
//        searchButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!isEmpty(searchField)){
//                    String addressStr = searchField.getText().toString();
//                    try{
//                        searchedLocation = getLatLngFromAddress(addressStr);
//                        Log.d(searchedLocation.toString(), "Location Test");
//                        setViewToLocation(searchedLocation); //FIXME
//                        ArrayAdapter resultsAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, getCoordListfromSearch(addressStr));
//                        resultsLV.setAdapter(resultsAdapter);
//                    } catch(Exception e){
//
//                    }
//
//
//
//                }
//
//            }
//        });
//
//    }

    public boolean isEmpty(EditText edittext){
        return edittext.getText().length() == 0;
    }

    private void setViewToLocation(LatLng latLng) {
        if (map != null) {
            // Set initial view to current location
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16.0f));
        }
    }





    private AdapterView.OnItemClickListener mAutocompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final AutoCompleteAdapter.PlaceFilter item = mAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            Log.i(TAG, "Autocomplete item selected: " + item.description);

            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(mClient, placeId);
            placeResult.setResultCallback(new ResultCallback<PlaceBuffer>() {
                @Override
                public void onResult(PlaceBuffer places) {
                    if (!places.getStatus().isSuccess()) {
                        // Request did not complete successfully
                        Log.e(TAG, "Place query did not complete. Error: " + places.getStatus().toString());
                        places.release();
                        return;
                    }
                    places.release();
                }
            });
            Log.i(TAG, "Called getPlaceById to get Place details for " + item.placeId);

            LatLng search_location = getLatLngFromAddress(item.toString());
            setViewToLocation(search_location);
        }
    };


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
    public void onConnected(Bundle bundle) {
        Location location = LocationServices.FusedLocationApi.getLastLocation(mClient);
        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mClient, createLocationRequest(), new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    //setViewToLocation(new LatLng(location.getLatitude(), location.getLongitude()));
                }
            });
        }

        // setViewToLocation(new LatLng(location.getLatitude(), location.getLongitude()));

    }


    private LocationRequest createLocationRequest() {
        return new LocationRequest()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setNumUpdates(1);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(getActivity().getApplicationContext(), "Connection Failed", Toast.LENGTH_SHORT).show();
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */


}