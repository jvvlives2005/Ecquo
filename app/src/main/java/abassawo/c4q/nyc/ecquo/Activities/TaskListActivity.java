package abassawo.c4q.nyc.ecquo.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import abassawo.c4q.nyc.ecquo.Model.Task;
import abassawo.c4q.nyc.ecquo.Model.sPlanner;
import abassawo.c4q.nyc.ecquo.R;

/**
 * Created by c4q-Abass on 9/14/15.
 */
public class TaskListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_list_activity);
        final ListView lv = (ListView) findViewById(R.id.master_task_list);
        List<Task> mTasks;
        mTasks = sPlanner.get(getApplicationContext()).getTasks();
        lv.setAdapter(new ArrayAdapter<Task>(getApplicationContext(), android.R.layout.simple_list_item_1, mTasks));
    }
}
