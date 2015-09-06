package abassawo.c4q.nyc.ecquo.Activities;

import android.app.FragmentTransaction;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import abassawo.c4q.nyc.ecquo.Fragments.EditDateFragment;
import abassawo.c4q.nyc.ecquo.Fragments.EditLocationragment;
import abassawo.c4q.nyc.ecquo.Fragments.EditNameFragment;
import abassawo.c4q.nyc.ecquo.Fragments.EditPriorityFragment;
import abassawo.c4q.nyc.ecquo.R;
import butterknife.Bind;
import butterknife.ButterKnife;

public class TaskEditActivity extends AppCompatActivity {
    @Bind(R.id.viewpager)
    ViewPager viewPager;
//    @Bind(R.id.toolbar)
//    Toolbar toolbar;
    @Bind(R.id.tabs)
    TabLayout tabLayout;
    private FragmentAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_edit);
        ButterKnife.bind(this);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

    }


    private void setupViewPager(ViewPager viewPager) {
        adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(new EditNameFragment(), "NAME");
        adapter.addFragment(new EditDateFragment(), "DATE");
        adapter.addFragment(new EditPriorityFragment(), "PRIORITY");
        adapter.addFragment(SupportMapFragment.newInstance(), "LOCATIONS"); //test.
        adapter.addFragment(new EditLocationragment(), "PLACES");

        viewPager.setAdapter(adapter);
    }





    private class FragmentAdapter extends FragmentPagerAdapter{
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();



        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }


        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }


        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }


        @Override
        public int getCount() {
            return mFragments.size();
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }



    }
}
