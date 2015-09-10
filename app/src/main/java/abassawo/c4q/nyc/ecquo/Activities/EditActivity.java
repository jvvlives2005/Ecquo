package abassawo.c4q.nyc.ecquo.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cocosw.bottomsheet.BottomSheet;
import com.truizlop.fabreveallayout.FABRevealLayout;
import com.truizlop.fabreveallayout.OnRevealChangeListener;
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
    @Bind(R.id.rating_bar)
    RatingBar priorityRatingBar;
    @Bind(R.id.fab_reveal_button)
    FloatingActionButton fab;

    @Bind(R.id.fab_reveal_layout_test)
    FABRevealLayout fabRevealLayout;


    private BottomSheet dateSheet;
    private BottomSheet prioritySheet;
    private BottomSheet locationSheet;
    private BottomSheet reminderSheet;
    private Context ctx;

    private Task task;
    public static List<Task> todayList;
    public static List<Task>taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ButterKnife.bind(this);
        configureFABReveal(fabRevealLayout);
        ctx = this;
        todayList = Planner.get(ctx).getTasks();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edittext.getText().toString().length() > 0) {
                    fabRevealLayout.revealSecondaryView();
                } else{
                    Toast.makeText(getApplicationContext(), "Title neded", Toast.LENGTH_SHORT).show();
                }
            }
        });


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




        edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                task = new Task(s.toString());
            }
        });
       // prioritySheet.show();

    }

    private void configureFABReveal(FABRevealLayout fabRevealLayout) {
        fabRevealLayout.setOnRevealChangeListener(new OnRevealChangeListener() {
            @Override
            public void onMainViewAppeared(FABRevealLayout fabRevealLayout, View mainView) {
                //showMainViewItems();
            }

            @Override
            public void onSecondaryViewAppeared(final FABRevealLayout fabRevealLayout, View secondaryView) {
                priorityRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        hideKeyboard();
                        dateSheet.show();
                        prepareBackTransition(fabRevealLayout);
                    }
                });
                secondaryView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hideKeyboard();
                        dateSheet.show();
                        prepareBackTransition(fabRevealLayout);
                       //TESTING
                    }
                });

            }
        });
    }

    private void prepareBackTransition(final FABRevealLayout fabRevealLayout) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fabRevealLayout.revealMainView();
            }
        }, 2000);
    }



    public void setupDateheets() {
        dateSheet = new BottomSheet.Builder(this).title("Start Date").sheet(R.menu.menu_date).listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                switch (id) {
                    case R.id.today_item:
                        if (edittext.getText().toString().length() > 0) {
                            task = new Task(edittext.getText().toString());
                            task.setDisplayToday(true);
                            todayList.add(task);
                            reminderSheet.show();
                        }
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
                Intent intent = new Intent(ctx, MainActivity.class);  //fixme
                        startActivity(intent);
            }
        }).build();
    }


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


