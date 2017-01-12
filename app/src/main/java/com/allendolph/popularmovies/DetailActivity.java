package com.allendolph.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.allendolph.popularmovies.data.models.MovieResult;
import com.allendolph.popularmovies.utility.NetworkUtils;


import com.squareup.picasso.Picasso;

import java.net.URL;
import java.text.SimpleDateFormat;

/**
 * Activity to show the details of a selected movie
 *
 *
 * @author Allen Dolph
 *
 */
public class DetailActivity extends AppCompatActivity {

    private static final String THUMBNAIL_SIZE = "w342";
    private static String RATING_SUFFIX = "/10";
    private ImageView mImageView;
    private TextView mTitleTextView;
    private TextView mReleaseDateTextView;
    private TextView mAvergeRatingTextView;
    private TextView mOverviewTextView;

    private MovieResult mMovieResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mImageView = (ImageView) findViewById(R.id.iv_movie_poster_detail_image);
        mTitleTextView = (TextView) findViewById(R.id.tv_movie_title);
        mReleaseDateTextView = (TextView) findViewById(R.id.tv_movie_release_date) ;
        mAvergeRatingTextView = (TextView) findViewById(R.id.tv_movie_avg_rating);
        mOverviewTextView = (TextView) findViewById(R.id.tv_movie_overview);

        Intent intent = getIntent();

        mMovieResult = (MovieResult) intent.getParcelableExtra(MainActivity.MOVIE_RESULT_BUNDLE);

        URL imageUrl = NetworkUtils.buildUrlForImage(mMovieResult.getPosterPath(), THUMBNAIL_SIZE);
        Context context = mImageView.getContext();
        Picasso.with(context)
                .load(imageUrl.toString())
                .into(mImageView);

        mTitleTextView.setText(mMovieResult.getTitle());
        mReleaseDateTextView.setText(mMovieResult.getReleaseYear());
        mAvergeRatingTextView.setText(mMovieResult.getVoteAverage() + RATING_SUFFIX);
        mOverviewTextView.setText(mMovieResult.getOverview());
    }
}
