package abassawo.c4q.nyc.ecquo.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import abassawo.c4q.nyc.ecquo.Model.Goal;
import abassawo.c4q.nyc.ecquo.Model.Planner;
import abassawo.c4q.nyc.ecquo.R;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by c4q-Abass on 8/18/15.
 */
public class GoalEditActivity extends AppCompatActivity implements View.OnClickListener {


    @Bind(R.id.edit_goal_title) EditText goalEdit;
    @Bind(R.id.note_imageView)
    ImageView noteImage;
    private String TAG = "abassawo.c4q.nyc.ecquo.Activities.NoteEditActivity";
    private static final int REQUEST_CODE = 1;
    private List<Goal> mGoals;
    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_edit);
        ButterKnife.bind(this);
        mGoals = Planner.get(getApplicationContext()).getGoals();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(GoalEditActivity.this, MainActivity.class));
    }

//    private void startVoiceRecognitionActivity() {
//        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
//                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Enter new Note");
//        startActivityForResult(intent, REQUEST_CODE);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            if (matches.size() > 0) {
                Log.d(TAG, matches.get(0));
                goalEdit.setText(matches.get(0));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_speak_now:
                if(goalEdit.getText().length()>0) {
                    String goal_str = goalEdit.getText().toString();
                    Goal goal = new Goal(goal_str);
                    mGoals.add(goal);
                    Log.d(mGoals.toString(), TAG);
                    Intent intent = new Intent(GoalEditActivity.this, GoalDetailActivity.class);
                    intent.putExtra("Goal title", goal.getGoalTitle());
                    startActivity(intent);
                }
                break;

        }
    }
}
