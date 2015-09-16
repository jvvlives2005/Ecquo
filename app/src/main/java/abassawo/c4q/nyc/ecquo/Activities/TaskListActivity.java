package abassawo.c4q.nyc.ecquo.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import abassawo.c4q.nyc.ecquo.Model.Task;
import abassawo.c4q.nyc.ecquo.Model.sPlanner;
import abassawo.c4q.nyc.ecquo.R;

/**
 * Created by c4q-Abass on 9/14/15.
 */
public class TaskListActivity extends AppCompatActivity {
    List<Task> mTasks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_list_activity);


        mTasks =  sPlanner.get(getApplicationContext()).fetchAllTasks();


        final ListView lv = (ListView) findViewById(R.id.master_task_list);
//        List<Task> allTasks =  sPlanner.get(getApplicationContext()).fetchAllTasks();
        lv.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, mTasks));

        //lv.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mTasks));
    }

    @Override
    public void onBackPressed()
    {// code here to show dialog
        super.onBackPressed();  // optional depending on your needs
    }


}
