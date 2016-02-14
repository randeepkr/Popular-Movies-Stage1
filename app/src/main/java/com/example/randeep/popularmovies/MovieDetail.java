package com.example.randeep.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by randeep on 1/2/16.
 */
public class MovieDetail implements Parcelable{

    private int id;
    private String movieName;
    private String posterUrl;
    private String backgroundUrl;
    private String description;
    private String releaseDate;
    private String rating;

    public MovieDetail(JSONObject movieDetailJson){
        try {
            id = movieDetailJson.getInt("id");
            movieName = movieDetailJson.getString("original_title");
            posterUrl = "http://image.tmdb.org/t/p/w300"+movieDetailJson.getString("poster_path");
            backgroundUrl = "http://image.tmdb.org/t/p/original" + movieDetailJson.getString("backdrop_path");
            description = movieDetailJson.getString("overview");
            releaseDate = getYear(movieDetailJson.getString("release_date"));
            rating = movieDetailJson.getString("vote_average");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String getYear(String release_date) {
        String dateArray[] = release_date.split("-");
        return dateArray[0];
    }

    protected MovieDetail(Parcel in) {
        id = in.readInt();
        movieName = in.readString();
        posterUrl = in.readString();
        backgroundUrl = in.readString();
        description = in.readString();
        releaseDate = in.readString();
        rating = in.readString();
    }

    public static final Creator<MovieDetail> CREATOR = new Creator<MovieDetail>() {
        @Override
        public MovieDetail createFromParcel(Parcel in) {
            return new MovieDetail(in);
        }

        @Override
        public MovieDetail[] newArray(int size) {
            return new MovieDetail[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(movieName);
        dest.writeString(posterUrl);
        dest.writeString(backgroundUrl);
        dest.writeString(description);
        dest.writeString(releaseDate);
        dest.writeString(rating);
    }

    public int getId(){
        return id;
    }

    public String getMovieName(){
        return movieName;
    }

    public String getPosterUrl(){
        return posterUrl;
    }

    public String getBackgroundUrl(){
        return backgroundUrl;
    }

    public String getDescription(){
        return description;
    }

    public String getReleaseDate(){
        return releaseDate;
    }

    public String getRating(){
        return rating;
    }
}
