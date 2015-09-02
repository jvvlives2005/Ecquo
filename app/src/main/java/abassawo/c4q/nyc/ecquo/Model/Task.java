package abassawo.c4q.nyc.ecquo.Model;

import android.location.Location;

import java.util.Date;

import abassawo.c4q.nyc.ecquo.Interfaces.Actionable;

/**
 * Created by c4q-Abass on 8/31/15.
 */
public class Task extends Note implements Actionable {
    private String title;
    private Date deadlineDate;
    private Location location;
    private boolean dueToday = deadlineDate == new Date();

    public Task(String title){
        this.title = title;
    }

    public Date getDeadlineDate()    {
        return deadlineDate;
    }




    public boolean isDueToday(){
        return dueToday;
    }


    @Override
    public void setDeadline(Date date) {
        this.deadlineDate = date;
    }

    @Override
    public void setLocation(Location loc) {
        this.location = loc;

    }
}
