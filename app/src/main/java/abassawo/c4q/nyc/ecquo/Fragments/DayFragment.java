package abassawo.c4q.nyc.ecquo.Fragments;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.StackView;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import com.wunderlist.slidinglayer.SlidingLayer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import abassawo.c4q.nyc.ecquo.R;
import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
//todo move all sliding layer logic to this class.
public class DayFragment extends Fragment {

    ArrayList dayList;
    ArrayAdapter arrayAdapter;
    private View view;
    //@Bind(R.id.stackview)  StackView dayStack;
//    @Bind(R.id.slidingLayer1)
//    SlidingLayer mSlidingLayer;
//    @Bind(R.id.fab)
//    Button fabButton;
    private SwipeFlingAdapterView flingContainer;

    public DayFragment() {

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_day, container, false);
        ButterKnife.bind(this, view);
//        fabButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mSlidingLayer.openPreview(true); //fixme
//            }
//        });

       // RecyclerView rv = (RecyclerView)view.findViewById(R.id.day_recycler);
//        dayStack = (StackView) view.findViewById(R.id.stackview);
        flingContainer = (SwipeFlingAdapterView) view.findViewById(R.id.cardFrame);
        initState();
    //    setUpStacks();
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

            dayList.add("Prepare for Demo");
            dayList.add("Create sorting algorithm");
            dayList.add("Finish reading Android API");
            dayList.add("Find bugs");
    }




}
