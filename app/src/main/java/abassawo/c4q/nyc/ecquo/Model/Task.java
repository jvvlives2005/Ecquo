package abassawo.c4q.nyc.ecquo.Model;

import android.location.Location;

import java.util.Date;


/**
 * Created by c4q-Abass on 8/31/15.
 */
public class Task extends Note{
    private String title;
    private boolean isNotifyToday;
    private Date startDate;
    private Date endDate;
    private Location location;
    private int duration;
    private Date reminderDay;


    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }


    public boolean isNotifyToday() {
        return isNotifyToday;
    }

    public void setDisplayToday(boolean isNotifyToday) {
        this.isNotifyToday = isNotifyToday;
    }




    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public boolean displayToday() {
        return isNotifyToday;
    }


    public Task(String title){
        this.title = title;
        startDate = new Date();
        //endDate = startDate + duration;
    }

    public Date getEndDate()    {
        return endDate;
    }




}
