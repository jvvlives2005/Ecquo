package abassawo.c4q.nyc.ecquo.Model;

import java.util.Date;
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

    private UUID goal_Id;
    private String goalTitle;
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
        startDate = MainActivity.todaysDate;
        if(this.isDueToday){    //General Rule
           remindUser = true;
            doTaskToday = true;
        }
    }

    public Goal(){

    }

}