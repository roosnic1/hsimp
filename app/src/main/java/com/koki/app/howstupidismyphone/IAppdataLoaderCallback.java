package com.koki.app.howstupidismyphone;

import java.util.ArrayList;

/**
 * Created by koki on 22/02/15.
 */
public interface IAppdataLoaderCallback {

    public enum LoaderError {
        CONNECTION,
        API,
        STUPIDCODE,
        OTHER,
    }

    public void success(ArrayList<GooglePlayApp> playApps);

    public void onProgress(int total, int done);

    public void failure(LoaderError error);
}
