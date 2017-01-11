package com.allendolph.popularmovies.data.models;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by allendolph on 1/6/17.
 */

public class MovieRequestResult implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        Bundle b = new Bundle();

        b.putInt("page", page);
        b.putInt("totalResults", totalResults);
        b.putInt("totalPages", totalPages);
        b.putParcelableArray("results", results);

        parcel.writeBundle(b);
    }

    public static final Parcelable.Creator<MovieRequestResult> CREATOR
            = new Parcelable.Creator<MovieRequestResult>() {
        public MovieRequestResult createFromParcel(Parcel in) {
            MovieRequestResult m = new MovieRequestResult();


            Bundle b = in.readBundle();
            m.setPage(b.getInt("page"));
            m.setTotalResults(b.getInt("totalResults"));
            m.setTotalPages(b.getInt("totalPages"));
            m.setResults((MovieResult[]) b.getParcelableArray("results"));

            return m;
        }

        public MovieRequestResult[] newArray(int size) {
            return new MovieRequestResult[size];
        }
    };

    /* page */
    @SerializedName("page")
    private int page;

    public int getPage() {
        return page;
    }

    public void setPage(int value) {
        page = value;
    }

    /* total_results */
    @SerializedName("total_results")
    private  int totalResults;

    public int getTotal_Results() {
        return totalResults;
    }

    public void setTotalResults(int value) {
        totalResults = value;
    }

    /* total_pages */
    @SerializedName("total_pages")
    private int totalPages;

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int value) {
        totalResults = value;
    }

    /* results */
    @SerializedName("results")
    private MovieResult[] results;

    public MovieResult[] getResults() {
        return results;
    }

    public void setResults(MovieResult[] value) {
        results = value;
    }

}
