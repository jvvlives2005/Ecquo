package abassawo.c4q.nyc.ecquo.Activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cocosw.bottomsheet.BottomSheet;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.commons.ImagePickerSheetView;
import com.flipboard.bottomsheet.commons.MenuSheetView;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.truizlop.fabreveallayout.FABRevealLayout;
import com.truizlop.fabreveallayout.OnRevealChangeListener;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

//import abassawo.c4q.nyc.ecquo.Fragments.DatePickerFragment;
//import abassawo.c4q.nyc.ecquo.Fragments.TimePickerFragment;
import abassawo.c4q.nyc.ecquo.Fragments.DatePickerFragment;
import abassawo.c4q.nyc.ecquo.Fragments.TimePickerFragment;
import abassawo.c4q.nyc.ecquo.Model.DBHelper;
import abassawo.c4q.nyc.ecquo.Model.sPlanner;
import abassawo.c4q.nyc.ecquo.Model.Task;
import abassawo.c4q.nyc.ecquo.R;
import butterknife.Bind;
import butterknife.ButterKnife;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;


public class EditActivity extends AppCompatActivity implements View.OnClickListener,
        com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener,
        com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener {
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
    @Bind(R.id.note_imageView)
    ImageView imgPreview;
    @Bind(R.id.saveFab)FloatingActionButton fabSave;

    @Bind(R.id.fab_reveal_layout)
    FABRevealLayout fabRevealLayout;
    private LocationManager locationManager;
    private BottomSheet dateSheet;
    @Bind(R.id.bottomsheet)
    BottomSheetLayout bottomSheetLayout;
    private Uri imageUri;
    private int CAPTURE_IMAGE = 9;
    private String TAG = "EditActivity";
    private Uri cameraImageUri;

    private Context ctx;
    private static final String DIALOG_DATE = "date";
    private Task mTask; //TASK BEING EDITED.
    public static List<Task> todayList;
    public static List<Task> taskList;
    private GoogleApiClient mClient;
    private Location mCurrentLocation;
    private static final long MIN_TIME = 400;
    private static final float MIN_DISTANCE = 1000;
    private static final int REQUEST_STORAGE = 0;
    private static final int REQUEST_IMAGE_CAPTURE = REQUEST_STORAGE + 1;
    private static final int REQUEST_LOAD_IMAGE = REQUEST_IMAGE_CAPTURE + 1;
    public static final int REQUEST_TIME = 3;
    public static final int REQUEST_DATE = REQUEST_TIME + REQUEST_TIME;
    public static final int REQUEST_LOCATION = REQUEST_DATE + REQUEST_TIME;

    private BottomSheet locationSheet;
    private BottomSheet reminderSheet;

    private SQLiteDatabase db;

    private static String mTitle;
    private static float mPriority;


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "OnSaveinstanceState");
        //outState.p(Task.TASK_KEY_INDEX, mTask.getId());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        DBHelper dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();




