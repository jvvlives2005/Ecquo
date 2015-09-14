package abassawo.c4q.nyc.ecquo.Activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import abassawo.c4q.nyc.ecquo.Adapters.FragAdapter;
import abassawo.c4q.nyc.ecquo.Fragments.PlaceListFragment;
import abassawo.c4q.nyc.ecquo.Fragments.TabbedMapFragment;
import abassawo.c4q.nyc.ecquo.R;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MapActivity extends AppCompatActivity {
    private String TAG = "MapActiivity";
    private FragAdapter adapter;

    @Bind(R.id.viewpager) ViewPager viewpager;
    @Bind(R.id.tabs)
    TabLayout tabLayout;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        setupViewPager(viewpager);
        tabLayout.setupWithViewPager(viewpager);

    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new FragAdapter(getSupportFragmentManager());
        adapter.addFragment(new TabbedMapFragment(), "New Location");
        adapter.addFragment(new PlaceListFragment(), "Saved Locations");
        viewPager.setAdapter(adapter);
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_map_activity, menu);
        MenuItem searchItem = menu.findItem(R.id.location_item_search);
        return true;
    }


}
