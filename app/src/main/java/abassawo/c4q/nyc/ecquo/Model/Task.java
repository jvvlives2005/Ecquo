package abassawo.c4q.nyc.ecquo.Model;

import java.util.Date;

/**
 * Created by c4q-Abass on 8/31/15.
 */
public class Task extends Note {
    private String title;

    public Task(String title){
        this.title = title;
    }

    public Date getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(Date deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    private Date deadlineDate;
}
