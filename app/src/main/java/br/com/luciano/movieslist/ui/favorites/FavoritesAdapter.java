package br.com.luciano.movieslist.ui.favorites;

import static movieslist.BuildConfig.IMAGE_API;

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

import br.com.luciano.movieslist.data.model.Movie;
import br.com.luciano.movieslist.data.local.AppDatabase;
import io.reactivex.rxjava3.schedulers.Schedulers;
import movieslist.R;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> implements ClickListener {

    private final List<Movie> movies = new ArrayList<>();

    @NonNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new FavoritesViewHolder(view);
    }

    @Override
    public void onItemClick(View v, int position) {
        this.movies.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    @Override
    public void onBindViewHolder(FavoritesViewHolder holder, int position) {
        holder.bind(movies.get(position));
    }

    public void appendMovies(List<Movie> movies) {
        this.movies.clear();
        notifyItemRangeRemoved(0, this.movies.size());
        this.movies.addAll(movies);
        notifyItemRangeRemoved(0, this.movies.size());
    }

    public class FavoritesViewHolder extends RecyclerView.ViewHolder {

        private final ImageView poster;

        FavoritesViewHolder(View itemView) {
            super(itemView);
            this.poster = itemView.findViewById(R.id.item_movie_poster);
        }

        public void bind(Movie movie) {
            Glide.with(itemView)
                    .load(IMAGE_API + movie.getPosterPath())
                    .transform(new CenterCrop())
                    .into(poster);

            itemView.setOnClickListener(iv -> {
                AppDatabase
                        .getDatabase(itemView.getContext())
                        .movieDao()
                        .delete(movie)
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io())
                        .subscribe();
                onItemClick(itemView, getAdapterPosition());
            });
        }
    }
}
