package abassawo.c4q.nyc.ecquo.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import abassawo.c4q.nyc.ecquo.Adapters.SimpleStringRecyclerViewAdapter;
import abassawo.c4q.nyc.ecquo.Model.Goal;
import abassawo.c4q.nyc.ecquo.Model.Note;
import abassawo.c4q.nyc.ecquo.Model.NotePad;
import abassawo.c4q.nyc.ecquo.R;
import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GoalMenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */


public class GoalMenuFragment extends Fragment{
    private static List<Goal> mGoals;
//@Bind(R.id.image1)
//ImageView image;
    //@Bind(R.id.recyclerview) RecyclerView rv;

    public static GoalMenuFragment newInstance(String param1, String param2) {
        GoalMenuFragment fragment = new GoalMenuFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public GoalMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mGoals =  NotePad.get(getActivity()).getGoals();
        View view = inflater.inflate(R.layout.fragment_goal, container, false);
        ButterKnife.bind(this, view);


        mGoals.add(new Goal("Exercise"));
        mGoals.add(new Goal("Reading"));
        mGoals.add(new Goal("Code"));


        //setupRecyclerView(rv);
        return view;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(getActivity(), mGoals));

    }

}
