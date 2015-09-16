//package abassawo.c4q.nyc.ecquo.Adapters;
//
///**
// * Created by c4q-Abass on 8/31/15.
// */
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//
//
//
//import com.nhaarman.listviewanimations.ArrayAdapter;
//
//
//
//
//
//
//import abassawo.c4q.nyc.ecquo.Model.Goal;
//import abassawo.c4q.nyc.ecquo.Model.sPlanner;
//import abassawo.c4q.nyc.ecquo.Model.Task;
//import abassawo.c4q.nyc.ecquo.R;
//import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
//
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class MyListAdapter extends android.widget.ArrayAdapter<Task> {
//    private List<Task> mTasks = sPlanner.get(getContext()).getTasks();
//    public int getCount() {
//        return mTasks.size();
//    }
//
//    @Override
//    public Task getItem(int position) {
//
//        return mTasks.get(position);
//    }
//
//    public MyListAdapter(List<Task> notes) {
//        super(getContext(), 0, notes);
//    }
//
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        LayoutInflater inflater = LayoutInflater.from(getContext());
//        if (convertView == null) {
//            convertView = inflater.inflate(R.layout.task_list_activity, parent, false); //try list_item_note nxt
//        }
//
//        // Configure for this note
//       Task c = getItem(position);
//
//        TextView titleTextView = (TextView) convertView.findViewById(R.id.note_list_title);
//        if (c != null) {
////                titleTextView.setText(c.getTitle());
//        }
//
//
//        TextView dateTextView = (TextView) convertView.findViewById(R.id.note_list_item_dateTV);
//
////            if (c != null) {
////                CharSequence cs = DateFormat.format("EEEE, MMM dd, yyyy", c.getDate());
////                dateTextView.setText(cs);
////            }
//
//
//        // CheckBox solvedCheckedBox = (CheckBox) convertView.findViewById(R.id.note_list_item_CheckBox);
////            solvedCheckedBox.setChecked(c.isSolved());
//        return convertView;
//    }
//}