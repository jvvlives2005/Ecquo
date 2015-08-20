package abassawo.c4q.nyc.ecquo.Model;

import android.media.Image;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import abassawo.c4q.nyc.ecquo.Activities.MainActivity;

/**
 * Created by c4q-Abass on 8/16/15.
 */
public class Goal extends Note {
    private static final String JSON_ID = "id";
    private static final String JSON_TITLE = "title";
    private static final String JSON_COMPLETED = "completed";
    private static final String JSON_DUEDATE = "duedate";


    private UUID goal_Id;

    public String getGoalTitle() {
        return goalTitle;
    }

    public void setGoalTitle(String goalTitle) {
        this.goalTitle = goalTitle;
    }

    private String goalTitle;
    private String goalSubTitle; //caption
    private Image goalIcon;
    private Date startDate;
    private Date dueDate;


    public List<GoalTask> getTaskList() {
        return taskList;
    }

    public void addtoTaskList(GoalTask task) {
        this.taskList.add(task);
    }


    private List<GoalTask> taskList;

    private boolean remindUser;

    public boolean isDueToday() {
        isDueToday = this.dueDate == MainActivity.todaysDate;
        return isDueToday;
    }

    public void setIsDueToday(boolean isDueToday) {

        this.dueDate = MainActivity.todaysDate;
    }

    private boolean isDueToday;
    private boolean doTaskToday;

    public Goal(String title){
        this.goalTitle = title;
        startDate = new Date();
        if(this.isDueToday){    //General Rule
           remindUser = true;
            doTaskToday = true;
        }
    }

    public Goal(){
        goal_Id = UUID.randomUUID();
        startDate = new Date();
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
//        json.put(JSON_ID, mId.toString());
//        json.put(JSON_TITLE, title);
//        json.put(JSON_SOLVED, mSolved);
//        json.put(JSON_DATE, mDate.getTime());
        // json.put(JSON_LABEL_TAG, label);
        return json;
    }


    @Override
    public String toString(){
        return this.getGoalTitle();
    }

}