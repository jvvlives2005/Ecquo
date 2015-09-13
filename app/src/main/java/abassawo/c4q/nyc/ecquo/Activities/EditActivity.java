package abassawo.c4q.nyc.ecquo.Activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cocosw.bottomsheet.BottomSheet;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.truizlop.fabreveallayout.FABRevealLayout;
import com.truizlop.fabreveallayout.OnRevealChangeListener;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import abassawo.c4q.nyc.ecquo.Fragments.DatePickerFragment;
import abassawo.c4q.nyc.ecquo.Model.sPlanner;
import abassawo.c4q.nyc.ecquo.Model.Task;
import abassawo.c4q.nyc.ecquo.R;
import butterknife.Bind;
import butterknife.ButterKnife;

public class EditActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener{
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.edit_task_title)
    EditText edittext;
    @Bind(R.id.rating_bar)
    RatingBar priorityRatingBar;
    @Bind(R.id.fab_reveal_button)
    FloatingActionButton fab;
    @Bind(R.id.camButton)
    ImageButton cameraButton;
    @Bind(R.id.note_imageView) ImageView imgPreview;

    @Bind(R.id.fab_reveal_layout_test)
    FABRevealLayout fabRevealLayout;


    private BottomSheet dateSheet;
    private BottomSheet prioritySheet;
    @Bind(R.id.bottomsheet) BottomSheetLayout bottomSheetLayout;
    private BottomSheet locationSheet;
    private BottomSheet reminderSheet;
    private BottomSheet pictureDialogSheet;
    private Uri imageUri;
    private int CAPTURE_IMAGE = 9;

    private Context ctx;
    private DatePicker datePicker;
    private static final String DIALOG_DATE = "date";
    private Task mTask; //TASK BEING EDITED.
    public static List<Task> todayList;
    public static List<Task> taskList;
    private DatePickerFragment dateDialog;

    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ButterKnife.bind(this);
        initState();
        initViews();
        setupListeners();
    }

    public void initState(){
        ctx = this;
        taskList = sPlanner.get(ctx).getTasks();
    }

    public void initViews(){
        setupActionBar();
        configureFABReveal(fabRevealLayout);
        setupDateSheets();
        setupLocationSheet();
        setupPrioritySheet();
        setupReminderSheet();
        setupPicturePickerSheet();
    }

    public void setupListeners(){
        cameraButton.setOnClickListener(this);
        fab.setOnClickListener(this);
        edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (hasText(edittext)) {
                    mTask = new Task(s.toString(), ctx);
                }
            }
        });
        // prioritySheet.show();

    }

    public void showDialogSheet(){
        if(bottomSheetLayout.isSheetShowing()){
            bottomSheetLayout.dismissSheet();
        }
                bottomSheetLayout.showWithSheetView(LayoutInflater.from(ctx).inflate(R.layout.dialog_date, bottomSheetLayout, false));
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
                        //dateSheet.show();
                        showDialogSheet();
                        //prioritySheet.show();
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

    public void setupPicturePickerSheet(){
        pictureDialogSheet = new BottomSheet.Builder(this).title("Choose a photo for this task").sheet(R.menu.menu_picture_edit).listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                switch (id) {
                    case R.id.take_a_picture:
                        startCameraIntent();
                        break;
                }
            }
        }).build();

    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
        if(resultCode == RESULT_OK){
            Date date = (Date) data.getExtras().get("EXTRA_DATE");
            mTask.setDueDate(date);
        }
    }

    public void setupDateSheets() {
            fm = getSupportFragmentManager();
            mTask = new Task(edittext.getText().toString(), ctx);

            dateDialog = DatePickerFragment.newInstance(mTask.getDueDate());
            dateSheet = new BottomSheet.Builder(this).title("Due Date").sheet(R.menu.menu_date).listener(new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    switch (id) {
                        case R.id.today_item:
                            mTask.setDueToday();
                            taskList.add(mTask);
                            Log.d(mTask.toString(), "due date test");
                            //timeSheet.show();
                            reminderSheet.show();
                            break;
                        case R.id.tomorrow:
                            mTask.setDueTomorrow(ctx); //setDueTomorrow fixme
                            taskList.add(mTask);
                            Log.d(mTask.toString(), "due date test");
                            reminderSheet.show();
                            break;
                        case R.id.choosedate:
                            fm.beginTransaction().add(dateDialog, "DATE").commit();
                            reminderSheet.show();
                            break;
                        case R.id.nextweek:
                            mTask.setDueinOneWeek(ctx);
                            Log.d(mTask.toString(), "due date test");
                        default:
                            //startActivity(new Intent(ctx, MainActivity.class));
                            break;
                    }

                }
            }).build();



    }

    @Override
    public void onProvideAssistData(Bundle data) {
        super.onProvideAssistData(data);
    }

    public void setupPrioritySheet() {
        prioritySheet = new BottomSheet.Builder(this).title("Priority").sheet(R.menu.menu_reminder).listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
               // locationSheet.show();
                switch (id) {
                    case R.id.locationreminder:
                    startActivity(new Intent(ctx, MapActivity.class));
                    break;
                }
            }
        }).build();


    }

    public void setupLocationSheet() {
        locationSheet = new BottomSheet.Builder(this).title("Location").sheet(R.menu.menu_location).listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(ctx, MapActivity.class);
                switch (id) {
                //fixme
                    case R.id.my_current_location:
                        startActivity(new Intent(EditActivity.this, MainActivity.class));
                        break;
                    case R.id.saved_places:
                        break;
                    case R.id.new_place:
                startActivity(intent);
                        break;

                }
            }
        }).build();
    }

    public void setupReminderSheet() {
        reminderSheet = new BottomSheet.Builder(this).title("Remind Me").sheet(R.menu.menu_reminder).listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                locationSheet.show();
//                Intent intent = new Intent(ctx, MainActivity.class);  //fixme
//                startActivity(intent);
            }
        }).build();
    }


    private void hideKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public boolean isEmpty(EditText edittext) {
        return edittext.getText().length() == 0;
    }

    public boolean hasText(EditText editText) {
        return !isEmpty(editText);
    }

    public void setupActionBar() {
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.app_name));
//        actionBar.setSubtitle(date.toString());
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayHomeAsUpEnabled(true);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_task_edit, menu);
        MenuItem labelItem = menu.findItem(R.id.menu_item_search);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() ==android.R.id.home) {
            startActivity(new Intent(ctx, MainActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void startCameraIntent(){
        //Destination
        String mediaStorageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES).getPath();

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        File mediaFile = new File(mediaStorageDir + File.separator + "IMG_" + timeStamp + ".jpg");
        // Send intent to take picture
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        imageUri = Uri.fromFile(mediaFile);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mediaFile));
        startActivityForResult(cameraIntent, CAPTURE_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == CAPTURE_IMAGE) {
            Glide.with(this).load(imageUri).into(imgPreview);
        }
//        if (requestCode == REQUEST_DATE) {
//            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
//            mNote.setDate(date);
//            updateDate();
//        }
//        } else if (requestCode == REQUEST_TIME) {
//            Date date = (Date)data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
//            mNote.setDate(date);
//            updateDate();
//        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_reveal_button:
                if (hasText(edittext)) {
                    fabRevealLayout.revealSecondaryView();
                } else {
                    Toast.makeText(getApplicationContext(), "Title neded", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.camButton:
                pictureDialogSheet.show();
                break;

        }
    }


    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

    }
}