//        else if(savedInstanceState != null){
//            mTask = savedInstanceState.getInt(Task.TASK_KEY_INDEX, "AA" )
//        }
        ButterKnife.bind(this);
        initState();
        buildGoogleMapClient(this);
        initViews();
        setupListeners();
    }



    public void initState() {
        ctx = this;
        taskList = sPlanner.get(ctx).getTasks();
    }

    public void initViews() {
        setupActionBar();
        configureFABReveal(fabRevealLayout);
        setupDateSheets();
        setupReminderSheet();
        setupLocationSheet();
    }

    public void setupListeners() {
        fabSave.setOnClickListener(this);
        priorityRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                hideKeyboard();
                mPriority = rating;
                mTask.setPriority(mPriority);
                Log.d(String.valueOf(mPriority), TAG);
                Log.d(mTask.getPriority().toString(), TAG);
                dateSheet.show();
                //showDatePicker();

            }
        });
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
                    mTitle = s.toString();
                    mTask = new Task(mTitle, ctx);
                    long id = cupboard().withDatabase(db).put(mTask);
                }
            }
        });

    }


    private void configureFABReveal(FABRevealLayout fabRevealLayout) {
        fabRevealLayout.setOnRevealChangeListener(new OnRevealChangeListener() {
            @Override
            public void onMainViewAppeared(FABRevealLayout fabRevealLayout, View mainView) {
                //showMainViewItems();
            }

            @Override
            public void onSecondaryViewAppeared(final FABRevealLayout fabRevealLayout, View secondaryView) {
                hideKeyboard();
                secondaryView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dateSheet.show();
                        prepareBackTransition(fabRevealLayout);

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


    public void setupDateSheets() {
        mTask = new Task(edittext.getText().toString(), ctx);
        long id = cupboard().withDatabase(db).put(mTask);

        dateSheet = new BottomSheet.Builder(this).title("Due Date").sheet(R.menu.menu_date).listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                switch (id) {
                    case R.id.today_item:
                        mTask.setDueToday();
                       // taskList.add(mTask);
                        Log.d(mTask.toString(), "due date test");
                        prepareBackTransition(fabRevealLayout);
                        reminderSheet.show();
                        // startActivity(new Intent(EditActivity.this, MainActivity.class));
                        break;
                    case R.id.tomorrow:
                        mTask.setDueTomorrow(ctx); //setDueTomorrow fixme
                        //taskList.add(mTask);
                        Log.d(mTask.toString(), "due date test");
                        prepareBackTransition(fabRevealLayout);
                        reminderSheet.show();
                        //startActivity(new Intent(EditActivity.this, MainActivity.class));
                        break;
                    case R.id.choosedate:
                        dateSheet.dismiss();//testing
                        showCustomDatePicker();
                        Calendar mcurrentDate = Calendar.getInstance();
                        int mYear = mcurrentDate.get(Calendar.YEAR);
                        int mMonth = mcurrentDate.get(Calendar.MONTH);
                        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
                        // fm.beginTransaction().add(dateDialog, "DATE").commit(); //open datepicker
                        reminderSheet.show();
                        break;
                    case R.id.nextweek:
                        mTask.setDueinOneWeek(ctx);
                        Log.d(mTask.getDueDate().toString(), "due date test");
                        prepareBackTransition(fabRevealLayout);
                        reminderSheet.show();
                        //startActivity(new Intent(EditActivity.this, MainActivity.class));
                        break;
                    default:
                        break;
                }

            }
        }).build();


    }


    @Override
    public void onProvideAssistData(Bundle data) {
        super.onProvideAssistData(data);
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = createCameraIntent();
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent != null) {
            // Create the File where the photo should go
            try {
                File imageFile = createImageFile();
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            } catch (IOException e) {
                // Error occurred while creating the File
                //genericError("Could not create imageFile for camera");
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );


        // Save a file: path for use with ACTION_VIEW intents
        cameraImageUri = Uri.fromFile(imageFile);
        return imageFile;
    }

    private void showGalleryPickerSheetView() {
        ImagePickerSheetView sheetView = new ImagePickerSheetView.Builder(this)
                .setMaxItems(30)
                .setShowCameraOption(createCameraIntent() != null)
                .setShowPickerOption(createPickIntent() != null)
                .setImageProvider(new ImagePickerSheetView.ImageProvider() {
                    @Override
                    public void onProvideImage(ImageView imageView, Uri imageUri, int size) {
                        Glide.with(EditActivity.this)
                                .load(imageUri)
                                .centerCrop()
                                .crossFade()
                                .into(imageView);
                    }
                })
                .setOnTileSelectedListener(new ImagePickerSheetView.OnTileSelectedListener() {
                    @Override
                    public void onTileSelected(ImagePickerSheetView.ImagePickerTile selectedTile) {
                        bottomSheetLayout.dismissSheet();
                        if (selectedTile.isCameraTile()) {
                            dispatchTakePictureIntent();
                        } else if (selectedTile.isPickerTile()) {
                            startActivityForResult(createPickIntent(), REQUEST_LOAD_IMAGE);
                        } else if (selectedTile.isImageTile()) {
                            showSelectedImage(selectedTile.getImageUri());
                        } else {
                            //genericError();
                        }
                    }
                })
                .setTitle("Choose an image...")
                .create();


        bottomSheetLayout.showWithSheetView(sheetView);

    }


    private void showSelectedImage(Uri selectedImageUri) {
        imgPreview.setImageDrawable(null);
        Glide.with(this)
                .load(selectedImageUri)
                .crossFade()
                .fitCenter()
                .into(imgPreview);
    }


    private Intent createPickIntent() {
        Intent picImageIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (picImageIntent.resolveActivity(getPackageManager()) != null) {
            return picImageIntent;
        } else {
            return null;
        }
    }


    private Intent createCameraIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            return takePictureIntent;
        } else {
            return null;
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
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(ctx, MainActivity.class));
            finish();
        } else if (item.getItemId() == R.id.menu_item_label) {
            startActivity(new Intent(ctx, LabelPicker.class));

        }
        return super.onOptionsItemSelected(item);
    }

