package abassawo.c4q.nyc.ecquo.Activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
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
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;


import com.bumptech.glide.Glide;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import com.ogaclejapan.arclayout.ArcLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import abassawo.c4q.nyc.ecquo.Adapters.FragmentAdapter;
import abassawo.c4q.nyc.ecquo.Fragments.CalendarFragment;
import abassawo.c4q.nyc.ecquo.Model.AnimatorUtils;
import abassawo.c4q.nyc.ecquo.Model.Goal;
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




    private FragmentAdapter adapter;
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
        loadHabitstoForm_BackDrop();

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
                Intent intent = new Intent(MainActivity.this, HabitEditActivity.class);
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

        collapsingToolbar.setTitle(getResources().getString(R.string.app_name));
    }

    public void loadHabitstoForm_BackDrop(){
        //set the listener and the adapter

        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.item, R.id.title, habitList);
        flingContainer.setAdapter(arrayAdapter);


        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                habitList.remove(0);
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onLeftCardExit(Object o) {
            }


            @Override
            public void onRightCardExit(Object o) {
            }


            @Override
            public void onAdapterAboutToEmpty(int i) {
                //Motivate the User.
            }


            @Override
            public void onScroll(float v) {

            }


        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                if (v.isSelected()) {
                    hideMenu();
                } else {
                    showMenu();
                }
                v.setSelected(!v.isSelected());
                break;
            case R.id.new_note_button:
                startActivity(new Intent(MainActivity.this, NoteEditActivity.class));
                break;
            case R.id.new_picture_button:
                //showPictureDialog;
                break;
            case R.id.new_task_button:
                // startActivity(new Intent(MainActivity.this, NoteEditActivity.class));
                break;
            case R.id.goal_list_btn:
                startActivity(new Intent(MainActivity.this, GoalListActivity.class));
                break;
            case R.id.habitFrame:
                startActivity(new Intent(MainActivity.this, HabitEditActivity.class));
            default:break;
        }

    }

    private void showMenu() {
        menuLayout.setVisibility(View.VISIBLE);

        List<Animator> animList = new ArrayList<>();

        for (int i = 0, len = arcLayout.getChildCount(); i < len; i++) {
            animList.add(createShowItemAnimator(arcLayout.getChildAt(i)));
        }

        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(400);
        animSet.setInterpolator(new OvershootInterpolator());
        animSet.playTogether(animList);
        animSet.start();
    }
    private void hideMenu() {

        List<Animator> animList = new ArrayList<>();

        for (int i = arcLayout.getChildCount() - 1; i >= 0; i--) {
            animList.add(createHideItemAnimator(arcLayout.getChildAt(i)));
        }

        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(400);
        animSet.setInterpolator(new AnticipateInterpolator());
        animSet.playTogether(animList);
        animSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                menuLayout.setVisibility(View.INVISIBLE);
            }
        });
        animSet.start();

    }
    private Animator createHideItemAnimator(final View item) {
        float dx = fab1.getX() - item.getX();
        float dy = fab1.getY() - item.getY();

        Animator anim = ObjectAnimator.ofPropertyValuesHolder(
                item,
                AnimatorUtils.rotation(720f, 0f),
                AnimatorUtils.translationX(0f, dx),
                AnimatorUtils.translationY(0f, dy)
        );

        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                item.setTranslationX(0f);
                item.setTranslationY(0f);
            }
        });

        return anim;
    }
    private Animator createShowItemAnimator(View item) {

        float dx = fab1.getX() - item.getX();
        float dy = fab1.getY() - item.getY();

        item.setRotation(0f);
        item.setTranslationX(dx);
        item.setTranslationY(dy);

        Animator anim = ObjectAnimator.ofPropertyValuesHolder(
                item,
                AnimatorUtils.rotation(0f, 720f),
                AnimatorUtils.translationX(dx, 0f),
                AnimatorUtils.translationY(dy, 0f)
        );

        return anim;
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