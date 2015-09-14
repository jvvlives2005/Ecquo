package abassawo.c4q.nyc.ecquo.Activities;

import android.location.Address;
import android.location.Geocoder;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import abassawo.c4q.nyc.ecquo.Adapters.FragAdapter;
import abassawo.c4q.nyc.ecquo.Fragments.PlaceListFragment;
import abassawo.c4q.nyc.ecquo.Fragments.TabbedMapFragment;
import abassawo.c4q.nyc.ecquo.Model.sPlanner;
import abassawo.c4q.nyc.ecquo.R;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MapViewActivity extends AppCompatActivity {
    private String TAG = "MapActiivity";
    private FragAdapter adapter;
    private Geocoder geocoder;
    private LatLng searchedLocation;
    @Bind(R.id.viewpager) ViewPager viewpager;
    @Bind(R.id.search_results_lv)
    ListView resultsLV;

//    @Bind(R.id.tabs)
//    TabLayout tabLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        geocoder = new Geocoder(getApplicationContext());

        ButterKnife.bind(this);
        setupViewPager(viewpager);
        setupActionBar();
       // tabLayout.setupWithViewPager(viewpager);

    }

    public void setupActionBar(){
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        //actionBar.setHomeAsUpIndicator(R.mipmap.ic_ecquo);
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
                        resultsLV.setAdapter(resultsAdapter);
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

}
