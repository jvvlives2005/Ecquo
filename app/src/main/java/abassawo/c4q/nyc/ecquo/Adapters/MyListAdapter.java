package abassawo.c4q.nyc.ecquo.Adapters;

/**
 * Created by c4q-Abass on 8/31/15.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;




import com.nhaarman.listviewanimations.ArrayAdapter;






import abassawo.c4q.nyc.ecquo.Model.Goal;
import abassawo.c4q.nyc.ecquo.Model.sPlanner;
import abassawo.c4q.nyc.ecquo.Model.Task;
import abassawo.c4q.nyc.ecquo.R;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;


import java.util.ArrayList;
import java.util.List;


public class MyListAdapter extends ArrayAdapter<String> implements  StickyListHeadersAdapter {








    private final Context mContext;
    private List<Goal> mGoals;








    public MyListAdapter(final Context context) {
        mContext = context;
//        for (int i = 0; i < 100; i++) {
//            add(mContext.getString(R.string.row_number, i));
//        }
        mGoals = sPlanner.get(mContext).getGoals();
        Goal goal = new Goal("Work");
        Goal goal2 = new Goal("School");
        goal2.add(new Task("Final Project"));
        for (int i = 0; i < 100; i++){
            mGoals.add(goal);
            mGoals.add(goal2);
            mGoals.add(new Goal("fix bugs"));
        }


        ArrayList sampleTasks = new ArrayList();
        sampleTasks.add("fix animations");
        sampleTasks.add("set up navigation drawer.");
        for (int i = 0; i < 20; i++) {
            add(mGoals.get(i).toString());
        }
    }








    @Override
    public long getItemId(final int position) {
        return mGoals.get(position).getId();
    }








    @Override
    public boolean hasStableIds() {
        return true;
    }








    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View view = convertView;
        //= convertView;
        if (view == null) {
            view = (View) LayoutInflater.from(mContext).inflate(R.layout.list_row, parent, false);
            TextView row_tv = (TextView) view.findViewById(R.id.list_row_draganddrop_textview);
            row_tv.setText(mGoals.get(position).toString()); //fixme
        }




        return view;
    }








//    @NonNull
//    @Override
//    public View getUndoView(final int position, final View convertView, @NonNull final ViewGroup parent) {
//        View view = convertView;
//        if (view == null) {
//            view = LayoutInflater.from(mContext).inflate(R.layout.undo_row, parent, false);
//        }
//        return view;
//    }
//
//
//    @NonNull
//    @Override
//    public View getUndoClickView(@NonNull final View view) {
//        return view.findViewById(R.id.undo_row_undobutton);
//    }








    @Override
    public View getHeaderView(final int position, final View convertView, final ViewGroup parent) {
        TextView view = (TextView) convertView;
        if (view == null) {
            view = (TextView) LayoutInflater.from(mContext).inflate(R.layout.list_header, parent, false);
        }


        view.setText(mGoals.get(position).toString());


        return view;
    }






    @Override
    public long getHeaderId(final int position) {
        return position / 10;
    }
}