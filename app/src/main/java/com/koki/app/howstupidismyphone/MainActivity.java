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


public class MainActivity extends ActionBarActivity implements IAppdataLoaderCallback {

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
        //for(int i=0;i<10;i++) {
            appList.add(pkgAppsList.get(i).activityInfo.packageName);
            //Log.i(TAG,"App: " + pkgAppsList.get(i).activityInfo.packageName);
            //Log.i(TAG,"App: " + pkgAppsList.get(i).filter.toString());
        }
        AppdataLoader appdataLoader = new AppdataLoader(this,this);
        appdataLoader.load(appList);
        //Log.i(TAG,"All Apps: " + appList.toString());
    }


    @Override
    public void success(ArrayList<GooglePlayApp> playApps) {
        Log.i(TAG,playApps.toString());
        double totalScore = 0;
        for(int i=0;i<playApps.size();i++) {
            totalScore += playApps.get(i).getScore();
        }
        totalScore = totalScore / playApps.size();

    }

    @Override
    public void onProgress(int total, int done) {
        Log.i(TAG,"Done " + done + " of " + total);
        pgLoading.setMax(total);
        pgLoading.setProgress(done);
    }

    @Override
    public void failure(LoaderError error) {
        Log.i(TAG,"Error :" + error.toString());
    }
}
