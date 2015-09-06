
package abassawo.c4q.nyc.ecquo.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;


import com.andtinder.model.CardModel;
import com.andtinder.model.Orientations;
import com.andtinder.view.CardContainer;
import com.andtinder.view.SimpleCardStackAdapter;
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


import java.util.Date;

import abassawo.c4q.nyc.ecquo.R;
import butterknife.Bind;
import butterknife.ButterKnife;






public class MainActivity extends AppCompatActivity implements View.OnClickListener, AbsListView.OnScrollListener, AbsListView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.deck1)
    CardContainer deck;
    private FragmentManager fragMan;
    @Bind(R.id.fab2)
    FloatingActionButton fabEdit;




    private String TAG = "abassawo.c4q.nyc.ecquo.Activities.MainActivity";
    private ActionBarDrawerToggle mDrawerToggle;


    //save our header or result
    private AccountHeader headerResult = null;
    private Drawer drawerModel = null;
    private static final int PROFILE_SETTING = 1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initState();
        initListeners();
        setupActionBar();
        setupNavDrawer(savedInstanceState);
        setupDayStacks(deck);

       // alarmMan = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE); //run in background thread or servic.
    }

    public void setupDayStacks(CardContainer deck){
        deck.setOrientation(Orientations.Orientation.Ordered);
        SimpleCardStackAdapter adapter = new SimpleCardStackAdapter(this);
        for(int i = 0; i < 5; i++) {
            CardModel card = new CardModel("Fix bugs", "Testing", getResources().getDrawable(R.drawable.picture1));
            CardModel card2 = new CardModel("Work on report and keep on testing stuff for this project because this is fun", "More Testing", getResources().getDrawable(R.drawable.picture2));
            adapter.add(card);
            adapter.add(card2);
        }
        deck.setAdapter(adapter);
    }

    public void setupNavDrawer(Bundle savedInstanceState){
        final IProfile abassProfile = new ProfileDrawerItem().withName("Abass Bayo").withNameShown(true).withEmail("100 Points").withIcon(getResources().getDrawable(R.drawable.exercise_brain));
        final IProfile hansProfile = new ProfileDrawerItem().withName("Hans");
        final IProfile joshProfile = new ProfileDrawerItem().withName("Joshelyn");
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.background_poly)
                .addProfiles(abassProfile, hansProfile, joshProfile,
                        //14dp for the add account icon in gmail but 20dp for the normal icons (like manage account)
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
                        new PrimaryDrawerItem().withName("New Goal").withIcon(getResources().getDrawable(R.drawable.ic_action_add_to_queue)).withIdentifier(R.id.nav_new_goal),
                        new PrimaryDrawerItem().withName("New Task").withIcon(getResources().getDrawable(R.drawable.ic_alarm_add_black)).withIdentifier(R.id.nav_new_task),
                        new PrimaryDrawerItem().withName("Collaborators").withIcon(getResources().getDrawable(R.drawable.ic_discuss)).withIdentifier(R.id.nav_collaborators),
                        new PrimaryDrawerItem().withName("Calendar").withIcon(getResources().getDrawable(android.R.drawable.ic_menu_my_calendar)).withIdentifier(R.id.nav_calendar))
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int i, IDrawerItem iDrawerItem) {

                        //switch (iDrawerItem.getIdentifier()) {
//                            case R.id.nav_new_goal: startActivity(new Intent(MainActivity.this, GoalEditActivity.class));
//                                break;
//                            case R.id.nav_new_task: startActivity(new Intent(MainActivity.this, GoalListActivity.class));
//                                break;
//                            case R.id.nav_collaborators: startActivity(new Intent(MainActivity.this, BackburnerPickerActivity.class));
//                                break;
//                            case R.id.nav_calendar: startActivity(new Intent(MainActivity.this, GoalDetailActivity.class));
                        //break;
                        //}
                        return false;
                    }
                }).withSavedInstance(savedInstanceState).withShowDrawerOnFirstLaunch(true).build();
    }



    private void initState() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        fragMan = getSupportFragmentManager();
    }

    public void initListeners(){
        fabEdit.setOnClickListener(this);

    }


    public void setupActionBar(){
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.app_name));
        Date date = new Date();
//        actionBar.setSubtitle(date.toString());
        actionBar.setIcon(R.mipmap.ic_ecquo);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDefaultDisplayHomeAsUpEnabled(false);
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_ecquo);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab2: startActivity(new Intent(MainActivity.this, TaskEditActivity.class));
        }

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


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }
}