//    public void startCameraIntent() {
//        //Destination
//        String mediaStorageDir = Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_PICTURES).getPath();
//
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
//        File mediaFile = new File(mediaStorageDir + File.separator + "IMG_" + timeStamp + ".jpg");
//        // Send intent to take picture
//        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        imageUri = Uri.fromFile(mediaFile);
//        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mediaFile));
//        startActivityForResult(cameraIntent, CAPTURE_IMAGE);
//    }

    @Override
    public void onSupportActionModeFinished(ActionMode mode) {
        super.onSupportActionModeFinished(mode);
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            long id = cupboard().withDatabase(db).put(mTask);
            Uri selectedImage = null;
            if (requestCode == REQUEST_LOAD_IMAGE && data != null) {
                selectedImage = data.getData();
                if (selectedImage == null) {
                    //generic error
                }
            } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
                // Do something with imagePath
                selectedImage = cameraImageUri;
            } else if (requestCode == REQUEST_LOCATION) {
                mTask.setLocation(data.getExtras().getString("LOCATION"));
                //startActivity(new Intent(this, MainActivity.class));
            } else if (requestCode == REQUEST_DATE) {
                Intent intent = getIntent();
                mTask.setDueDate((Date) intent.getExtras().get((DatePickerFragment.EXTRA_DATE)));
                Log.d(TAG, mTask.getDueDate().toString());
            }


            if (selectedImage != null) {
                showSelectedImage(selectedImage);
            } else {
                //genericError();
            }

            if (resultCode == DatePickerFragment.DATE_PICKED_RESULT_CODE) {
                long datePickedInMilliseconds = data.getLongExtra(DatePickerFragment.DATE_PICKED_INTENT_KEY, 0);
                mTask.setDueDate(new Date(datePickedInMilliseconds));
                Log.d(TAG, mTask.getDueDate().toString());
            }

            if (requestCode == REQUEST_DATE) {
                //Date date = (Date) data.getExtras().get("EXTRA_DATE");
                Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
                mTask.setDueDate(date);
                // updateDate();
            } else if (requestCode == REQUEST_TIME) {
                Date date = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
                mTask.setReminderTime(date);
                // updateDate();

            }
            if (resultCode != Activity.RESULT_OK) {
                return;
            }
        }
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
        if (resultCode == RESULT_OK) {
            Date date = (Date) data.getExtras().get("EXTRA_DATE");
            mTask.setDueDate(date);
        }
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
                hideKeyboard();
                if (!bottomSheetLayout.isSheetShowing()) {
                    showGalleryPickerSheetView();
                } else {
                    bottomSheetLayout.dismissSheet();
                }
                break;
            case R.id.saveFab:
                taskList.add(mTask);
                cupboard().withDatabase(db).put(mTask);
                startActivity(new Intent(EditActivity.this, MainActivity.class));
                break;

