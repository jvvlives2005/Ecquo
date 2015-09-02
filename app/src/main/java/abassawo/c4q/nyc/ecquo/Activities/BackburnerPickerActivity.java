package abassawo.c4q.nyc.ecquo.Activities;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.nhaarman.listviewanimations.appearance.StickyListHeadersAdapterDecorator;
import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.nhaarman.listviewanimations.util.StickyListHeadersListViewWrapper;

import abassawo.c4q.nyc.ecquo.Adapters.MyListAdapter;
import abassawo.c4q.nyc.ecquo.R;
import butterknife.Bind;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class BackburnerPickerActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.today_button)
    Button todayButton;
    @Bind(R.id.tomorrow_button) Button tomorrowButton;
    @Bind(R.id.location_button) Button locationButton;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_list);

        StickyListHeadersListView listView = (StickyListHeadersListView) findViewById(R.id.goals_to_pick_from_lv);
        listView.setFitsSystemWindows(true);

        MyListAdapter adapter = new MyListAdapter(this);
        AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter(adapter);
        StickyListHeadersAdapterDecorator stickyListHeadersAdapterDecorator = new StickyListHeadersAdapterDecorator(animationAdapter);
        stickyListHeadersAdapterDecorator.setListViewWrapper(new StickyListHeadersListViewWrapper(listView));

        assert animationAdapter.getViewAnimator() != null;
        animationAdapter.getViewAnimator().setInitialDelayMillis(500);

        assert stickyListHeadersAdapterDecorator.getViewAnimator() != null;
        stickyListHeadersAdapterDecorator.getViewAnimator().setInitialDelayMillis(500);

        listView.setAdapter(stickyListHeadersAdapterDecorator);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.today_button:
            Snackbar.make(null, "This is main activity", Snackbar.LENGTH_LONG)
                    .setAction("CLOSE", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    })
                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                    .show();
        }
    }
}
