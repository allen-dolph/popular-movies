package com.allendolph.popularmovies.utility;

import com.allendolph.popularmovies.data.models.MovieRequestResult;

import com.google.gson.Gson;

/**
 * Created by allendolph on 1/6/17.
 */


/**
 * Utility class to convert json results from themoviedb.org to data model objects
 *
 * @author Allen Dolph
 *
 */
public class MovieDbOrgJsonUtils {

    /**
     * Takes a json string representation of a query result and returns it as
     * a result object
     *
     * @param jsonResult The json query results
     * @return MovieRequestResult which contains of list of MovieResult items
     * @see com.allendolph.popularmovies.data.models.MovieResult
     */
    public static MovieRequestResult getMovieRequestResulFromJson(String jsonResult) {
        Gson gson = new Gson();

        MovieRequestResult movieRequestResult = gson.fromJson(jsonResult, MovieRequestResult.class);

        return movieRequestResult;
    }
}
