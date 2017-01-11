package com.allendolph.popularmovies.data.models;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by allendolph on 1/6/17.
 */

public class MovieResult implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        Bundle b = new Bundle();

        b.putString("posterPath", posterPath);
        b.putBoolean("adult", adult);
        b.putString("overview", overview);
        b.putString("relaseDate", releaseDate);
        b.putIntArray("genreIds", genreIds);
        b.putLong("id", id);
        b.putString("originalTitle", originalTitle);
        b.putString("originalLanguage", originalLanguage);
        b.putString("title", title);
        b.putString("backdropPath", backdropPath);
        b.putDouble("popularity", popularity);
        b.putInt("voteCount", voteCount);
        b.putBoolean("video", video);
        b.putDouble("voteAverage", voteAverage);

        parcel.writeBundle(b);
    }

    public static final Parcelable.Creator<MovieResult> CREATOR
            = new Parcelable.Creator<MovieResult>() {
        public MovieResult createFromParcel(Parcel in) {
            MovieResult m = new MovieResult();


            Bundle b = in.readBundle();
            m.setPosterPath(b.getString("posterPath"));
            m.setAdult(b.getBoolean("adult"));
            m.setOverview(b.getString("overview"));
            m.setReleaseDate(b.getString("releaseDate"));
            m.setGenreIds(b.getIntArray("genreIds"));
            m.setId(b.getLong("id"));
            m.setOriginalTitle(b.getString("originalTitle"));
            m.setOriginalLanguage(b.getString("originalLanguage"));
            m.setTitle(b.getString("title"));
            m.setBackdropPath(b.getString("backdropPath"));
            m.setPopularity(b.getDouble("popularity"));
            m.setVoteCount(b.getInt("voteCount"));
            m.setVideo(b.getBoolean("video"));
            m.setVoteAverage(b.getDouble("voteAverage"));

            return m;
        }

        public MovieResult[] newArray(int size) {
            return new MovieResult[size];
        }
    };


    @SerializedName("poster_path")
    private String posterPath;

    public String getPosterPath() {
        return  posterPath;
    }

    public void setPosterPath(String value) {
        posterPath = value;
    }

    @SerializedName("adult")
    private Boolean adult;

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean value) {
        adult = value;
    }

    @SerializedName("overview")
    private String overview;

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    @SerializedName("release_date")
    private String releaseDate;

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getReleaseYear() {
        if(releaseDate != null && releaseDate.length() >=4) {
            return releaseDate.substring(0, 4);
        } else {
            return "";
        }
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @SerializedName("genre_ids")
    private int[] genreIds;

    public int[] getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(int[] genreIds) {
        this.genreIds = genreIds;
    }

    @SerializedName("id")
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @SerializedName("original_title")
    private String originalTitle;

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    @SerializedName("original_language")
    private String originalLanguage;

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    @SerializedName("title")
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @SerializedName("backdrop_path")
    private String backdropPath;

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    @SerializedName("popularity")
    private double popularity;

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    @SerializedName("vote_count")
    private int voteCount;

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    @SerializedName("video")
    private Boolean video;

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    @SerializedName("vote_average")
    private double voteAverage;

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }


}
