package abassawo.c4q.nyc.ecquo.user.inputs;

import android.os.Bundle;

import abassawo.c4q.nyc.ecquo.R;

/**
 * Created by Hans on 9/12/15.
 */
public class HelpFeedActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_feedback_layout);
        mToolBar = activateToolbar();
        setUpNavigationDrawer();
    }
}
