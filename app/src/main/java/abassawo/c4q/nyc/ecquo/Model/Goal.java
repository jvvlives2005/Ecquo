package abassawo.c4q.nyc.ecquo.Model;

import android.media.Image;
import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import abassawo.c4q.nyc.ecquo.Activities.MainActivity;

/**
 * Created by c4q-Abass on 8/16/15.
 */
public class Goal implements Collection<Task>{
    private static final String JSON_ID = "id";
    private static final String JSON_TITLE = "title";
    private static final String JSON_COMPLETED = "completed";
    private static final String JSON_DUEDATE = "duedate";

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

    public void addtoTaskList(Task task) {
        this.taskList.add(task);
    }




    private List<Task> taskList;

    private boolean remindUser;



    public void setIsDueToday(boolean isDueToday) {

        //this.dueDate = MainActivity.todaysDate;
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

    @Override
    public boolean add(Task object) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends Task> collection) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean contains(Object object) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @NonNull
    @Override
    public Iterator<Task> iterator() {
        return null;
    }

    @Override
    public boolean remove(Object object) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @NonNull
    @Override
    public <T> T[] toArray(T[] array) {
        return null;
    }
}