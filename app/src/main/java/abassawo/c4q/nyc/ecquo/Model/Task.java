package abassawo.c4q.nyc.ecquo.Model;

import android.location.Location;

import java.util.Date;


/**
 * Created by c4q-Abass on 8/31/15.
 */
public class Task extends Note{
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;
    private Date deadlineDate;
    private Location location;
    private boolean dueToday = deadlineDate == new Date();

    public boolean isRemindMeToday() {
        return remindMeToday;
    }

    public void setRemindMeToday(boolean remindMeToday) {
        this.remindMeToday = remindMeToday;
    }

    private boolean remindMeToday = dueToday;


    public Task(String title){
        this.title = title;
    }

    public Date getDeadlineDate()    {
        return deadlineDate;
    }


    public boolean isDueToday(){
        return dueToday;
    }




}
