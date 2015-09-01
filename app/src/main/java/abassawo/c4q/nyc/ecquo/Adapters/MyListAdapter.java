package abassawo.c4q.nyc.ecquo.Adapters;

/**
 * Created by c4q-Abass on 8/31/15.
 */
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.nhaarman.listviewanimations.ArrayAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo.UndoAdapter;


import abassawo.c4q.nyc.ecquo.Model.Goal;
import abassawo.c4q.nyc.ecquo.Model.Planner;
import abassawo.c4q.nyc.ecquo.Model.Task;
import abassawo.c4q.nyc.ecquo.R;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.nhaarman.listviewanimations.ArrayAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo.UndoAdapter;


import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;


public class MyListAdapter extends ArrayAdapter<String> implements UndoAdapter, StickyListHeadersAdapter {


    private final Context mContext;
    private List<Goal> mGoals;


    public MyListAdapter(final Context context) {
        mContext = context;
//        for (int i = 0; i < 100; i++) {
//            add(mContext.getString(R.string.row_number, i));
//        }
      mGoals = Planner.get(mContext).getGoals();
        Goal goal = new Goal("Exercise");
        Goal goal2 = new Goal("Test");
        goal.addtoTaskList(new Task("testing"));
        for (int i = 0; i < 100; i++){
            mGoals.add(goal);
            mGoals.add(goal2);
            mGoals.add(new Goal("android_test"));
        }
        for (int i = 0; i < mGoals.size(); i++) {
            add(new Goal().getTaskList().toString());
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
        TextView view = (TextView) convertView;
        if (view == null) {
            view = (TextView) LayoutInflater.from(mContext).inflate(R.layout.list_row, parent, false);
        }

        view.setText(mGoals.get(position).toString()); //fixme

        return view;
    }


    @NonNull
    @Override
    public View getUndoView(final int position, final View convertView, @NonNull final ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.undo_row, parent, false);
        }
        return view;
    }


    @NonNull
    @Override
    public View getUndoClickView(@NonNull final View view) {
        return view.findViewById(R.id.undo_row_undobutton);
    }


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