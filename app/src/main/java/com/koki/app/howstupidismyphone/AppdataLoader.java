package com.koki.app.howstupidismyphone;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.crashlytics.android.Crashlytics;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by koki on 22/02/15.
 */
public class AppdataLoader {


    private final static String TAG = "AppdataLoader";
    private final static String APIKEY = "77fdda62ef4cf138d20f114b78785c28";
    private final static String APIBASEURL = "http://api.playstoreapi.com/v1.1";

    private Context mContext;
    private IAppdataLoaderCallback mCallback;

    private Boolean errorHttp = false;
    private Boolean errorJson = false;
    private Boolean errorConnection = false;



    public AppdataLoader(Context context, IAppdataLoaderCallback callback) {
        mContext = context;
        mCallback = callback;
    }

    public void load(ArrayList<String> appList) {
        errorHttp = false;
        errorJson = false;
        errorConnection = false;
        LoadAppdataTask loadAppdataTask = new LoadAppdataTask();
        loadAppdataTask.execute(appList);

    }

    public void load(String packageId) {
        ArrayList<String> appList = new ArrayList<>();
        appList.add(packageId);
        load(appList);
    }



    private class LoadAppdataTask extends AsyncTask<ArrayList<String>,Integer,ArrayList<GooglePlayApp>> {

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mCallback.onProgress(values[0]-1,values[1]);
        }

        @Override
        protected ArrayList<GooglePlayApp> doInBackground(ArrayList<String>... params) {
            ArrayList<GooglePlayApp> playApps = new ArrayList<>();
            ArrayList<String> appList = params[0];
            for(int i=0;i<appList.size();i++) {
                StringBuilder stringBuilder = new StringBuilder();
                String url = String.format("%s/apps/%s?key=%s",APIBASEURL,appList.get(i),APIKEY);
                Log.i(TAG, "Trying to load url: " + url);
                try {
                    DefaultHttpClient httpClient = new DefaultHttpClient();
                    HttpGet httpGet = new HttpGet(url);
                    //HttpPost httpPost = new HttpPost(url);
                    HttpResponse httpResponse = httpClient.execute(httpGet);
                    HttpEntity httpEntity = httpResponse.getEntity();
                    InputStream inputStream = httpEntity.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    inputStream.close();
                } catch(UnsupportedEncodingException | ClientProtocolException e) {
                    e.printStackTrace();
                    Crashlytics.log(0,"http",e.toString());
                    errorHttp = true;
                    stringBuilder = null;
                } catch(IOException e) {
                    e.printStackTrace();
                    errorConnection = true;
                    stringBuilder = null;
                }

                if(stringBuilder != null) {
                    //Log.i(TAG,"StringBuilder value: " + stringBuilder.toString());
                    try {
                        JSONObject appInfo = new JSONObject(stringBuilder.toString());
                        if(appInfo.has("error") && !appInfo.isNull("error")) {
                            Log.i(TAG,"App " + appList.get(i) + " is not in the Playstore");
                        } else {
                            GooglePlayApp app = new GooglePlayApp(appInfo.getString("packageID"),appInfo.getString("appName"),appInfo.optString("category", ""),appInfo.optString("playStoreUrl",""),appInfo.getDouble("score"));

                            JSONArray topReviews = appInfo.optJSONArray("topReviews");
                            if(topReviews != null) {
                                for(int j=0;j<topReviews.length();j++) {
                                    JSONObject review = topReviews.getJSONObject(j);
                                    if(review != null && review.optString("reviewText") != null) {
                                        app.setReview(review.getString("reviewText"));
                                    }
                                }
                            }
                            playApps.add(app);
                        }
                    } catch(JSONException e) {
                        e.printStackTrace();
                        Crashlytics.log(0,"json",e.toString());
                        errorJson = true;
                    }

                }
                publishProgress(appList.size(),i);
            }
            return playApps;
        }

        @Override
        protected void onPostExecute(ArrayList<GooglePlayApp> googlePlayApps) {
            super.onPostExecute(googlePlayApps);
            if(!googlePlayApps.isEmpty()) {
                mCallback.success(googlePlayApps);
            } else {
                if(errorConnection) {
                    mCallback.failure(IAppdataLoaderCallback.LoaderError.CONNECTION);
                } else if(errorHttp) {
                    mCallback.failure(IAppdataLoaderCallback.LoaderError.API);
                } else if(errorJson) {
                    mCallback.failure(IAppdataLoaderCallback.LoaderError.STUPIDCODE);
                } else {
                    mCallback.failure(IAppdataLoaderCallback.LoaderError.OTHER);
                }
            }
        }
    }



}
