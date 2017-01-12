package com.allendolph.popularmovies.utility;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by allendolph on 1/6/17.
 */

/**
 * Utility class to handle network communication
 * <p>
 * This handles the both the communication to themoviedb.org api, as well has the the call to
 * tmbdb.org to pull in the image thumbnails
 *
 * @author Allen Dolph
 *
 */
public class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String MOVIES_BASE_URL = "https://api.themoviedb.org/3/movie";

    private static final String API_KEY_PARAM = "api_key";
    private static final String API_KEY = "";

    private static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";

    /**
     * Builds the URL used to talk to themoviedb.org server using a tagPath.
     *
     * @param tagPath The tag from themoviedb.org that will be queried for. This will allow for the
     *                simple sort of by popularity, or by top rated
     * @return The URL to use to query the themoviedb.org server.
     */
    public static URL buildUrl(String tagPath) {
        Uri builtUri = Uri.parse(MOVIES_BASE_URL).buildUpon()
                .appendPath(tagPath)
                .appendQueryParameter(API_KEY_PARAM, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    /**
     * Builds the URL used to talk to tmbdb.org server using a imagePath to construct the
     * url of the image.
     *
     * @param imagePath The image path will be queried for.
     * @return The URL to use to query the tmdb.org server.
     */
    public static URL buildUrlForImage(String imagePath, String imageSize) {
        Uri builtUri = Uri.parse(IMAGE_BASE_URL).buildUpon()
                .appendPath(imageSize)
                .appendEncodedPath(imagePath)
                //.appendPath(imagePath)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
