package com.koki.app.howstupidismyphone;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by koki on 22/02/15.
 */
public class GooglePlayApp implements Serializable {

    private String packageId;
    private String appName;
    private String category;
    private String playUrl;

    private double score;

    private Bitmap logo;

    private ArrayList<String> reviews;


    public GooglePlayApp(String packageId, String appName, String category, String playUrl, double score, Bitmap logo, ArrayList<String> reviews) {
        this.packageId = packageId;
        this.appName = appName;
        this.category = category;
        this.playUrl = playUrl;
        this.score = score;
        this.logo = logo;
        this.reviews = reviews;
    }

    public GooglePlayApp(String packageId, String appName, String category, String playUrl, double score) {
        this.packageId = packageId;
        this.appName = appName;
        this.category = category;
        this.playUrl = playUrl;
        this.score = score;
        this.logo = null;
        this.reviews = new ArrayList<>();
    }


    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Bitmap getLogo() {
        return logo;
    }

    public void setLogo(Bitmap logo) {
        this.logo = logo;
    }

    public ArrayList<String> getReviews() {
        return reviews;
    }

    public String getReview(int id) {
        return reviews.get(id);
    }

    public void setReviews(ArrayList<String> reviews) {
        this.reviews = reviews;
    }

    public void setReview(String review) {
        if(this.reviews == null) {
            this.reviews = new ArrayList<String>();
        }
        this.reviews.add(review);
    }

    @Override
    public String toString() {
        return "GooglePlayApp{" +
                "packageId='" + packageId + '\'' +
                ", appName='" + appName + '\'' +
                ", category='" + category + '\'' +
                ", playUrl='" + playUrl + '\'' +
                ", score=" + score +
                ", logo=" + logo +
                ", reviews=" + reviews +
                '}';
    }
}
