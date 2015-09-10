package abassawo.c4q.nyc.ecquo.Activities;

import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.maps.SupportMapFragment;
import com.truizlop.fabreveallayout.FABRevealLayout;
import com.truizlop.fabreveallayout.OnRevealChangeListener;

import java.util.ArrayList;
import java.util.List;

import abassawo.c4q.nyc.ecquo.Fragments.EditDateFragment;
import abassawo.c4q.nyc.ecquo.Fragments.EditNameFragment;
import abassawo.c4q.nyc.ecquo.Fragments.EditPriorityFragment;
import abassawo.c4q.nyc.ecquo.R;
import butterknife.Bind;
import butterknife.ButterKnife;

public class TabbedEditActivity extends AppCompatActivity {
    @Bind(R.id.fab_reveal_layout_test)
    FABRevealLayout fabRevealLayout;
    @Bind(R.id.fab_reveal_button)
    FloatingActionButton fabRevealButton;
    @Bind(R.id.edit_task_title)
    EditText titleField;
//    @Bind(R.id.viewpager)
//    ViewPager viewPager;
//    @Bind(R.id.toolbar)
//    Toolbar toolbar;
//    @Bind(R.id.tabs)
//    TabLayout tabLayout;
//    private FragmentAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_edit);
        ButterKnife.bind(this);
        configureFABReveal(fabRevealLayout);
        titleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               // revealFabifTextPresent();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });



//        setupViewPager(viewPager);
//        tabLayout.setupWithViewPager(viewPager);

    }

    public void revealFabifTextPresent(){
        if(titleField.getText().length()>0){
            fabRevealButton.setVisibility(View.VISIBLE);
        }
    }

    private void configureFABReveal(FABRevealLayout fabRevealLayout) {
        fabRevealLayout.setOnRevealChangeListener(new OnRevealChangeListener() {
            @Override
            public void onMainViewAppeared(FABRevealLayout fabRevealLayout, View mainView) {
                //showMainViewItems();
            }

            @Override
            public void onSecondaryViewAppeared(final FABRevealLayout fabRevealLayout, View secondaryView) {
                //showSecondaryViewItems();

                secondaryView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        prepareBackTransition(fabRevealLayout);
                    }
                });
            }
        });
    }

    private void prepareBackTransition(final FABRevealLayout fabRevealLayout) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fabRevealLayout.revealMainView();
            }
        }, 2000);
    }



//    private void setupViewPager(ViewPager viewPager) {
//        adapter = new FragmentAdapter(getSupportFragmentManager());
//        adapter.addFragment(new EditNameFragment(), "NAME");
//        adapter.addFragment(new EditDateFragment(), "DATE");
//        adapter.addFragment(new EditPriorityFragment(), "PRIORITY");
//        adapter.addFragment(SupportMapFragment.newInstance(), "LOCATIONS"); //test.
//        //adapter.addFragment(new EditLocationragment(), "PLACES");
//
//        viewPager.setAdapter(adapter);
//    }
//
//
//
//
//
//    private class FragmentAdapter extends FragmentPagerAdapter{
//        private final List<Fragment> mFragments = new ArrayList<>();
//        private final List<String> mFragmentTitles = new ArrayList<>();
//
//
//
//        public FragmentAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//
//        public void addFragment(Fragment fragment, String title) {
//            mFragments.add(fragment);
//            mFragmentTitles.add(title);
//        }
//
//
//        @Override
//        public Fragment getItem(int position) {
//            return mFragments.get(position);
//        }
//
//
//        @Override
//        public int getCount() {
//            return mFragments.size();
//        }
//
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return mFragmentTitles.get(position);
//        }
//
//
//
//    }
}
