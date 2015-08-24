package abassawo.c4q.nyc.ecquo.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

import abassawo.c4q.nyc.ecquo.Activities.NoteEditActivity;
import abassawo.c4q.nyc.ecquo.Model.Goal;
import abassawo.c4q.nyc.ecquo.Model.Note;
import abassawo.c4q.nyc.ecquo.R;

/**
 * Created by c4q-Abass on 8/18/15.
 */
public class SwipableRecyclerAdapter extends RecyclerView.Adapter<SwipableRecyclerAdapter.ViewHolder> {

    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;
    List<Goal> mGoals = new ArrayList();



    public static class ViewHolder extends RecyclerView.ViewHolder {
        public Note mBoundString;

        public final View mView;
        public final ImageView mImageView;
        public final TextView mTextView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = (ImageView) view.findViewById(R.id.avatar);
            mTextView = (TextView) view.findViewById(R.id.text1);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTextView.getText();
        }
    }

    public Goal getValueAt(int position) {

        return (Goal) mGoals.get(position);
    }

    public SwipableRecyclerAdapter(Context context, List<Goal> items) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;
        mGoals = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_goals, parent, false);
        view.setBackgroundResource(mBackground);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mBoundString = mGoals.get(position);
            holder.mTextView.setText(mGoals.get(position).toString());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, NoteEditActivity.class);
                // intent.putExtra(NoteEditActivity.EXTRA_NAME, holder.mBoundString.toString());

                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {

        return mGoals.size();
    }
}
