package abassawo.c4q.nyc.ecquo.Activities;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.wunderlist.slidinglayer.LayerTransformer;
import com.wunderlist.slidinglayer.SlidingLayer;
import com.wunderlist.slidinglayer.transformer.AlphaTransformer;
import com.wunderlist.slidinglayer.transformer.RotationTransformer;
import com.wunderlist.slidinglayer.transformer.SlideJoyTransformer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import abassawo.c4q.nyc.ecquo.Adapters.FragmentAdapter;
import abassawo.c4q.nyc.ecquo.Fragments.CalendarFragment;
import abassawo.c4q.nyc.ecquo.Fragments.DayFragment;
import abassawo.c4q.nyc.ecquo.Model.Goal;
import abassawo.c4q.nyc.ecquo.R;
import butterknife.Bind;
import butterknife.ButterKnife;



public class MainActivity extends AppCompatActivity {
//    @Bind(R.id.imageindicator)
//    ImageView image;
@Bind(R.id.nav_view)
NavigationView navigationView;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.viewpager) ViewPager viewPager;
    @Bind(R.id.slidingLayer1)
    SlidingLayer mSlidingLayer;
    @Bind(R.id.tabs)
    TabLayout tabLayout;
    public static Date todaysDate;
    private ActionBarDrawerToggle mDrawerToggle;



    AlarmManager alarmMan;


    private FragmentAdapter adapter;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initState();
        alarmMan = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        setupActionBar();
        setupDrawerBehavior();
    }

    public void setupDrawerBehavior(){

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,toolbar,R.string.openDrawer,R.string.closeDrawer){ //fixme fix the strings

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                // open I am not going to put anything here)
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }



        }; // Drawer Toggle Object Made
        mDrawerLayout.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();               // Finally we set the drawer toggle sync State

    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        int id = menuItem.getItemId();
                        switch (id) {
                            case 1:
                                id = R.id.nav_new_goal;
                                Intent intent = new Intent(MainActivity.this, NoteEditActivity.class);
                                startActivity(intent);
                                break;
                            case 2:
                                id = R.id.nav_new_task;
                                break;
                        }
                        mDrawerLayout.closeDrawers();

                        return true;
                    }
                });
    }


    public ArrayList makeTestList(){
        ArrayList testList = new ArrayList();
        String test = "Test";
        String ui = "UI";
        for (int i = 0; i < 4; i++) {
            testList.add(test);
            testList.add(ui);
        }
        return testList;
    }

    private void getAlarmIntent(){

    }

    private void initState() {
        todaysDate = new Date();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        tabLayout.setupWithViewPager(this.viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {     //Populate view pager tabs
        adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(new DayFragment(), "Today's Tasks");
        adapter.addFragment(new CalendarFragment(), "Calendar");
        viewPager.setAdapter(adapter);
    }

    public void setupActionBar(){

        String date_str = new SimpleDateFormat("EEE, MM-dd-yyyy").format(todaysDate);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.ic_launcher);
        actionBar.isHideOnContentScrollEnabled();
        getSupportActionBar().setSubtitle(date_str);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //fixme repetitive code. testing
        item.setChecked(true);
        int id = item.getItemId();
        switch (id) {
            case 1: id = R.id.nav_new_goal;
                Intent intent = new Intent(MainActivity.this, NoteEditActivity.class);
                startActivity(intent);
                break;
            case 2: id = R.id.nav_new_task;
                break;
        }
        mDrawerLayout.closeDrawers();
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


}
