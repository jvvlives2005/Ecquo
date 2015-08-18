package abassawo.c4q.nyc.ecquo.Activities;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
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

import abassawo.c4q.nyc.ecquo.Adapters.FragmentAdapter;
import abassawo.c4q.nyc.ecquo.Fragments.CalendarFragment;
import abassawo.c4q.nyc.ecquo.Fragments.DayFragment;
import abassawo.c4q.nyc.ecquo.R;
import butterknife.Bind;
import butterknife.ButterKnife;



public class MainActivity extends AppCompatActivity {
//    @Bind(R.id.imageindicator)
//    ImageView image;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.viewpager) ViewPager viewPager;
    @Bind(R.id.slidingLayer1)
    SlidingLayer mSlidingLayer;
    @Bind(R.id.tabs)
    TabLayout tabLayout;

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

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        setupSlidingLayerPosition();
        setupLayerOffset(true);
        setupPreviewMode(false);
        setupSlidingLayerTransform("slide");
        setupShadow(true);
        //setupShadow(prefs.getBoolean("layer_has_shadow", true));
        //setupLayerOffset(prefs.getBoolean("layer_has_offset", true));
        //setupPreviewMode(prefs.getBoolean("preview_mode_enabled", true));

        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        tabLayout.setupWithViewPager(this.viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {     //Populate view pager tabs
        adapter = new FragmentAdapter(getSupportFragmentManager());
        String date = new SimpleDateFormat("EEE, MM-dd-yyyy").format(new Date());
        adapter.addFragment(new DayFragment(), "Today");
        adapter.addFragment(new CalendarFragment(), "Calendar");
        viewPager.setAdapter(adapter);
    }

    public void setupActionBar(){
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.isHideOnContentScrollEnabled();
        actionBar.setLogo(R.mipmap.ic_launcher);
        String date = new SimpleDateFormat("EEE, MM-dd-yyyy").format(new Date());
        getSupportActionBar().setSubtitle(date);
    }

    private void setupSlidingLayerPosition() {
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) mSlidingLayer.getLayoutParams();
        int textResource;
                mSlidingLayer.setStickTo(SlidingLayer.STICK_TO_BOTTOM);
                rlp.width = android.app.ActionBar.LayoutParams.WRAP_CONTENT;
                rlp.height = getResources().getDimensionPixelSize(R.dimen.layer_size);

        mSlidingLayer.setLayoutParams(rlp);
    }

    private void setupSlidingLayerTransform(String layerTransform) {

        LayerTransformer transformer;

        switch (layerTransform) {
            case "alpha":
                transformer = new AlphaTransformer();
                break;
            case "rotation":
                transformer = new RotationTransformer();
                break;
            case "slide":
                transformer = new SlideJoyTransformer();
                break;
            default:
                return;
        }
        mSlidingLayer.setLayerTransformer(transformer);
    }

    private void setupShadow(boolean enabled) {
        if (enabled) {
            mSlidingLayer.setShadowSizeRes(R.dimen.shadow_size);
            mSlidingLayer.setShadowDrawable(R.drawable.sidebar_shadow);
        } else {
            mSlidingLayer.setShadowSize(0);
            mSlidingLayer.setShadowDrawable(null);
        }
    }

    private void setupLayerOffset(boolean enabled) {
        int offsetDistance = enabled ? getResources().getDimensionPixelOffset(R.dimen.offset_distance) : 0;
        mSlidingLayer.setOffsetDistance(offsetDistance);
    }

    private void setupPreviewMode(boolean enabled) {
        int previewOffset = enabled ? getResources().getDimensionPixelOffset(R.dimen.preview_offset_distance) : -1;
        mSlidingLayer.setPreviewOffsetDistance(previewOffset);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (mSlidingLayer.isOpened()) {
                    mSlidingLayer.closeLayer(true);
                    return true;
                }

            default:
                return super.onKeyDown(keyCode, event);
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


}
