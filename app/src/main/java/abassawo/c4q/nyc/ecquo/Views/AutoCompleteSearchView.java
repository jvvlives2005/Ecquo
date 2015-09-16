//package abassawo.c4q.nyc.ecquo.Views;
//
//import android.content.Context;
//import android.support.v4.widget.CursorAdapter;
//import android.support.v7.widget.SearchView;
//import android.util.AttributeSet;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.AutoCompleteTextView;
//
///**
// * Created by c4q-Abass on 9/15/15.
// */
//public class AutoCompleteSearchView extends SearchView {
//
//    private AutoCompleteTextView mSearchAutoComplete;
//
//    public AutoCompleteSearchView(Context context) {
//        super(context);
//        initialize();
//    }
//
//    public AutoCompleteSearchView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        initialize();
//    }
//
//    public void initialize() {
//        mSearchAutoComplete = (AutoCompleteTextView) findViewById(getResources().getIdentifier("android:id/search_src_text", null, null));
//        setAutoCompleSuggestionsAdapter(null);
//        setOnItemClickListener(null);
//    }
//
//    @Override
//    public void setSuggestionsAdapter(CursorAdapter adapter) {
//        throw new UnsupportedOperationException("Please use setAutoCompleSuggestionsAdapter(ArrayAdapter<?> adapter) instead");
//    }
//
//    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
//        mSearchAutoComplete.setOnItemClickListener(listener);
//    }
//
//    public void setAutoCompleSuggestionsAdapter(ArrayAdapter<?> adapter) {
//        mSearchAutoComplete.setAdapter(adapter);
//    }
//
//    public void setText(String text) {
//        mSearchAutoComplete.setText(text);
//    }
//
//}