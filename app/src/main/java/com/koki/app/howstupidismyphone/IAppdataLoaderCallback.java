package com.koki.app.howstupidismyphone;

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

    public void success();

    public void onProgress(int total, int done);

    public void failure(LoaderError error);
}
