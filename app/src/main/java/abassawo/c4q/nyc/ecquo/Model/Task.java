package abassawo.c4q.nyc.ecquo.Model;

import android.location.Location;

import java.util.Date;


/**
 * Created by c4q-Abass on 8/31/15.
 */
public class Task extends Note{
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


}
