package abassawo.c4q.nyc.ecquo.Fragments;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

//import com.google.android.gms.plus.Plus;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

import abassawo.c4q.nyc.ecquo.R;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by c4q-Abass on 9/4/15.
 */
public class EcquoMapFragment extends SupportMapFragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks{
    private MapView mMapView;
    private GoogleMap mMap;
    private LatLng mCurrentLocation;
    private LatLng mLastLocation;
    private GoogleApiClient googleLogInClient;
    private static Context ctx;
    private Geocoder geocoder;
    private Marker marker;
    private double lat, lng;

    AutoCompleteTextView searchField;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        searchField = (AutoCompleteTextView) view.findViewById(R.id.search_location_box);
        initListeners();
        return view;
    }

    public void initListeners(){
        searchField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    mCurrentLocation = getLatLng(searchField.getText().toString());
                    setMapPin(mCurrentLocation);

                }
                return true;
            }
        });
    }

    public static EcquoMapFragment newInstance(){
        return new EcquoMapFragment();
    }

    public void initState(){
        lat = 40.756296;
        lng = -73.923944;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        initState();
        ctx = getActivity().getApplicationContext();
        buildGoogleMapClient(ctx);
        Geocoder geocoder = new Geocoder(ctx, Locale.getDefault());
        getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                initMap(mMap);

            }
        });
    }



    public void initMap(GoogleMap map){
        map.setMyLocationEnabled(true);
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        try {
            Location location = locationManager.getLastKnownLocation(provider);
            Log.d(location.toString(), "test location" );
        } catch(Exception e){  //fixme

        }
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL); //Choose type of map, normal, terrain, satellite, none
        marker = map.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title("MOMI"));
        LatLng homePoint = new LatLng(lat, lng);    //fixme
        LatLng workPoint = new LatLng(100, -11); //fixme
        //LatLngBounds latBounds = new LatLngBounds.Builder().include()

        LatLngBounds bounds = new LatLngBounds.Builder().include(homePoint).include(workPoint).build();
        int margin = getResources().getDimensionPixelSize(R.dimen.map_inset_margin);
        Marker newmarker = map.addMarker(new MarkerOptions().position(homePoint).title("marker title").icon(BitmapDescriptorFactory.fromResource(R.drawable.main_screen_rocket)));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(homePoint).zoom(14.0f).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        map.moveCamera(cameraUpdate);
        //setupMapView();
    }


    public void buildGoogleMapClient(Context ctx){
        googleLogInClient = new GoogleApiClient.Builder(ctx)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addApi(LocationServices.API).addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {
                        getActivity().invalidateOptionsMenu();
                        //mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(googleLogInClient);
                        //mLastLocation = LocationServices.FusedLocationApi.getLastLocation(googleLogInClient);
                        //Log.d(mLastLocation.toString(), "Location Test");
                        if (mLastLocation != null) {
//                            mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
//                            mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
                        } else {
//
                        }
                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                })
                .build();


    }



    @Override
    public void onStart() {
        super.onStart();
        getActivity().invalidateOptionsMenu();
        googleLogInClient.connect();
    }


    @Override
    public void onStop() {
        super.onStop();
        googleLogInClient.disconnect();
    }

    public LatLng getLatLng(String strAddress) {
        Geocoder coder = new Geocoder(getActivity());
        List<Address> address;
        LatLng coordinates = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            coordinates = new LatLng(location.getLatitude(), location.getLongitude());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return coordinates;
    }




    @Override
    public void onConnected(Bundle bundle) {
        mCurrentLocation = new LatLng(lat, lng);
                //LocationServices.FusedLocationApi.getLastLocation(googleLogInClient);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.getUiSettings().setTiltGesturesEnabled(true);
        mMap.getUiSettings().setScrollGesturesEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        mMap.setMyLocationEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//        mMap.setOnMapClickListener(mapClickListener);
//        mMap.setOnMarkerClickListener(markerClickListener);
    }

    private void setMapPin(LatLng latLng) {
        if (mMap != null) {
            // Set initial view to current location
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16.0f));
        }
    }

}







