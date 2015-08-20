package abassawo.c4q.nyc.ecquo.Fragments;
import android.graphics.drawable.Drawable;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.StackView;

import com.bumptech.glide.Glide;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

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
   // @Bind(R.id.stackview)  StackView dayStack;
   // private SwipeFlingAdapterView flingContainer;

    public DayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_day, container, false);
        ButterKnife.bind(this, view);
        loadBackdrop();
        initState();
        return view;
    }



    public void initState(){

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("Abass");

        dayList = new ArrayList<>(); //fixme sharedprefs, database, or json serializer for persistence

            dayList.add("Prepare for Demo");
            dayList.add("Create sorting algorithm");
            dayList.add("Finish reading Android API");
            dayList.add("Find bugs");

    }

    private void loadBackdrop() {
        final ImageView imageView = (ImageView) view.findViewById(R.id.backdrop);
        Glide.with(this).load(R.drawable.background_poly).centerCrop().into(imageView);
    }


}
