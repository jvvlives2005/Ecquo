package abassawo.c4q.nyc.ecquo.Model;

import android.media.Image;
import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import abassawo.c4q.nyc.ecquo.Activities.MainActivity;

/**
 * Created by c4q-Abass on 8/16/15.
 */
public class Goal {
    private static final String JSON_ID = "id";
    private static final String JSON_TITLE = "title";
    private static final String JSON_COMPLETED = "completed";
    private static final String JSON_DUEDATE = "duedate";


    private List<Task> taskList;

    public long getId() {
        return id;
    }

    public long id;

    public UUID getGoal_Id() {
        return goal_Id;
    }

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


    public List<Task> getTaskList() {
        return taskList;
    }



    private boolean remindUser;


    public Goal(String title){
        this.goalTitle = title;
        startDate = new Date();
        this.taskList = new ArrayList<Task>();

    }

    public Goal(){
        goal_Id = UUID.randomUUID();
        startDate = new Date();
        taskList = new ArrayList<Task>();


    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        return json;
    }

    public void add(Task task){
        taskList.add(task);
    }


    @Override
    public String toString(){
        return this.getGoalTitle();
    }


}