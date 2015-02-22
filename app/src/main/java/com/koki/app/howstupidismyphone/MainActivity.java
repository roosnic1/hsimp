package com.koki.app.howstupidismyphone;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;


public class MainActivity extends ActionBarActivity {

    private final static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);

        getAllInstalledApps();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void getAllInstalledApps() {
        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        final List<ResolveInfo> pkgAppsList = this.getPackageManager().queryIntentActivities(mainIntent,0);

        ArrayList<String> appList = new ArrayList<>();

        for(int i=0;i<pkgAppsList.size();i++) {
            appList.add(pkgAppsList.get(i).activityInfo.packageName);
            //Log.i(TAG,"App: " + pkgAppsList.get(i).activityInfo.packageName);
            //Log.i(TAG,"App: " + pkgAppsList.get(i).filter.toString());
        }

        Log.i(TAG,"All Apps: " + appList.toString());
    }
}
