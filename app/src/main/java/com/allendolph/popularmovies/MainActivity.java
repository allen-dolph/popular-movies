package com.allendolph.popularmovies;

/**
 * Created by allendolph on 1/6/17.
 */

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.net.URL;

import com.allendolph.popularmovies.data.models.MovieRequestResult;
import com.allendolph.popularmovies.data.models.MovieResult;
import com.allendolph.popularmovies.utility.MovieDbOrgJsonUtils;
import com.allendolph.popularmovies.utility.NetworkUtils;
import com.squareup.picasso.Picasso;


/**
 * Activity that the application starts into
 *
 * This activity shows a list of movie posters, populated from a call
 * to the moviedb.org api
 *
 * @author Allen Dolph
 *
 */
public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private static final String TAG = MainActivity.class.getSimpleName();

    public static final String MOVIE_RESULT_BUNDLE = "movieResult";
    public static final String IMAGE_SIZE = "w500";

    private TextView mErrorMessageDisplay;
    private RecyclerView mRecyclerView;
    private ProgressBar mLoadingIndicator;

    private MovieAdapter mMovieAdapter;

    private String mSort = "popular";

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mContext = this;

        /* This is the Recycler view to hold our list of movie poster */
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_movies);

        /* This TextView is used to display errors and will be hidden if there are no errors */
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        /* This is the indicator that will show when the movie data is loading */
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        /*
         * LinearLayoutManager can support HORIZONTAL or VERTICAL orientations. The reverse layout
         * parameter is useful mostly for HORIZONTAL layouts that should reverse for right to left
         * languages.
         */
        GridLayoutManager layoutManager
                = new GridLayoutManager(this, 2);

        mRecyclerView.setLayoutManager(layoutManager);

        /*
         * Use this setting to improve performance if you know that changes in content do not
         * change the child layout size in the RecyclerView
         */
        //mRecyclerView.setHasFixedSize(true);

        /*
         * The MovieAdapter is responsible for linking our movie data with the Views that
         * will end up displaying our movie posters.
         */
        mMovieAdapter = new MovieAdapter(this);

        /* Setting the adapter attaches it to the RecyclerView in our layout. */
        mRecyclerView.setAdapter(mMovieAdapter);

        loadMovies();
    }

    /**
     * Creates a new instance of the FetchMoviesTask to load movie data on a background
     * thread
     *
     * @see FetchMoviesTask
     *
     */
    private void loadMovies() {
        new FetchMoviesTask().execute(mSort);
    }

    /**
     * A Class that extends AsyncTask to load movie data using themoviedb.org api
     *
     *
     * @author Allen Dolph
     * @see AsyncTask
     */
    public class FetchMoviesTask extends AsyncTask<String, Void, MovieRequestResult> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected MovieRequestResult doInBackground(String... params) {
            /* If there's no tag, there's nothing to query for. */
            if (params.length == 0) {
                return null;
            }

            String tag = params[0];
            URL movieRequestUrl = NetworkUtils.buildUrl(tag, mContext);

            try {
                String jsonMovieResponse = NetworkUtils
                        .getResponseFromHttpUrl(movieRequestUrl);

                MovieRequestResult movieRequestResult = MovieDbOrgJsonUtils
                        .getMovieRequestResulFromJson(jsonMovieResponse);

                return movieRequestResult;

            } catch (Exception e) {
                Log.v(TAG, e.getMessage());
                String t = e.getMessage();
                return null;
            }
        }

        @Override
        protected void onPostExecute(MovieRequestResult result) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (result != null) {
                showMovieDataView();
                mMovieAdapter.setMovieResultData(result);
            } else {
                showErrorMessage();
            }
        }
    }

    /**
     * This method will make the View for the movie data visible and
     * hide the error message.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't
     * need to check whether each view is currently visible or invisible.
     */
    private void showMovieDataView() {
        /* First, make sure the error is invisible */
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        /* Then, make sure the weather data is visible */
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * This method will make the error message visible and hide the movie
     * View.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't
     * need to check whether each view is currently visible or invisible.
     */
    private void showErrorMessage() {
        /* First, hide the currently visible data */
        mRecyclerView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movies, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_sort_highest_rated) {
            mSort = "top_rated";
            mMovieAdapter.setMovieResultData(null);
            loadMovies();
            return true;
        } else if (id == R.id.action_sort_popularity) {
            mSort = "popular";
            mMovieAdapter.setMovieResultData(null);
            loadMovies();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(MovieResult movieResult) {
        Log.v(TAG, movieResult.getTitle() + " clicked");

        Context context = this;
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);

        /* Add the clicked Movie Result to the intent as an extra.  We can
         * do this since MovieResult implements the parceable interface
         */
        intentToStartDetailActivity.putExtra(MOVIE_RESULT_BUNDLE, movieResult);

        startActivity(intentToStartDetailActivity);
    }
}
