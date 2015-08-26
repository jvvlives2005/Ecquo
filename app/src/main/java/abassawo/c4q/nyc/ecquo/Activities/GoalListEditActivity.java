package abassawo.c4q.nyc.ecquo.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import java.util.List;
import java.util.UUID;
import abassawo.c4q.nyc.ecquo.Model.Goal;
import abassawo.c4q.nyc.ecquo.Model.NoteBook;
import abassawo.c4q.nyc.ecquo.R;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by c4q-Abass on 8/18/15.
 */
public class GoalListEditActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.toolbar)
    Toolbar toolbar;


    private FragmentManager fragmentManager;
    public static List<Goal> mGoals;

    private String TAG = "abassawo.c4q.nyc.ecquo.Activities.NoteEditActivity";
    private static final int REQUEST_CODE = 1;
    private Goal mGoal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_edit);
        ButterKnife.bind(this);

        initState();
        initListeners();
        UUID testGoalID = new UUID(20, 100);

    }

    public void initState() {
        mGoals = NoteBook.get(this).getGoals();
        fragmentManager = getSupportFragmentManager();
        //if the intent has a new note extra {   load edit fragment.

        //if tasks are currently selected, show control fragment - do today, tomorrow, next week, or locartion

        //if a goal is currently selected, show control fragment for goals - deadline, priority setting, and location associated.
    }


    public void initListeners() {

    }

    public void setupActionBar(){
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.ic_ecquo);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDefaultDisplayHomeAsUpEnabled(false);
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_ecquo);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(GoalListEditActivity.this, MainActivity.class));
    }


    private void startVoiceRecognitionActivity() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Enter new Note");
        startActivityForResult(intent, REQUEST_CODE);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}

