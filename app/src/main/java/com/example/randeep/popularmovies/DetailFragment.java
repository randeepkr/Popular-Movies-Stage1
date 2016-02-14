package com.example.randeep.popularmovies;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.florent37.picassopalette.BitmapPalette;
import com.github.florent37.picassopalette.PicassoPalette;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by randeep on 1/2/16.
 */
public class DetailFragment extends Fragment {

    @Bind(R.id.poster_image)
    ImageView imgPoster;

    @Bind(R.id.movie_name)
    TextView txtMovieName;

    @Bind(R.id.release_date)
    TextView txtReleaseDate;

    @Bind(R.id.rating)
    TextView txtRating;

    @Bind(R.id.description)
    TextView txtDescription;

    MovieDetail movieDetail;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.detail_fragment, container, false);
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        ButterKnife.bind(this, rootView);
        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            movieDetail = bundle.getParcelable("MOVIE_DETAIL");
        }

        Picasso.with(getActivity())
                .load(movieDetail.getPosterUrl())
                .into(imgPoster, PicassoPalette.with(movieDetail.getPosterUrl(), imgPoster)
                        .use(PicassoPalette.Profile.VIBRANT)
                        .intoCallBack(new PicassoPalette.CallBack() {
                            @Override
                            public void onPaletteLoaded(Palette palette) {
                                int color = palette.getDarkMutedColor(0);
                                if (color == 0){
                                    color = palette.getDarkVibrantColor(0);
                                }
                                collapsingToolbarLayout.setContentScrimColor(color);
                                getActivity().findViewById(R.id.background).setBackgroundColor(color);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                                    getActivity().getWindow().setStatusBarColor(color);

                                txtMovieName.setTextColor(color);

                            }
                        }));


        txtMovieName.setText(movieDetail.getMovieName());
        txtReleaseDate.setText(movieDetail.getReleaseDate());
        txtRating.setText(movieDetail.getRating()+"/10");
        txtDescription.setText(movieDetail.getDescription());

        return rootView;
    }
}
