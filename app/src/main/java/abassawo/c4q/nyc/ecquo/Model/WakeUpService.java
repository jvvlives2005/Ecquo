package abassawo.c4q.nyc.ecquo.Model;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

/**
 * Created by c4q-Abass on 9/9/15.
 */
public class WakeUpService extends IntentService {
    private static final int WAKEUP_INTERVAL = 1000 * 60;


    public WakeUpService() {
        super("MyIntentService");
    }

    public static Intent newIntent(Context context){
        return new Intent(context, WakeUpService.class);
    }
    @Override
    protected void onHandleIntent(Intent intent) {

    }

    public static void setServiceAlarm(Context context, boolean isOn){
        Intent i = WakeUpService.newIntent(context);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, i, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if(isOn){
            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), WAKEUP_INTERVAL  , pendingIntent);
        } else {
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
        }

    }
}
