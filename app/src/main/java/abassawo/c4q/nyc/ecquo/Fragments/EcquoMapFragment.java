package abassawo.c4q.nyc.ecquo.Fragments;


import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
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
public class EcquoMapFragment extends SupportMapFragment implements GoogleApiClient.ConnectionCallbacks, OnMapReadyCallback {
    private MapView mMapView;
    private GoogleMap mMap;
    private LatLng mCurrentLocation;
    private LatLng mLastLocation;
    private GoogleApiClient googleLogInClient;
    private static Context ctx;
    private Geocoder geocoder;
    private Marker marker;
    private double lat, lng;
    private View view;
    private LocationManager locationManager;
    private static final long MIN_TIME = 400;
    private static final float MIN_DISTANCE = 1000;


    AutoCompleteTextView searchField;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = super.onCreateView(inflater, container, savedInstanceState);
        initState();
        mCurrentLocation = new LatLng(lat, lng);
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);


//        if (checkLocationPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for Activity#requestPermissions for more details.
//            return TODO;
//        }
        try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {


                }


                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {


                }


                @Override
                public void onProviderEnabled(String provider) {


                }


                @Override
                public void onProviderDisabled(String provider) {


                }
            });
        } catch (SecurityException e){


        }


        return view;
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
        getMapAsync(this);




    }


    private void setMapPin(LatLng coordinates) {
        if (mMap != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 16.0F ));
        }
    }


    public void initMap(GoogleMap map){
        map.setMyLocationEnabled(true);
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL); //Choose type of map, normal, terrain, satellite, none
        marker = map.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title("MOMI"));


        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        try {
            Location location = locationManager.getLastKnownLocation(provider);
            Log.d(location.toString(), "test location");
            if(location != null) {
                setMapPin(new LatLng(location.getLatitude(), location.getLongitude()));
            } else{
                setMapPin(new LatLng(lat, lng)); //default placeholder map location.
            }
        } catch(Exception e){  //fixme


        }




        LatLng homePoint = new LatLng(lat, lng);    //fixme
        LatLng workPoint = new LatLng(100, -11); //fixme
        //LatLngBounds latBounds = new LatLngBounds.Builder().include()


        LatLngBounds bounds = new LatLngBounds.Builder().include(homePoint).include(workPoint).build();
        int margin = getResources().getDimensionPixelSize(R.dimen.map_inset_margin);
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
    public void onMapReady(GoogleMap map) {
        mMap = map;
        map.getUiSettings().setMapToolbarEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setCompassEnabled(true);
        map.getUiSettings().setMapToolbarEnabled(true);
        map.getUiSettings().setTiltGesturesEnabled(true);
        map.getUiSettings().setScrollGesturesEnabled(true);
        map.getUiSettings().setRotateGesturesEnabled(true);
        map.setMyLocationEnabled(true);
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

//        map.setOnMapClickListener(mapClickListener);
//        map.setOnMarkerClickListener(markerClickListener);
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                //mMap.setMyLocationEnabled(true);
                //mMap.animateCamera(CameraUpdateFactory.zoomTo(13));
                initMap(mMap);
            }




        });






    }




}






