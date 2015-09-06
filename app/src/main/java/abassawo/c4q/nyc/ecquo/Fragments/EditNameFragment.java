package abassawo.c4q.nyc.ecquo.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import abassawo.c4q.nyc.ecquo.Model.Task;
import abassawo.c4q.nyc.ecquo.R;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by c4q-Abass on 9/4/15.
 */

public class EditNameFragment extends Fragment{
   @Bind(R.id.new_task_tv)
   TextView textview;
    @Bind(R.id.new_task_edittext)
    EditText edittext;
    private Task newTask;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_name_edit, container, false);
        ButterKnife.bind(this, view);
        initState();
        return view;
    }

    public void initState(){
        if(edittext.getText().length()>0){
            textview.setAlpha(1);

            //textview.setVisibility(View.VISIBLE);
        }

        edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                newTask = new Task(textview.getText().toString());
                //send this to parent activity to be sent to next fragment.
            }
        });

    }

}
