package com.allendolph.popularmovies;

/**
 * Created by allendolph on 1/6/17.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.allendolph.popularmovies.data.models.MovieRequestResult;
import com.allendolph.popularmovies.data.models.MovieResult;
import com.allendolph.popularmovies.utility.NetworkUtils;

import java.net.URL;
import com.squareup.picasso.Picasso;

/**
 * {@link MovieAdapter} exposes a list of movies to a
 * {@link android.support.v7.widget.RecyclerView}
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private MovieRequestResult mMovieRequestResult;

    MovieAdapterOnClickHandler mClickHandler;

    public interface MovieAdapterOnClickHandler {
        void onClick(MovieResult movieResult);
    }

    public MovieAdapter(MovieAdapterOnClickHandler movieAdapterOnClickHandler) {
        mClickHandler = movieAdapterOnClickHandler;
    }

    /**
     * Cache of the children views for a forecast list item.
     */
    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView mImageView;

        public MovieAdapterViewHolder(View view) {
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.iv_movie_poster_image);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            MovieResult movieResult = mMovieRequestResult.getResults()[adapterPosition];
            mClickHandler.onClick(movieResult);
        }
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder movieAdapterViewHolder, int position) {
        MovieResult movieForThisPosition = mMovieRequestResult.getResults()[position];

        // we need to get a url for the movie poster to pass to picaso
        URL imageUrl = NetworkUtils
                .buildUrlForImage(movieForThisPosition.getPosterPath(), MainActivity.IMAGE_SIZE);

        Context context = movieAdapterViewHolder.mImageView.getContext();
        Picasso.with(context)
                .load(imageUrl.toString())
                .into(movieAdapterViewHolder.mImageView);
    }

    @Override
    public int getItemCount() {
        if (null == mMovieRequestResult) return 0;
        int count = mMovieRequestResult.getResults().length;
        return count;

    }

    /**
     * This method is used to set the movie result data on a MovieAdapter if we've already
     * created one.
     *
     * @param movieData The new movie request result data to be displayed.
     */
    public void setMovieResultData(MovieRequestResult movieData) {
        mMovieRequestResult = movieData;
        notifyDataSetChanged();
    }
}
