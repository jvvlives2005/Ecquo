package abassawo.c4q.nyc.ecquo.Model;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by c4q-Abass on 8/18/15.
 */
public class NotePad {
    private static final String TAG = "NotePad";
    private static final String FILENAME = "notes.json";

    private ArrayList<Goal> mGoals;

    public ArrayList<Goal> getGoals() {
        return mGoals;
    }
    private JSONSerializer mSerializer;

    private static NotePad sNotePad;
    private Context mAppContext;

    private NotePad(Context appContext) {
        mAppContext = appContext;
        mSerializer = new JSONSerializer(mAppContext, FILENAME);

        try {
            mGoals = mSerializer.loadGoals();
        } catch (Exception e) {
            mGoals = new ArrayList<Goal>();
            Log.e(TAG, "Error loading labels: ", e);
        }

    }

    //
    public static NotePad get(Context c) {
        if (sNotePad == null) {
            sNotePad = new NotePad(c.getApplicationContext());
        }
        return sNotePad;
    }
}

