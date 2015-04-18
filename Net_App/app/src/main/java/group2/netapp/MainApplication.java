package group2.netapp;

import com.matesnetwork.callverification.Cognalys;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.PushService;

import android.app.Application;

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();

        Parse.initialize(this, "oS5B5on8xGqQFPDHvtR4jgmXsemYbdBq14DfWAzo", "YiJqph6IHfZOIBIQhSVmFtOMgoNABQdem3teoEgA");
        PushService.setDefaultPushCallback(this, MainActivity.class);
        Cognalys.enableAnalytics(getApplicationContext(), true, true);
        //ParseInstallation.getCurrentInstallation().saveInBackground();
        //PushService.subscribe(this, "Broadcast", MainActivity.class);
    }
}




