package abassawo.c4q.nyc.ecquo.Model;

import android.content.Context;
import android.location.Location;
import android.text.format.DateUtils;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import abassawo.c4q.nyc.ecquo.R;


/**
 * Created by c4q-Abass on 8/31/15.
 */
public class Task extends Note {
    public static final String TASK_KEY_INDEX = "index";
    private String title;
    private static final String JSON_ID = "id";
    private static final String JSON_TITLE = "title";
    private static final String JSON_SOLVED = "solved";
    private static final String JSON_DATE = "date";
    private static final String JSON_START_DATE = "startdate";
    private static final String JSON_LABEL_TAG = "label";
    public Long _id;//for cupboard

    private boolean solved;
    private boolean complete;

    public Task(){

    }

    public String getReminderFrequency() {
        return reminderFrequency;
    }

    public void setReminderFrequency(String reminderFrequency) {
        this.reminderFrequency = reminderFrequency;
    }

    private String reminderFrequency;

    public String getLabel() {
        return label;
    }




    private String label;
    private boolean dueToday;

    public void setRemindMeToday(boolean remindMeToday) {
        this.remindMeToday = remindMeToday;
    }

    private boolean remindMeToday;
    private Date mDueDate;
    private int duration;
    private Date reminderDay;
    private int taskPhotoId;
    private boolean hasCustomPhoto;
    private static Date todaysDate;

    public Date getStartDate() {
        return mStartDate;
    }

    public void setStartDate(Date mStartDate) {
        this.mStartDate = mStartDate;
    }

    private Date mStartDate;

    public Date getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(Date reminderTime) {
        this.reminderTime = reminderTime;
    }

    private Date reminderTime;

    public void setPriority(Float priority) {
        this.priority = priority;
    }

    private Float priority;

    public String getLocation() {
        return location;
    }

    public Float getPriority(){
        return priority;
    }

    private String location; //will be converted to Latlng


    public boolean isDueToday(){
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(new Date());
        if(mDueDate != null) {
            c2.setTime(mDueDate);
            boolean sameDay = c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) &&
                    c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR);
            return sameDay;
        } else {
            return false;
        }

    }

    public void setDueToday(){
        this.mDueDate = todaysDate;
    }

    public void setDueTomorrow(Context ctx){
        this.mDueDate = (sPlanner.get(ctx).getTomorrowsDate());
    }

    public void setLocation(String location){

        this.location = location;
    }

    public void setDueinOneWeek(Context ctx){
        this.mDueDate = sPlanner.get(ctx).getNextWeekDate();
    }

    public void setLabel(String label){
        this.label = label;
    }




    public int getTaskPhoto() {
        return taskPhotoId;
    }

    public void setTaskPhoto(int taskPhoto_id) {
        this.taskPhotoId = taskPhoto_id;
    }

    public boolean isCustomPhotoSet(){
        return this.getTaskPhoto() != R.drawable.mountaintop;
    }


    public Date getDueDate() {
        return mDueDate;
    }

    public void setDueDate(Date dueDate) {
        this.mDueDate = dueDate;
    }


    public boolean isNotifyToday() {
        boolean notifyToday = false;
        if(this.isDueToday()){
            notifyToday = true;
        }
        return notifyToday;
    }




    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public Task(String title, Context ctx){
        Calendar mcurrentDate = Calendar.getInstance();
        this.label = "Personal";
        todaysDate = mcurrentDate.getTime();
        this.title = title;
        mStartDate = todaysDate;
        this.taskPhotoId = R.drawable.mountaintop;
    }


    public void setDuetoday(Context ctx){
        this.setDueDate(sPlanner.get(ctx).getTodaysDate());
    }

    @Override
    public String toString(){
        return "Title " + this.title + "\n" +
                "Priority " + this.getPriority() + "\n" +
                "Due Date " + this.getDueDate() + "\n" +
                "Location " + this.location + "\n" +
                "custom photo set? : " + this.isCustomPhotoSet() + "\n" +
                "Part of today's list?" + this.isNotifyToday();
    }

    public boolean isReminderForToday(){
        return remindMeToday; //fixme
    }


    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_TITLE, title);
        json.put(JSON_SOLVED, solved);
        json.put(JSON_DATE, mDueDate.getTime());
        json.put(JSON_START_DATE, mStartDate.getTime());
        json.put(JSON_LABEL_TAG, label);
        return json;
    }


}
