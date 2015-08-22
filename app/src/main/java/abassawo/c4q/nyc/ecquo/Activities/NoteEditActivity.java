package abassawo.c4q.nyc.ecquo.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.UUID;

import abassawo.c4q.nyc.ecquo.R;

/**
 * Created by c4q-Abass on 8/18/15.
 */
public class NoteEditActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(NoteEditActivity.this, MainActivity.class));
    }
}
