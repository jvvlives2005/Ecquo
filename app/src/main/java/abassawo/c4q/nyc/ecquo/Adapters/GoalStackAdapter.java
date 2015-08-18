package abassawo.c4q.nyc.ecquo.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import abassawo.c4q.nyc.ecquo.R;

/**
 * Created by c4q-Abass on 8/17/15.
 */
public class GoalStackAdapter extends BaseAdapter {
    private Context context;
    Integer[] images = {

    };

    public GoalStackAdapter(Context c) {
        context = c;
    }

    public int getCount() {
        return images.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View view, ViewGroup
            parent) {
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(
                            Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.item, null, false);
        }
        return view;
    }
}
