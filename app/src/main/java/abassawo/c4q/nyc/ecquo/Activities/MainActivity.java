package abassawo.c4q.nyc.ecquo.Activities;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

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

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.viewpager) ViewPager viewPager;

    @Bind(R.id.tabs)
    TabLayout tabLayout;
    public static Date todaysDate;



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
        actionBar.setSubtitle(date_str);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
