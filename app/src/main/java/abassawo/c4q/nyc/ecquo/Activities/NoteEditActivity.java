package abassawo.c4q.nyc.ecquo.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.UUID;

import abassawo.c4q.nyc.ecquo.R;
import butterknife.Bind;

/**
 * Created by c4q-Abass on 8/18/15.
 */
public class NoteEditActivity extends AppCompatActivity {


    @Bind(R.id.edit_note_msg) EditText msgEdit;
    @Bind(R.id.note_imageView)
    ImageView noteImage;

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
