
package abassawo.c4q.nyc.ecquo.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import abassawo.c4q.nyc.ecquo.Model.Planner;
import abassawo.c4q.nyc.ecquo.Model.QueryPreferences;
import abassawo.c4q.nyc.ecquo.Model.Task;
import abassawo.c4q.nyc.ecquo.Model.WakeUpService;
import abassawo.c4q.nyc.ecquo.R;
import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, AbsListView.OnScrollListener, AbsListView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.fab2)
    FloatingActionButton fabEdit;

    @Bind(R.id.empty_card_view)
    CardView emptyLayout;

    private FragmentManager fragMan;



    private String TAG = "abassawo.c4q.nyc.ecquo.Activities.MainActivity";
    private ActionBarDrawerToggle mDrawerToggle;


    //save our header or result
    private AccountHeader headerResult = null;
    private Drawer drawerModel = null;
    private static final int PROFILE_SETTING = 1;
    public static List<Task> taskList;
    public static List<Task> todayList;
    @Bind(R.id.deck1) CardContainer deck;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WakeUpService.setServiceAlarm(this, true);
        ButterKnife.bind(this);
        initState();
        initListeners();
        setupActionBar();
        setupNavDrawer(savedInstanceState);
        taskList = Planner.get(getApplicationContext()).getTasks();
        todayList = new ArrayList<>();

        setupDayStacks(deck);
//        if(!todayList.isEmpty()){
//           emptyLayout.setAlpha(0);
//        } else {
//            emptyLayout.setAlpha(1);
//        }

        emptyLayout.setAlpha(1);

       // alarmMan = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE); //run in background thread or servic.
    }

    public void setupDayStacks(CardContainer deck){
        deck.setOrientation(Orientations.Orientation.Ordered);
        SimpleCardStackAdapter adapter = new SimpleCardStackAdapter(this);
        for(Task x : taskList){
            if(x.isNotifyToday()){
                todayList.add(x);
            }
        }
        //fixme : sort the list by priority factors.
        for(int i = 0; i < todayList.size(); i++) {
            CardModel card = new CardModel(todayList.get(i).getTitle(), "Testing", getResources().getDrawable(R.drawable.mountaintop));
            adapter.add(card);

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
                        new PrimaryDrawerItem().withName("New Task").withIcon(getResources().getDrawable(R.drawable.ic_action_add_to_queue)).withIdentifier(R.id.nav_new_task),
                        new PrimaryDrawerItem().withName("Places").withIcon(getResources().getDrawable(R.drawable.ic_alarm_add_black)).withIdentifier(R.id.nav_places),
                        new PrimaryDrawerItem().withName("Collaborators").withIcon(getResources().getDrawable(R.drawable.ic_discuss)).withIdentifier(R.id.nav_collaborators),
                        new PrimaryDrawerItem().withName("Calendar").withIcon(getResources().getDrawable(android.R.drawable.ic_menu_my_calendar)).withIdentifier(R.id.nav_calendar))
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int i, IDrawerItem iDrawerItem) {

                        switch (iDrawerItem.getIdentifier()) {
                            case R.id.nav_new_task: startActivity(new Intent(MainActivity.this, EditActivity.class));
                                break;
                            case R.id.nav_places: startActivity(new Intent(MainActivity.this, TabbedEditActivity.class));

                        }
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
        //actionBar.setIcon(R.mipmap.ic_ecquo);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDefaultDisplayHomeAsUpEnabled(false);
        //actionBar.setHomeAsUpIndicator(R.mipmap.ic_ecquo);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //case R.id.fab2: startActivity(new Intent(MainActivity.this, TabbedEditActivity.class));
            case R.id.fab2: startActivity(new Intent(MainActivity.this, EditActivity.class)); //testing animation
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        switch(item.getItemId()){
//
//        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "QueryTextSubmit: " + query);
                Planner.setStoredQuery(getApplicationContext(), query);
                updateSearchQueryItems();
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
                String query = Planner.getStoredQuery(getApplicationContext());
                searchView.setQuery(query, false);
            }
        });
        return true;
    }

    private void updateSearchQueryItems(){
        //new FetchQueryTask().execute();
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
