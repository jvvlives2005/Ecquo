package abassawo.c4q.nyc.ecquo.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import abassawo.c4q.nyc.ecquo.R;

public class LauncherActivity extends Activity {

    private static final int SPLASHTIME = 3000;
    private static final int STOPSPLASH = 0;
    private Handler mSplashHandler;

    // Handler start
    {
        mSplashHandler = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                super.handleMessage(msg);
                switch (msg.what)
                {
                    case STOPSPLASH:
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                }
            }
        };
    }// Handler end

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        // to start the handler
        mSplashHandler.sendEmptyMessageDelayed(STOPSPLASH, SPLASHTIME);
    }

}