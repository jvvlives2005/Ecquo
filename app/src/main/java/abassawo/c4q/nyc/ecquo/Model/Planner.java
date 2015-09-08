package abassawo.c4q.nyc.ecquo.Model;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by c4q-Abass on 8/18/15.
 */
public class Planner{
    private static final String TAG = "NotePad";
    private static final String FILENAME = "notes.json";
    private static ArrayList<Task>mTasks;
    private ArrayList<Goal> mGoals;

    public ArrayList<Goal> getGoals() {
        return mGoals;
    }

    public ArrayList<Task> getTasks(){
        return mTasks;
    }
    private JSONSerializer mSerializer;

    private static Planner sPlanner;
    private Context mAppContext;

    private Planner(Context appContext) {
        mAppContext = appContext;
        mSerializer = new JSONSerializer(mAppContext, FILENAME);

        try {
            mGoals = mSerializer.loadGoals();
            mTasks = mSerializer.loadTasks();
        } catch (Exception e) {
            mGoals = new ArrayList<Goal>();
            Log.e(TAG, "Error loading labels: ", e);
        }

    }

    //
    public static Planner get(Context c) {
        if (sPlanner == null) {
            sPlanner = new Planner(c.getApplicationContext());
        }
        return sPlanner;
    }
}

