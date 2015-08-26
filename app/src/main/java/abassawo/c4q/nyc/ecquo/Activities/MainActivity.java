
package abassawo.c4q.nyc.ecquo.Activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import com.ogaclejapan.arclayout.ArcLayout;

import java.util.ArrayList;
import java.util.List;


import abassawo.c4q.nyc.ecquo.Model.AnimatorUtils;
import abassawo.c4q.nyc.ecquo.Model.Goal;
import abassawo.c4q.nyc.ecquo.Model.NoteBook;
import abassawo.c4q.nyc.ecquo.R;
import butterknife.Bind;
import butterknife.ButterKnife;






public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static List<Goal>mGoals;
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


    @Bind(R.id.nav_view)
    NavigationView navigationView;


    boolean dailyHabitsDone;
    private String TAG = "abassawo.c4q.nyc.ecquo.Activities.MainActivity";
    private ActionBarDrawerToggle mDrawerToggle;
    private ArrayList habitList;
    private ArrayList todaysTasks;
    ArrayAdapter arrayAdapter;
    AlarmManager alarmMan;





    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
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
        mGoals = NoteBook.get(this).getGoals();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);


        habitList = new ArrayList<>(); //fixme sharedprefs, database, or json serializer for p
        habitList.add("Meditate");
        habitList.add("Exercise");
        habitList.add("Read More");
        habitList.add("Practice Gratitude");

        dailyHabitsDone = habitList.isEmpty();
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

    }

    public void initListeners(){

        fab1.setOnClickListener(this);
       // goalBtn.setOnClickListener(this);
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
                            case 1: id = R.id.nav_new_goal;
                                Intent intent = new Intent(MainActivity.this, GoalListEditActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_new_task:
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

//    public void loadHabitstoForm_BackDrop(){
//        //set the listener and the adapter
//
//        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.habit_stack_item, R.id.title, habitList);
//        flingContainer.setAdapter(arrayAdapter);
//
//
//        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
//            @Override
//            public void removeFirstObjectInAdapter() {
//                // this is the simplest way to delete an object from the Adapter (/AdapterView)
//                Log.d("LIST", "removed object!");
//                habitList.remove(0);
//                arrayAdapter.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void onLeftCardExit(Object o) {
//            }
//
//
//            @Override
//            public void onRightCardExit(Object o) {
//            }
//
//
//            @Override
//            public void onAdapterAboutToEmpty(int i) {
//                //Motivate the User.
//            }
//
//
//            @Override
//            public void onScroll(float v) {
//
//            }
//
//
//        });
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                startActivity(new Intent(MainActivity.this, GoalListEditActivity.class));
                break;
            case R.id.new_picture_button:
                //showPictureDialog;
                break;
            case R.id.goal_list_btn:
                startActivity(new Intent(MainActivity.this, GoalListEditActivity.class));
                break;

            default:break;
        }

    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //fixme repetitive code. testing 
        item.setChecked(true);
        int id = item.getItemId();
        switch (id) {
            case 1: id = R.id.nav_new_goal;
                Intent intent = new Intent(MainActivity.this, GoalListEditActivity.class);
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
