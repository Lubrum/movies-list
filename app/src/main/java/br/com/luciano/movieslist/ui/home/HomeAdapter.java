package br.com.luciano.movieslist.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import java.util.ArrayList;
import java.util.List;

import br.com.luciano.movieslist.model.Movie;
import movieslist.R;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

    private final List<Movie> movies = new ArrayList<>();

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    @Override
    public void onBindViewHolder(HomeViewHolder holder, int position) {
        holder.bind(movies.get(position));
    }

    public void appendMovies(List<Movie> movies) {
        this.movies.addAll(movies);
        notifyItemRangeInserted(this.movies.size(), movies.size() - 1);
    }

    public static class HomeViewHolder extends RecyclerView.ViewHolder {

        private final ImageView poster;

        HomeViewHolder(View itemView) {
            super(itemView);
            this.poster = itemView.findViewById(R.id.item_movie_poster);
        }

        public void bind(Movie movie) {
            Glide.with(itemView)
                    .load("https://image.tmdb.org/t/p/w342" + movie.getPosterPath())
                    .transform(new CenterCrop())
                    .into(poster);
            itemView.setOnClickListener(iv -> {

            });
        }
    }

}
