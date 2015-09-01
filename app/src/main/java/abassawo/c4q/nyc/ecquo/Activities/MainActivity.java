
package abassawo.c4q.nyc.ecquo.Activities;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import com.ogaclejapan.arclayout.ArcLayout;

import java.util.ArrayList;


import abassawo.c4q.nyc.ecquo.R;
import butterknife.Bind;
import butterknife.ButterKnife;






public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.goal_list_btn)
    Button goalBtn;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.backdrop_img)
    ImageView backdrop;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @Bind(R.id.fab)
    View fab1;
    @Bind(R.id.arc_layout)
    ArcLayout arcLayout;
    @Bind(R.id.menu_layout)
    FrameLayout menuLayout;
    @Bind(R.id.nav_view)
    NavigationView navigationView;
    @Bind(R.id.habitFrame)
    SwipeFlingAdapterView flingContainer;

    boolean dailyHabitsDone;
    private String TAG = "abassawo.c4q.nyc.ecquo.Activities.MainActivity";
    private ActionBarDrawerToggle mDrawerToggle;
    private ArrayList habitList;
    private ArrayList todaysTasks;
    ArrayAdapter arrayAdapter;
    AlarmManager alarmMan;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initState();
        initListeners();
        //alarmMan = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE); //run in background thread or servic.
        setupActionBar();
        setupDrawerBehavior();
        loadMotivationalBackDrop();


    }



    private void initState() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        habitList = new ArrayList<>(); //fixme sharedprefs, database, or json serializer for p
        habitList.add("Meditate");
        habitList.add("Exercise");
        habitList.add("Read More");
        habitList.add("Practice Gratitude");




        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

    }

    public void initListeners(){
        for (int i = 0, size = arcLayout.getChildCount(); i < size; i++) {
            arcLayout.getChildAt(i).setOnClickListener(this);
        }
        dailyHabitsDone = habitList.isEmpty();

        fab1.setOnClickListener(this);
        goalBtn.setOnClickListener(this);
        flingContainer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO:
                Intent intent = new Intent(MainActivity.this, BackburnerListActivity.class);
                intent.putExtra("id for intent", "test"); //fixme
                startActivity(intent);

            }
        });

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
    private void loadMotivationalBackDrop(){
        Glide.with(MainActivity.this).load(R.drawable.do_it_now).centerCrop().into(backdrop);
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


    public void setupActionBar(){
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.ic_ecquo);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDefaultDisplayHomeAsUpEnabled(false);
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_ecquo);

        collapsingToolbar.setTitle(getResources().getString(R.string.app_name));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab: startActivity(new Intent(MainActivity.this, GoalListActivity.class));
        }

    }
}
