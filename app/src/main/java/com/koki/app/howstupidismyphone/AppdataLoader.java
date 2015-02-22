package com.koki.app.howstupidismyphone;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;

/**
 * Created by koki on 22/02/15.
 */
public class AppdataLoader {


    private final static String TAG = "AppdataLoader";
    private final static String APIKEY = "f4f96a0578687afdf71c5aecea9e329c";
    private final static String APIBASEURL = "http://api.playstoreapi.com/v1.1/";

    private Context mContext;
    private IAppdataLoaderCallback mCallback;



    public AppdataLoader(Context context, IAppdataLoaderCallback callback) {
        mContext = context;
        mCallback = callback;
    }

    public void load(ArrayList<String> appList) {
        LoadAppdataTask loadAppdataTask = new LoadAppdataTask();
        loadAppdataTask.execute(appList);

    }

    public void load(String packageId) {
        ArrayList<String> appList = new ArrayList<>();
        appList.add(packageId);
        load(appList);
    }



    private class LoadAppdataTask extends AsyncTask<ArrayList<String>,Integer,String> {

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mCallback.onProgress(values[0],values[1]);
        }

        @Override
        protected String doInBackground(ArrayList<String>... params) {
            
            for(int i=0;i<params[0].size();i++) {

            }

            return null;
        }
    }



}
