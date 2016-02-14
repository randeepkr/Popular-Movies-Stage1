package com.example.randeep.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by randeep on 1/2/16.
 */
public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState == null ){
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new DetailFragment()).commit();
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Picasso.with(this).load(((MovieDetail)bundle.getParcelable("MOVIE_DETAIL")).getBackgroundUrl())
                    .into((ImageView)findViewById(R.id.background_image));
        }
    }
}
