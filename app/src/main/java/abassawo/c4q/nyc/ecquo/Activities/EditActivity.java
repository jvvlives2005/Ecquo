package abassawo.c4q.nyc.ecquo.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cocosw.bottomsheet.BottomSheet;
//import com.flipboard.bottomsheet.BottomSheetLayout;
//import com.flipboard.bottomsheet.OnSheetDismissedListener;
//import com.flipboard.bottomsheet.commons.MenuSheetView;


import java.util.List;

import abassawo.c4q.nyc.ecquo.Model.Planner;
import abassawo.c4q.nyc.ecquo.Model.Task;
import abassawo.c4q.nyc.ecquo.R;
import butterknife.Bind;
import butterknife.ButterKnife;

public class EditActivity extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.edit_task_title)
    EditText edittext;
    //@Bind(R.id.bottomsheet)
  //  BottomSheetLayout bottomSheetLayout;
    @Bind(R.id.btn_speak_now)
    ImageButton speakEnterBtn;


    private BottomSheet dateSheet;
    private BottomSheet prioritySheet;
    private BottomSheet locationSheet;
    private BottomSheet reminderSheet;
    private Context ctx;

    private Task task;
    private List<Task> todayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ButterKnife.bind(this);
        ctx = this;
        todayList = Planner.get(ctx).getTasks();


        //bottomSheetLayout.setPeekOnDismiss(true);
        setupDateheets();
        setupLocationSheet();
        setupPrioritySheet();
        setupReminderSheet();
//        showDateSheet(MenuSheetView.MenuType.LIST);
//        final View priorityView = LayoutInflater.from(ctx).inflate(R.layout.bottom_priority_edit, bottomSheetLayout, false);
//        View dateView = LayoutInflater.from(ctx).inflate(R.layout.fragment_date_edit, bottomSheetLayout, false);
        //final View locationView = LayoutInflater.from(ctx).inflate(R.layout.fragment_date_edit, bottomSheetLayout, false);
       // bottomSheetLayout.showWithSheetView(priorityView);
        //bottomSheetLayout.showContextMenu();

        speakEnterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edittext.getText().length()>0){
                   hideKeyboard();
                    Task task = new Task(edittext.getText().toString());
                    dateSheet.show();
                    //showDateSheet(MenuSheetView.MenuType.LIST);
                    //showDateSheet(MenuSheetView.MenuType.GRID);


                }
            }
        });
        if(edittext.getText().length()>0){
            speakEnterBtn.setImageResource(android.R.drawable.ic_media_next);
        }

        edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(edittext.getText().length()>0){
                    speakEnterBtn.setImageResource(android.R.drawable.ic_media_next);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                task = new Task(s.toString());
            }
        });
       // prioritySheet.show();

    }


    public void setupDateheets() {
        dateSheet = new BottomSheet.Builder(this).title("Start Date").sheet(R.menu.menu_date).listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                switch (id) {
                    case R.id.today_item:
                        todayList.add(task);
                        Intent intent = new Intent(ctx, MainActivity.class);
                        intent.putExtra("task_remind_today", task.isRemindMeToday());
                        startActivity(intent);

                        break;
                    default:
                        startActivity(new Intent(ctx, MainActivity.class));
                        break;

                }
                //reminderSheet.show();

            }
        }).build();


    }

    public void setupPrioritySheet() {
        prioritySheet = new BottomSheet.Builder(this).title("Priority").sheet(R.menu.menu_priority).listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                locationSheet.show();
//                switch (id) {
//
//                }
            }
        }).build();


    }

    public void setupLocationSheet() {
        locationSheet = new BottomSheet.Builder(this).title("Location").sheet(R.menu.menu_location).listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).build();
    }

    public void setupReminderSheet(){
        reminderSheet = new BottomSheet.Builder(this).title("Remind Me").sheet(R.menu.menu_reminder).listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

            }
        }).build();
    }

//    private void showDateSheet(MenuSheetView.MenuType menuType) {
//        MenuSheetView dateSheetView =
//                new MenuSheetView(EditActivity.this, menuType, "Start Date", new MenuSheetView.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        Toast.makeText(EditActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
//                        if (bottomSheetLayout.isSheetShowing()) {
//                            bottomSheetLayout.dismissSheet();
//                            startActivity(new Intent(ctx, TaskEditActivity.class)); //fixme
//                        }
//                        return true;
//                    }
//                });
//        dateSheetView.inflateMenu(R.menu.menu_date);
//        bottomSheetLayout.showWithSheetView(dateSheetView);
//    }
//
//    private void showPrioritySheet(MenuSheetView.MenuType menuType){
//        MenuSheetView prioritySheetView = new MenuSheetView(EditActivity.this, menuType, "Set Priority", new MenuSheetView.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem menuItem) {
//                bottomSheetLayout.dismissSheet();
//                locationSheet.show();
//                return false;
//            }
//        });
//        prioritySheetView.inflateMenu(R.menu.menu_priority);
//        bottomSheetLayout.dismissSheet();
//        bottomSheetLayout.showWithSheetView(prioritySheetView);
    //}

//    @Override
//    public void onDismissed(BottomSheetLayout bottomSheetLayout) {
//
//    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

        }
    }
    private void hideKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


}


