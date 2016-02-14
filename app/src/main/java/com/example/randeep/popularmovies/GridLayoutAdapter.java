package com.example.randeep.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by randeep on 1/2/16.
 */
public class GridLayoutAdapter extends RecyclerView.Adapter<GridLayoutAdapter.ViewHolder> {


    private Context mContext;
    public ArrayList<MovieDetail> movieDetailArrayList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        public View container;
        public ImageView movie_image;
        public ViewHolder(View itemView) {
            super(itemView);
            container = itemView;
            movie_image = (ImageView) itemView.findViewById(R.id.movie_image);

        }
    }

    public GridLayoutAdapter(Context context, ArrayList<MovieDetail> arrayList){
        mContext = context;
        movieDetailArrayList = arrayList;
    }

    @Override
    public GridLayoutAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {

        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View container = layoutInflater.inflate(R.layout.movie_image,parent, false);



        return new ViewHolder(container);
    }

    @Override
    public void onBindViewHolder(GridLayoutAdapter.ViewHolder holder, final int position) {
        Picasso.with(mContext)
                .load(movieDetailArrayList.get(position).getPosterUrl())
                .into(holder.movie_image);

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,DetailActivity.class).putExtra("MOVIE_DETAIL",
                        movieDetailArrayList.get(position));
                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return movieDetailArrayList.size();
    }
}
