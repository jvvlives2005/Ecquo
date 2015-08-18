package abassawo.c4q.nyc.ecquo.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import abassawo.c4q.nyc.ecquo.R;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GoalMenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */


public class GoalMenuFragment extends Fragment{
//@Bind(R.id.image1)
//ImageView image;

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
        View view = inflater.inflate(R.layout.fragment_goal, container, false);
        ButterKnife.bind(this, view);
        ArrayList goals = new ArrayList();
        String exercise = "exercise";
        String nutrition = "nutrition";
        String brainTraining = "brain training";
        goals.add(exercise);
        goals.add("demo");
        goals.add("test");
        //image.setImageResource(R.drawable.exercise_brain);

        ListView lv = (ListView) view.findViewById(R.id.goal_list_view);
        lv.setAdapter(new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, goals));

        return view;
    }

}
