
package abassawo.c4q.nyc.ecquo.Activities;

import android.app.AlarmManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;


import java.util.ArrayList;


import abassawo.c4q.nyc.ecquo.R;
import butterknife.Bind;
import butterknife.ButterKnife;






public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.goal_list_btn)
    Button goalBtn;
    @Bind(R.id.drawer_view)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.backdrop_img)
    ImageView backdrop;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @Bind(R.id.fab)
    View fab1;
    @Bind(R.id.cardFrame)
    SwipeFlingAdapterView deck1;


    @Bind(R.id.nav_view)
    NavigationView navigationView;



    private String TAG = "abassawo.c4q.nyc.ecquo.Activities.MainActivity";
    private ActionBarDrawerToggle mDrawerToggle;
    private ArrayList habitList;
    private ArrayList todaysTasks;
    ArrayAdapter arrayAdapter;
    AlarmManager alarmMan;

    //save our header or result
    private AccountHeader headerResult = null;
    private Drawer drawerModel = null;
    private static final int PROFILE_SETTING = 1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        loadMotivationalBackDrop();

//        if (navigationView != null) {
//            setupDrawerContent(navigationView);
//        }
        initListeners();
       // alarmMan = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE); //run in background thread or servic.


        final IProfile userProfile = new ProfileDrawerItem().withName("Abass Bayo").withNameShown(true).withEmail("100 Points").withIcon(getResources().getDrawable(R.drawable.exercise_brain));
        final IProfile hansProfile = new ProfileDrawerItem().withName("Hans");
        final IProfile joshProfile = new ProfileDrawerItem().withName("Joshelyn");


        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.background_poly)
                .addProfiles(
                        userProfile,
                        hansProfile,
                        joshProfile,
                        //don't ask but google uses 14dp for the add account icon in gmail but 20dp for the normal icons (like manage account)
//                        new ProfileSettingDrawerItem().withName("Work").withDescription("Add new Goal").withIcon(new IconicsDrawable(this).actionBarSize().paddingDp(5).colorRes(R.color.material_drawer_primary_text)),
//                        new ProfileSettingDrawerItem().withName("School").withDescription("Add new Goal").withIcon(new IconicsDrawable(this).actionBarSize().paddingDp(5).colorRes(R.color.material_drawer_primary_text)),
                        new ProfileSettingDrawerItem().withName("Coalition for Queens").withIcon(new IconicsDrawable(this).actionBarSize().paddingDp(5).colorRes(R.color.material_drawer_primary_text)
                        )

                ).withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        return false;
                    }

                })
                .withSavedInstance(savedInstanceState)
                .build();
        drawerModel = new DrawerBuilder().withActivity(this).withSliderBackgroundColor(getResources().getColor(R.color.primary_dark_material_light)).withToolbar(toolbar)
                .withAccountHeader(headerResult).addDrawerItems(
        new PrimaryDrawerItem().withName("New Goal").withIcon(getResources().getDrawable(R.drawable.ic_action_add_to_queue)).withIdentifier(1),
        new PrimaryDrawerItem().withName("New Task").withIcon(getResources().getDrawable(R.drawable.ic_alarm_add_black)).withIdentifier(2),
        new PrimaryDrawerItem().withName("Collaborators").withIcon(getResources().getDrawable(R.drawable.ic_discuss)).withIdentifier(3),
        new PrimaryDrawerItem().withName("Calendar").withIcon(getResources().getDrawable(android.R.drawable.ic_menu_my_calendar)).withIdentifier(4))
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                                                   @Override
                                                   public boolean onItemClick(View view, int i, IDrawerItem iDrawerItem) {
                                                       //check if the drawerItem is set.
                                                       //there are different reasons for the drawerItem to be null
                                                       //--> click on the header
                                                       //--> click on the footer
                                                       //those items don't contain a drawerItem
                                                       Intent intent = new Intent();
                                                       if (iDrawerItem.getIdentifier() == 1) {
                                                           intent = new Intent(MainActivity.this, GoalEditActivity.class);
                                                       } else if (iDrawerItem.getIdentifier() == 2) {
                                                           intent = new Intent(MainActivity.this, GoalListActivity.class);
                                                       } else if (iDrawerItem.getIdentifier() == 3) {
                                                           intent = new Intent(MainActivity.this, BackburnerPickerActivity.class);
                                                       } else if (iDrawerItem.getIdentifier() == 4) {
                                                           intent = new Intent(MainActivity.this, GoalDetailActivity.class);
                                                       }
                                                       if (intent != null) {
                                                           MainActivity.this.startActivity(intent);
                                                       }


                                                       return false;
                                                   }
                                               }).withSavedInstance(savedInstanceState).withShowDrawerOnFirstLaunch(true).build();



        ArrayList dayList = new ArrayList();
        dayList.add("Laundry");
        dayList.add("Groceries");
        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.habit_stack_item, R.id.title_habit, dayList);
//        deck1.setAdapter(arrayAdapter);
//
//                       deck1.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
//                                @Override
//                                public void removeFirstObjectInAdapter() {
//                                        // this is the simplest way to delete an object from the Adapter (/AdapterView)
//                                                Log.d("LIST", "removed object!");
//
//                                        arrayAdapter.notifyDataSetChanged();
//                                    }
//
//                                        @Override
//                                public void onLeftCardExit(Object o) {
//
//                                            }
//
//                                        @Override
//                                public void onRightCardExit(Object o) {
//
//                                            }
//
//                                        @Override
//                                public void onAdapterAboutToEmpty(int i) {
//                                        //Motivate the User.
//                                            }
//
//                                        @Override
//                                public void onScroll(float v) {
//
//                                            }
//
//                            });


    }



    private void initState() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);




    }

    public void initListeners(){

        fab1.setOnClickListener(this);
        goalBtn.setOnClickListener(this);



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

    private void setupDrawerModel() {
        // Create the AccountHeader

    }


//    private void setupDrawerContent(NavigationView navigationView) {
//        navigationView.setNavigationItemSelectedListener(
//                new NavigationView.OnNavigationItemSelectedListener() {
//
//
//                    @Override
//                    public boolean onNavigationItemSelected(MenuItem menuItem) {
//
//                        menuItem.setChecked(true);
//                        int id = menuItem.getItemId();
//                        switch (id) {
//                            case R.id.nav_new_goal:
//                                startActivity(  new Intent(MainActivity.this, GoalEditActivity.class) );
//                                break;
//                            case R.id.nav_new_task:
//                                break;
//                        }
//
//                        mDrawerLayout.closeDrawers();
//                        return true;
//                    }
//                });
//    }


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
            case R.id.fab: startActivity(new Intent(MainActivity.this, BackburnerPickerActivity.class));
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.nav_new_goal:
                Intent intent = new Intent(MainActivity.this, GoalEditActivity.class);
                startActivity(intent);
                break;
        }
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
