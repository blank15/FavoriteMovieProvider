package id.kampung.favoritemovie.view.Main;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.kampung.favoritemovie.R;
import id.kampung.favoritemovie.adapter.RecyclerViewMovie;
import id.kampung.favoritemovie.model.MovieModel;

import static id.kampung.favoritemovie.db.DbContract.CONTENT_URI;

public class MainActivity extends AppCompatActivity implements MainInterface  {

    List<MovieModel> movieModel = new ArrayList<>();
    RecyclerViewMovie adapter;
    RecyclerView recyclerView;
    private MainPresenter mainPresenter;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainPresenter = new MainPresenter(this,this);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewMovie(this,movieModel);

        mainPresenter.getData();
    }


    @Override
    public void setData(Cursor listMovie) {
        adapter.setListNotes(listMovie);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void empty() {

        Toast.makeText(this, " Belum ada Movie yang di favorite kan", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.removeAllItem();
        mainPresenter.getData();
    }
}
