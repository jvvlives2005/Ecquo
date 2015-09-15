package abassawo.c4q.nyc.ecquo.Activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.support.v7.app.AlertDialog;
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
import com.flipboard.bottomsheet.commons.ImagePickerSheetView;
import com.flipboard.bottomsheet.commons.MenuSheetView;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.truizlop.fabreveallayout.FABRevealLayout;
import com.truizlop.fabreveallayout.OnRevealChangeListener;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import abassawo.c4q.nyc.ecquo.Fragments.DatePickerFragment;
import abassawo.c4q.nyc.ecquo.Fragments.TimePickerFragment;
import abassawo.c4q.nyc.ecquo.Model.sPlanner;
import abassawo.c4q.nyc.ecquo.Model.Task;
import abassawo.c4q.nyc.ecquo.R;
import butterknife.Bind;
import butterknife.ButterKnife;

public class EditActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
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
    @Bind(R.id.time_reminder_fab_button) FloatingActionButton timeFab;
    @Bind(R.id.location_reminder_fab_button) FloatingActionButton locationFab;

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
    private DatePickerFragment dateDialog;
    private TimePickerFragment timeDialog;
    private GoogleApiClient mClient;
    private Location mCurrentLocation;
    private static final long MIN_TIME = 400;
    private static final float MIN_DISTANCE = 1000;
    private static final int REQUEST_STORAGE = 0;
    private static final int REQUEST_IMAGE_CAPTURE = REQUEST_STORAGE + 1;
    private static final int REQUEST_LOAD_IMAGE = REQUEST_IMAGE_CAPTURE + 1;
    private static final int REQUEST_TIME = 3;
    private static final int REQUEST_DATE = REQUEST_TIME + REQUEST_TIME;
    public static final int REQUEST_LOCATION = REQUEST_DATE + REQUEST_TIME;
    FragmentManager fm;

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
        fm = getSupportFragmentManager();


    }

    public void initViews() {
        setupActionBar();
        configureFABReveal(fabRevealLayout);
        setupDateSheets();
    }

    public void setupListeners() {
        timeFab.setOnClickListener(this);
        locationFab.setOnClickListener(this);
        priorityRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                hideKeyboard();
                mTask.setPriority(rating);
                Log.d(String.valueOf(rating), TAG);
                Log.d(mTask.toString(), TAG);
                dateSheet.show();
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
                    mTask = new Task(s.toString(), ctx);
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
        dateDialog = DatePickerFragment.newInstance(mTask.getDueDate());
        timeDialog = TimePickerFragment.newInstance(mTask.getDueDate());
        dateSheet = new BottomSheet.Builder(this).title("Due Date").sheet(R.menu.menu_date).listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                switch (id) {
                    case R.id.today_item:
                        mTask.setDueToday();
                        taskList.add(mTask);
                        Log.d(mTask.toString(), "due date test");
                        prepareBackTransition(fabRevealLayout);
                        startActivity(new Intent(EditActivity.this, MainActivity.class));
                        break;
                    case R.id.tomorrow:
                        mTask.setDueTomorrow(ctx); //setDueTomorrow fixme
                        taskList.add(mTask);
                        Log.d(mTask.toString(), "due date test");
                        prepareBackTransition(fabRevealLayout);
                        startActivity(new Intent(EditActivity.this, MainActivity.class));
                        break;
                    case R.id.choosedate:
                        fm.beginTransaction().add(dateDialog, "DATE").commit();
                        break;
                    case R.id.nextweek:
                        mTask.setDueinOneWeek(ctx);
                        Log.d(mTask.toString(), "due date test");
                        prepareBackTransition(fabRevealLayout);
                        startActivity(new Intent(EditActivity.this, MainActivity.class));
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

    private void showGalleryPickerSheetView(){
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri selectedImage = null;
            if (requestCode == REQUEST_LOAD_IMAGE && data != null) {
                selectedImage = data.getData();
                if (selectedImage == null) {
                    //generic error
                }
            } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
                // Do something with imagePath
                selectedImage = cameraImageUri;
            } else if(requestCode == REQUEST_LOCATION){
//                mTask.setLocation(data.getExtras().get("LOCATION"));
            }


            if (selectedImage != null) {
                showSelectedImage(selectedImage);
            } else {
                //genericError();
            }

            if (requestCode == REQUEST_DATE) {
                //Date date = (Date) data.getExtras().get("EXTRA_DATE");
                Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
                mTask.setDueDate(date);
                mTask.setDueDate(date);
                // updateDate();
            }  else if (requestCode == REQUEST_TIME) {
                Date date = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
                mTask.setReminderTime(date);
                // updateDate();

        }
        } else if (resultCode != Activity.RESULT_OK) {
            return;
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
                if (!bottomSheetLayout.isSheetShowing()){
                showGalleryPickerSheetView();
                } else {
                    bottomSheetLayout.dismissSheet();
                }
                break;
            case R.id.time_reminder_fab_button:
                fm.beginTransaction().add(timeDialog, "TIME").commit();
                break;
            case R.id.location_reminder_fab_button:
                Intent locationIntent = new Intent(this, MapViewActivity.class);
                startActivityForResult(locationIntent, REQUEST_LOCATION);

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


    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

    }


}

