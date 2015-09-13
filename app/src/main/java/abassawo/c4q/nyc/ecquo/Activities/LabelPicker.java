package abassawo.c4q.nyc.ecquo.Activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.Date;

import abassawo.c4q.nyc.ecquo.R;
import butterknife.Bind;

/**
 * Created by c4q-Abass on 9/13/15.
 */
public class LabelPicker extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label_list);
        setupActionBar();
    }

    public void setupActionBar(){
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.app_name));
        Date date = new Date();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        //actionBar.setHomeAsUpIndicator(R.mipmap.ic_ecquo);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //todo
        return super.onOptionsItemSelected(item);
    }
}
