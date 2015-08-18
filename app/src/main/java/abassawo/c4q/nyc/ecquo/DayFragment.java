package abassawo.c4q.nyc.ecquo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.StackView;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
//todo move all sliding layer logic to this class.
public class DayFragment extends Fragment {
    ArrayList dayList;
    ArrayAdapter arrayAdapter;
    private View view;
    private StackView dayStack;
    private SwipeFlingAdapterView flingContainer;

    public DayFragment() {

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day, container, false);
        dayStack = (StackView) view.findViewById(R.id.stackview);
        flingContainer = (SwipeFlingAdapterView) view.findViewById(R.id.cardFrame);
        initState();
        setUpStacks();
        setUpFlingContainer();
        return view;
    }

    public void setUpFlingContainer(){

//choose your favorite adapter
        arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.item, R.id.title, dayList);

        //set the listener and the adapter
        flingContainer.setAdapter(arrayAdapter);

        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                dayList.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object o) {

            }

            @Override
            public void onRightCardExit(Object o) {

            }

            @Override
            public void onAdapterAboutToEmpty(int i) {
                //Motivate the User.
            }

            @Override
            public void onScroll(float v) {

            }

        });
    }

    public void initState(){

        dayList = new ArrayList<>(); //fixme sharedprefs, database, or json serializer for persistence
        //for (int i = 0; i < 5; i++) {
            dayList.add("Prepare for Demo");
            dayList.add("Create sorting algorithm");
            dayList.add("Finish reading Android API");
            dayList.add("Find bugs");

//        }

    }


    public void setUpStacks(){
        ArrayAdapter arrayAdapter2 = new ArrayAdapter<String>(getActivity(), R.layout.day_stack_item, R.id.title, dayList);
        dayStack.setAdapter(arrayAdapter2);

    }


}
