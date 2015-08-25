package abassawo.c4q.nyc.ecquo.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import abassawo.c4q.nyc.ecquo.Model.Goal;
import abassawo.c4q.nyc.ecquo.Model.NoteBook;
import abassawo.c4q.nyc.ecquo.R;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by c4q-Abass on 8/18/15.
 */
public class EditActivity extends AppCompatActivity implements View.OnClickListener {
    public static List<Goal> mGoals;

    @Bind(R.id.edit_goal_title)
    EditText goalEdit;
    @Bind(R.id.goal_deadline_btn)
    Button deadlineBtn;
    @Bind(R.id.btn_speak_now)
    ImageButton speakButton;
    @Bind(R.id.note_imageView)
    ImageView noteImage;
    private String TAG = "abassawo.c4q.nyc.ecquo.Activities.NoteEditActivity";
    private static final int REQUEST_CODE = 1;
    private Goal mGoal;


    private static final String DIALOG_DATE = "date";
    private static final String DIALOG_TIME = "time";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_edit);
        ButterKnife.bind(this);

        initState();
        initListeners();
        if (isEmpty(goalEdit)) {
            speakButton.setImageResource(android.R.drawable.ic_btn_speak_now);
        } else {
            speakButton.setImageResource(android.R.drawable.ic_input_add);
        }
//        UUID goalId = (UUID)getArguments().getSerializable(EXTRA_GOAL_ID);
        mGoal = new Goal(); //fixme todo = create id in MainActivity, throw it in a extra. deliver it here.
//        mGoal = NotePad.get(getActivity()).getNote(noteId);

    }

    public void initState() {
        mGoals = NoteBook.get(this).getGoals();

    }

    public boolean isEmpty(final EditText editText) { //check if edittext is blank and toggle listeners.
        if (editText.getText().toString().trim().length() == 0) {
            speakButton.setOnClickListener(this);
            return true;
        }
        speakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveGoal();

            }
        });
        return false;
    }

    public void initListeners() {
        deadlineBtn.setOnClickListener(this);
        goalEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mGoal.setGoalTitle(s.toString());
                if (!isEmpty(goalEdit)) {
                    speakButton.setImageResource(android.R.drawable.ic_input_add);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(EditActivity.this, MainActivity.class));
    }


    private void startVoiceRecognitionActivity() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Enter new Note");
        startActivityForResult(intent, REQUEST_CODE);
    }

    public void saveGoal() {
        mGoal.setGoalTitle(goalEdit.getText().toString());
        goalEdit.clearComposingText();
        mGoals.add(mGoal);
        Intent intent = new Intent(EditActivity.this, GoalListActivity.class);
        startActivity(intent);
        Log.d("goal update", "goals size:" + mGoals.size() + "mGoals empty?" + mGoals.isEmpty());


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            if (matches.size() > 0) {
                Log.d(TAG, matches.get(0));
                goalEdit.setText(matches.get(0));  //set the text to the first result.


            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_speak_now:  //speak button when text is empty
                startVoiceRecognitionActivity();
                break;
            case R.id.goal_deadline_btn:
                FragmentManager fm = getSupportFragmentManager();
                TimePickerFragment dialog = TimePickerFragment.newInstance(mGoal.getDateCreated());
                dialog.setTargetFragment(new Fragment(), REQUEST_TIME);
                dialog.show(fm, DIALOG_TIME);
        }
    }
}

