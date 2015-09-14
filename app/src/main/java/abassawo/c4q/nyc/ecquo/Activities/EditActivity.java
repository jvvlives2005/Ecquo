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

    @Bind(R.id.fab_reveal_layout_test)
    FABRevealLayout fabRevealLayout;
    private String sheetTitle;
    private LocationManager locationManager;
    private BottomSheet dateSheet;
    private BottomSheet prioritySheet;
    @Bind(R.id.bottomsheet)
    BottomSheetLayout bottomSheetLayout;
    private BottomSheet locationSheet;
    private BottomSheet reminderSheet;
    private BottomSheet pictureDialogSheet;
    private Uri imageUri;
    private int CAPTURE_IMAGE = 9;
    private String TAG = "EditActivity";

    private Context ctx;
    private DatePicker datePicker;
    private static final String DIALOG_DATE = "date";
    private Task mTask; //TASK BEING EDITED.
    public static List<Task> todayList;
    public static List<Task> taskList;
    private DatePickerFragment dateDialog;
    private GoogleApiClient mClient;
    private Location mCurrentLocation;
    private static final long MIN_TIME = 400;
    private static final float MIN_DISTANCE = 1000;
    private static final int REQUEST_STORAGE = 0;
    private static final int REQUEST_IMAGE_CAPTURE = REQUEST_STORAGE + 1;
    private static final int REQUEST_LOAD_IMAGE = REQUEST_IMAGE_CAPTURE + 1;

    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
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
        setupLocationSheet();
        setupReminderSheet();
        setupPicturePickerSheet();
    }

    public void setupListeners() {
        priorityRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                hideKeyboard();
                mTask.setPriority(rating);
                Log.d(String.valueOf(rating), TAG);
                Log.d(mTask.toString(), TAG);
                dateSheet.show();
                prepareBackTransition(fabRevealLayout);
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
                secondaryView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hideKeyboard();
                        //dateSheet.show();
                        //showDialogSheet();
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

    public void setupPicturePickerSheet() {
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
        if (resultCode == RESULT_OK) {
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
                        locationSheet.show();       //No reminders presented if due today.
                        break;
                    case R.id.tomorrow:
                        mTask.setDueTomorrow(ctx); //setDueTomorrow fixme
                        taskList.add(mTask);
                        Log.d(mTask.toString(), "due date test");
                        reminderSheet.show();      //Reminders are presented.
                        break;
                    case R.id.choosedate:
                        fm.beginTransaction().add(dateDialog, "DATE").commit();
                        break;
                    case R.id.nextweek:
                        mTask.setDueinOneWeek(ctx);
                        Log.d(mTask.toString(), "due date test");
                        reminderSheet.show();
                        break;
                    default:break;
                }

            }
        }).build();


    }

    @Override
    public void onProvideAssistData(Bundle data) {
        super.onProvideAssistData(data);
    }



    public void setupLocationSheet() {
        locationSheet = new BottomSheet.Builder(this).title("Location").sheet(R.menu.menu_location).listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(ctx, MapViewActivity.class);
                switch (id) {
                    case R.id.saved_places:

                        startActivity(new Intent(EditActivity.this, MapViewActivity.class)); //fixme
                        break;
                    case R.id.new_place:
                        startActivity(new Intent(EditActivity.this, MapViewActivity.class)); //fixme
                        break;
                    default: startActivity(new Intent(EditActivity.this, MainActivity.class));
                }
            }
        }).build();
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
        Uri cameraImageUri = Uri.fromFile(imageFile);
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

    public void setupReminderSheet() {
        reminderSheet = new BottomSheet.Builder(this).title("Remind Me").sheet(R.menu.menu_reminder).listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                switch (id) {
                    default:
                        locationSheet.show();
                }


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
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(ctx, MainActivity.class));
            finish();
        } else if (item.getItemId() == R.id.menu_item_label) {
            startActivity(new Intent(ctx, LabelPicker.class));

        }
        return super.onOptionsItemSelected(item);
    }

    public void startCameraIntent() {
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
                showGalleryPickerSheetView();
                //pictureDialogSheet.show();
                break;

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