//            case R.id.time_reminder_fab_button: //set these listeners on bottomsheet dialog
//                fm.beginTransaction().add(timeDialog, "TIME").commit();
//                break;
//            case R.id.location_reminder_fab_button:


        }
    }

    public void buildGoogleMapClient(Context ctx) {
        mClient = new GoogleApiClient.Builder(ctx)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addApi(LocationServices.API).addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {
                        invalidateOptionsMenu();
                        mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mClient);

                    }


                    @Override
                    public void onConnectionSuspended(int i) {


                    }
                })
                .build();


    }

    public void setupLocationSheet() {
        locationSheet = new BottomSheet.Builder(this).title("Location").sheet(R.menu.menu_location).listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(ctx, MapViewActivity.class);
                switch (id) {
                    case R.id.current_location:
                        if (mCurrentLocation != null) {
                            LatLng location = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
                            mTask.setLocation(location.toString());
                            Log.d(mCurrentLocation.getLatitude() + "," + mCurrentLocation.getLongitude(), "location");
                        } else {
                            Snackbar snack = Snackbar.make(findViewById(R.id.snackbar_space), "GPS Services are not turned", Snackbar.LENGTH_SHORT);
                                    snack.show();
                        }
                        taskList.add(mTask);
                        startActivity(new Intent(EditActivity.this, MainActivity.class));
                        break;
                    case R.id.choose_location:
                        Intent locationIntent = new Intent(EditActivity.this, MapViewActivity.class);
                              //  taskList.add(mTask); CALL THIS IN ON ACTIVITY RESULT
                        startActivityForResult(intent, REQUEST_LOCATION); //fixme
                        break;
                    case R.id.no_location:
                        LatLng latlng  = new LatLng(0, 0);
                        mTask.setLocation(latlng.toString());
                        startActivity(new Intent(EditActivity.this, MainActivity.class));
                        taskList.add(mTask);
                        cupboard().withDatabase(db).put(mTask); //updating db item
                    default:
                        startActivity(new Intent(EditActivity.this, MainActivity.class));
                }
            }
        }).build();
    }

    public void setupReminderSheet() {
        reminderSheet = new BottomSheet.Builder(this).title("Remind Me").sheet(R.menu.menu_reminder).listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                switch (id) {
                    case R.id.current_time_reminder:
                        mTask.setReminderTime(new Date());
                        locationSheet.show();
                        break;
                    case R.id.custom_time:
                         showCustomTimePicker();
                        //fm.beginTransaction().add(timeDialog, "TIME").commit();
                        break;
                    case R.id.no_timed_reminder:
                        //taskList.add(mTask);
                        locationSheet.show();
                        break;
                }
                //  locationSheet.show();
//                Intent intent = new Intent(ctx, MainActivity.class);  //fixme
//                startActivity(intent);
            }
        }).build();
    }

    public void showCustomTimePicker() {
        Calendar now = Calendar.getInstance();
        com.wdullaer.materialdatetimepicker.time.TimePickerDialog tpd;
        tpd = com.wdullaer.materialdatetimepicker.time.TimePickerDialog.newInstance(EditActivity.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE), false);
        tpd.setThemeDark(true);
        tpd.vibrate(false);
        tpd.dismissOnPause(true);
        tpd.setAccentColor(Color.parseColor("#03A9F4"));

        tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Log.d("TimePicker", "Dialog was cancelled");
            }
        });
        tpd.show(getFragmentManager(), "Timepickerdialog");
    }

    public void showCustomDatePicker() {
        Calendar now = Calendar.getInstance();
        com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd;
        dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                EditActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH));


        dpd.setThemeDark(true);
        dpd.vibrate(false);
        dpd.dismissOnPause(true);
        // if (modeCustomAccentDate.isChecked()) {
        dpd.setAccentColor(Color.parseColor("#03A9F4"));

        //}
        dpd.show(getFragmentManager(), "Datepickerdialog");


    }

    @Override
    public void onResume() {
        super.onResume();
        com.wdullaer.materialdatetimepicker.time.TimePickerDialog tpd = (com.wdullaer.materialdatetimepicker.time.TimePickerDialog) getFragmentManager().findFragmentByTag("Timepickerdialog");
        com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = (com.wdullaer.materialdatetimepicker.date.DatePickerDialog) getFragmentManager().findFragmentByTag("Datepickerdialog");


        if (tpd != null) tpd.setOnTimeSetListener(this);
        if (dpd != null) dpd.setOnDateSetListener(this);

        if( (mTitle != null) && (isEmpty(edittext))){
            edittext.setText(mTitle);
        }


    }

    @Override
    public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog datePickerDialog, int year, int month, int day) {
           Calendar date = Calendar.getInstance();
           date.set(Calendar.YEAR, year);
           date.set(Calendar.MONTH, month);
           date.set(Calendar.DAY_OF_MONTH, day);
           mTask.setDueDate(date.getTime());
           locationSheet.show();
    }

    @Override
    public void onTimeSet(RadialPickerLayout radialPickerLayout, int hour, int min) {
        Calendar time =  Calendar.getInstance();
        time.set(Calendar.HOUR, hour);
        time.set(Calendar.MINUTE, min);
        mTask.setReminderTime(time.getTime());
        locationSheet.show();

    }
}