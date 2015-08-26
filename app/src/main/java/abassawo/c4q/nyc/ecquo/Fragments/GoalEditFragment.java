package abassawo.c4q.nyc.ecquo.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import abassawo.c4q.nyc.ecquo.Adapters.SimpleStringRecyclerViewAdapter;
import abassawo.c4q.nyc.ecquo.Model.Goal;
import abassawo.c4q.nyc.ecquo.Model.NoteBook;
import abassawo.c4q.nyc.ecquo.R;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GoalEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */


public class GoalEditFragment extends Fragment{
    private static List<Goal> mGoals;
    //@Bind(R.id.listview) ListView listview;
//@Bind(R.id.image1)
//ImageView image;
    //@Bind(R.id.recyclerview) RecyclerView rv;

    public static GoalEditFragment newInstance(String param1, String param2) {
        GoalEditFragment fragment = new GoalEditFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public GoalEditFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

//    public void saveGoal() {
//        mGoal.setGoalTitle(goalEdit.getText().toString());
//        goalEdit.clearComposingText();
//        mGoals.add(mGoal);
////        Intent intent = new Intent(EditActivity.this, GoalListEditActivity.class);
////        startActivity(intent);
//        Log.d("goal update", "goals size:" + mGoals.size() + "mGoals empty?" + mGoals.isEmpty());
//
//
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_pane, container);

        ButterKnife.bind(this, view);

       // listview.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, mGoals));


        //setupRecyclerView(rv);
        return view;
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btn_speak_now:  //speak button when text is empty
//                startVoiceRecognitionActivity();
//                break;
//            case R.id.goal_deadline_btn:
//                FragmentManager fm = getSupportFragmentManager();
//                TimePickerFragment dialog = TimePickerFragment.newInstance(mGoal.getDateCreated());
//                dialog.setTargetFragment(new android.support.v4.app.Fragment(), REQUEST_TIME);
//                dialog.show(fm, DIALOG_TIME);
//        }
//    }


}
