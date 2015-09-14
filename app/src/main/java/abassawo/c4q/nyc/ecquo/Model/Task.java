package abassawo.c4q.nyc.ecquo.Model;

import android.content.Context;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

import abassawo.c4q.nyc.ecquo.R;


/**
 * Created by c4q-Abass on 8/31/15.
 */
public class Task extends Note implements Parcelable {
    private String title;

    public String getLabel() {
        return label;
    }

    private String label;
    private boolean dueToday;
    private boolean remindMeToday;
    private Date mDueDate;
    private int duration;
    private Date reminderDay;
    private int taskPhotoId;
    private boolean hasCustomPhoto;
    private static Date todaysDate;
    private Date mStartDate;

    public void setPriority(Float priority) {
        this.priority = priority;
    }

    private Float priority;

    public Location getLocation() {
        return location;
    }

    public Float getPriority(){
        return priority;
    }

    private Location location;


    public boolean isDueToday(){
        return this.mDueDate == this.todaysDate;
    }

    public void setDueToday(){
        this.setDueDate(this.todaysDate);
    }

    public void setDueTomorrow(Context ctx){
        this.setDueDate(sPlanner.get(ctx).getTomorrowsDate());
    }

    public void setLocation(Location location){
        this.location = location;
    }


    public void setDueinOneWeek(Context ctx){
        this.setDueDate(sPlanner.get(ctx).getNextWeekDate());
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
//        else if (this.isReminderForToday){
//
//        }
        return notifyToday;
    }




    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public Task(String title, Context ctx){
        this.label = "Personal";
        todaysDate = sPlanner.get(ctx).getTodaysDate();
        this.title = title;
        mStartDate = todaysDate;
        // mDueDate = new Date();
        this.taskPhotoId = R.drawable.mountaintop;
        //endDate = startDate + duration;
    }


    public void setDuetoday(Context ctx){
        this.setDueDate(sPlanner.get(ctx).getTodaysDate());
    }

    @Override
    public String toString(){
        return "Title " + this.title + "\n" +
                "Priority" + this.getPriority() + "\n" +
                "Due Date " + this.getDueDate() + "\n" +
                "Location" + this.location + "\n" +
                "custom photo set? : " + this.isCustomPhotoSet() + "\n" +
                "Part of today's list?" + this.isNotifyToday();
    }

    public boolean isReminderForToday(){
        return this.isDueToday(); //fixme
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }


}
