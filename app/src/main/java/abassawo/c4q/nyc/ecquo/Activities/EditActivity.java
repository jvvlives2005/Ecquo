package abassawo.c4q.nyc.ecquo.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.cocosw.bottomsheet.BottomSheet;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.commons.IntentPickerSheetView;

import abassawo.c4q.nyc.ecquo.Model.Task;
import abassawo.c4q.nyc.ecquo.R;
import butterknife.Bind;
import butterknife.ButterKnife;

public class EditActivity extends AppCompatActivity {
    @Bind(R.id.edit_task_title)
    EditText edittext;




    private BottomSheet dateSheet;
    private BottomSheet prioritySheet;
    private BottomSheet locationSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        initSheets();
        ButterKnife.bind(this);



        edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dateSheet.show();

            }

            @Override
            public void afterTextChanged(Editable s) {
                Task Task = new Task(edittext.getText().toString());
            }
        });


    }


    public void initSheets(){
        dateSheet = new BottomSheet.Builder(this).title("Set a Due Date").sheet(R.menu.menu_date).listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                prioritySheet.show();
//                switch (id) {
//
//                }
            }
        }).build();

        prioritySheet = new BottomSheet.Builder(this).title("Set a Due Date").sheet(R.menu.menu_priority).listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                locationSheet.show();
//                switch (id) {
//
//                }
            }
        }).build();

        locationSheet = new BottomSheet.Builder(this).title("Set a Due Date").sheet(R.menu.menu_location).listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
//                switch (id) {
//
//                }
            }
        }).build();






    }
}

