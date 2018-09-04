package id.kampung.favoritemovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import id.kampung.favoritemovie.R;
import id.kampung.favoritemovie.model.MovieModel;
import id.kampung.favoritemovie.view.detail.DetailMovie;

import static id.kampung.favoritemovie.db.DbContract.CONTENT_URI;

public class RecyclerViewMovie extends RecyclerView.Adapter<RecyclerViewMovie.ViewHolder> {

    private Context context;
    private List<MovieModel> modelList;
    private final String BASE_PATH;

    private Cursor cursor ;
    public RecyclerViewMovie(Context context, List<MovieModel> modelList) {
        this.context = context;
        this.modelList = modelList;

        BASE_PATH = "http://image.tmdb.org/t/p/w185/";
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.card_list_movie,parent,false));
    }

    public void setListNotes(Cursor lisMovie) {
        this.cursor = lisMovie;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieModel movieModel = new MovieModel() ;

        if(cursor != null){
            movieModel = getItem(position);
            Log.d("coba","ini kursor ke" + movieModel.getId());
        }
        Glide.with(context).load(BASE_PATH+movieModel.getUrlGambar()).into(holder.imageViewCover);
        Log.d("url",BASE_PATH+movieModel.getUrlGambar());
        holder.textViewNama.setText(movieModel.getTitle());

        holder.textViewDeskripsi.setText(movieModel.getDeskripsi());
        holder.textViewRilis.setText(movieModel.getRilis());
        boolean favorite = true;
        final MovieModel finalMovieModel = movieModel;
        final boolean finalFavorite = favorite;
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailMovie.class);

                intent.putExtra("data",finalMovieModel);
                intent.putExtra("favorite", finalFavorite);
                Uri uri = Uri.parse(CONTENT_URI + "/" + finalMovieModel.getId());
                intent.setData(uri);
                context.startActivity(intent);
            }
        });
    }


    private MovieModel getItem(int position){
        if(!cursor.moveToPosition(position)){
            throw new IllegalStateException("Position invalid");
        }
        return new MovieModel(cursor);
    }
    public void removeAllItem(){
        cursor = null;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        if(cursor == null){
            return  0;
        }
        return cursor.getCount();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNama;
        TextView textViewDeskripsi;
        TextView textViewRilis;
        ImageView imageViewCover;
        ConstraintLayout layout;
        ViewHolder(View itemView) {
            super(itemView);
            textViewNama = itemView.findViewById(R.id.text_nama);
            textViewDeskripsi = itemView.findViewById(R.id.text_deskripsi);
            textViewRilis = itemView.findViewById(R.id.text_rilis);
            imageViewCover = itemView.findViewById(R.id.imageView);
            layout = itemView.findViewById(R.id.layout);
        }
    }
}
