//package abassawo.c4q.nyc.ecquo.Adapters;
//
///**
// * Created by c4q-Abass on 9/15/15.
// */
//import java.util.ArrayList;
//import java.util.List;
//
//import android.app.Activity;
//import android.content.Context;
//import android.database.Cursor;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.CursorAdapter;
//import android.widget.Filter;
//
//import abassawo.c4q.nyc.ecquo.Model.Task;
//import abassawo.c4q.nyc.ecquo.Model.sPlanner;
//
//public class SuggestionsAdapter extends CursorAdapter {
//
//    protected static final String TAG = "SuggestionAdapter";
//    private List<String> suggestions;
//
//    public SuggestionsAdapter(Activity context) {
//        // super(context, R.layout.search_layout);
//        super(context, android.R.layout.simple_dropdown_item_1line);
//        suggestions = new ArrayList<String>();
//    }
//
//    @Override
//    public int getCount() {
//        return suggestions.size();
//    }
//
//    @Override
//    public String getItem(int index) {
//        return suggestions.get(index);
//    }
//
//    @Override
//    public View newView(Context context, Cursor cursor, ViewGroup parent) {
//        return null;
//    }
//
//    @Override
//    public void bindView(View view, Context context, Cursor cursor) {
//
//    }
//
//    @Override
//    public Filter getFilter() {
//        Filter myFilter = new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence constraint) {
//                FilterResults filterResults = new FilterResults();
//
//                if (constraint != null) {
//                    // A class that queries a web API, parses the data and
//                    // returns an ArrayList<GoEuroGetSet>
//                    List<Task> new_suggestions = sPlanner.get(getContext()).getTasks();
//
//                    suggestions.clear();
//                    for (int i = 0; i < new_suggestions.size(); i++) {
//                        suggestions.add(new_suggestions.get(i).getTitle());
//                    }
//
//                    // Now assign the values and count to the FilterResults
//                    // object
//                    filterResults.values = suggestions;
//                    filterResults.count = suggestions.size();
//                }
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence contraint,
//                                          FilterResults results) {
//                if (results != null && results.count > 0) {
//                    notifyDataSetChanged();
//                } else {
//                    notifyDataSetInvalidated();
//                }
//            }
//        };
//        return myFilter;
//    }
//
//}