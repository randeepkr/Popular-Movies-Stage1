package com.example.randeep.popularmovies;


import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by randeep on 12/30/15.
 */
public class MoviesListFragment extends Fragment {


    @Bind(R.id.movies_list)
    RecyclerView moviesListView;
    ProgressBar progressBar;
    ArrayList<MovieDetail> movieDetailArrayList;
    GridLayoutAdapter gridLayoutAdapter;
    final String TAG = MoviesListFragment.class.getSimpleName();
    final String POPULARITY = "popularity.desc";
    final String HIGHEST_RATING = "vote_average.desc";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        movieDetailArrayList = new ArrayList<>();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.movies_list_fragment, container, false);
        ButterKnife.bind(this, rootView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridLayoutAdapter = new GridLayoutAdapter(getActivity(),movieDetailArrayList);
        moviesListView.setLayoutManager(gridLayoutManager);
        moviesListView.setAdapter(gridLayoutAdapter);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMoviesList();
    }

    public class FetchMovieListTask extends AsyncTask<String, String, ArrayList> {


        @Override
        protected ArrayList<MovieDetail> doInBackground(String... params) {
            HttpURLConnection httpURLConnection = null;
            BufferedReader bufferedReader = null;
            String moviesListStr = "";
            String MOVIESLIST_URL = "http://api.themoviedb.org/3/discover/movie?";
            String SORTBY_PARAM = "sort_by";
            String API_KEY = "api_key";
            try {

                Uri builtUri = Uri.parse(MOVIESLIST_URL)
                        .buildUpon()
                        .appendQueryParameter(SORTBY_PARAM, params[0])
                        .appendQueryParameter(API_KEY, BuildConfig.API_KEY)
                        .build();

                URL url = new URL(builtUri.toString());
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();

                InputStream inputStream = httpURLConnection.getInputStream();
                StringBuffer stringBuffer = new StringBuffer();
                if (inputStream == null) {

                    return null;
                }

                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = bufferedReader.readLine()) != null) {

                    stringBuffer.append(line + "\n");
                }

                if (stringBuffer.length() == 0) {

                    return null;
                }
                moviesListStr = stringBuffer.toString();

                movieDetailArrayList = getMovieslistFromJson(moviesListStr);

            } catch (Exception e) {

            }
            return movieDetailArrayList;


        }

        @Override
        protected void onPostExecute(ArrayList arrayList) {
            super.onPostExecute(arrayList);

            gridLayoutAdapter.movieDetailArrayList.clear();
            gridLayoutAdapter.movieDetailArrayList = arrayList;
            gridLayoutAdapter.notifyDataSetChanged();

        }

        public ArrayList<MovieDetail> getMovieslistFromJson(String moviesListStr) {
            ArrayList<MovieDetail> movieDetailList = new ArrayList<>();
            try {
                JSONObject moviesListJson = new JSONObject(moviesListStr);
                JSONArray moviesListArray = moviesListJson.getJSONArray("results");

                for (int i = 0; i < moviesListArray.length(); i++) {
                    movieDetailList.add(new MovieDetail(moviesListArray.getJSONObject(i)));
                }

                return movieDetailList;

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    private void updateMoviesList(){
        FetchMovieListTask fetchMovieListTask = new FetchMovieListTask();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortBy = sharedPreferences.getString(getString(R.string.pref_sorting_key),
                getString(R.string.pref_sorting_pupolarity_label));
        String sorting = sortBy.equalsIgnoreCase("popularity")?POPULARITY:HIGHEST_RATING;
        fetchMovieListTask.execute(sorting);
    }
}
