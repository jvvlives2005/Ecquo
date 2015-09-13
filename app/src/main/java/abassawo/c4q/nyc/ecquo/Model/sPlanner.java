package abassawo.c4q.nyc.ecquo.Model;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by c4q-Abass on 8/18/15.
 * Singleton Class for tracking state throughout app.
 */
public class sPlanner {
    private static Date todaysDate;
    private static final String TAG = "NotePad";
    private static final String FILENAME = "notes.json";
    private static ArrayList<Task>mTasks;
    private Date tomorrowsDate;
    private Date weekfromToday;

    private ArrayList<Goal> mGoals;
    public ArrayList<Goal> getGoals() {
        return mGoals;
    }
    public ArrayList<Task> getTasks(){

        return mTasks;
    }



    public List<Task>getTodaysTasks(){
        List<Task> todayList = new ArrayList<Task>();
        for(Task x : mTasks){
            if(x.isNotifyToday()){
                todayList.add(x);
            }
        }
        return todayList;
    }
    private JSONSerializer mSerializer;

    private static abassawo.c4q.nyc.ecquo.Model.sPlanner sSPlanner;
    private Context mAppContext;

    private sPlanner(Context appContext) {
        initDate();
        mAppContext = appContext;
        mSerializer = new JSONSerializer(mAppContext, FILENAME);

        Log.d(TAG + "date", todaysDate.toString());

        try {
            mGoals = mSerializer.loadGoals();
            mTasks = mSerializer.loadTasks();
        } catch (Exception e) {
            mGoals = new ArrayList<Goal>();
            Log.e(TAG, "Error loading labels: ", e);
        }

    }

    private void initDate(){
        todaysDate = Calendar.getInstance().getTime();
        Log.d(todaysDate.toString(), "today's date");
        // Date weekFromToday =;
    }

    public Date getTodaysDate(){
        return todaysDate;
    }

    public Date getTomorrowsDate(){
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.add(calendar.DAY_OF_MONTH, 1);
        tomorrowsDate = calendar.getTime();
        return tomorrowsDate;
    }

    public  Date getNextWeekDate(){
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.add(calendar.DAY_OF_MONTH, 7);
        weekfromToday = calendar.getTime();
        return tomorrowsDate;
    }

    //
    public static abassawo.c4q.nyc.ecquo.Model.sPlanner get(Context c) {
        if (sSPlanner == null) {
            sSPlanner = new sPlanner(c.getApplicationContext());
        }
        return sSPlanner;
    }

    private static final String PREF_SEARCH_QUERY = "searchQuery";

    public static String getStoredQuery(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(PREF_SEARCH_QUERY, null);
    }

    public static void setStoredQuery(Context context, String query) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(PREF_SEARCH_QUERY, query).apply();
    }


}

