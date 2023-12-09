package com.miner.newsappproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AlertDialogLayout;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.miner.newsappproject.Models.NewsApiResponse;
import com.miner.newsappproject.Models.NewsHeadlines;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SelectListener, View.OnClickListener{
    RecyclerView recyclerView;
    Button business,entertainment,general,health,science,sport,technology;
    SearchView searchView;

    CustomAdapter customAdapter;
    ProgressDialog dialog;
    String cat = "general";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dialog = new ProgressDialog(this);
        dialog.setTitle("Fetching news articles");
        dialog.show();

        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dialog.setTitle("Fetching news article of "+query+"\nat "+cat);
                dialog.show();
                RequestManager manager = new RequestManager(MainActivity.this);
                manager.getNewsHeadlines(listener, cat, query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        business = (Button) findViewById(R.id.business);
        business.setOnClickListener(this);
        entertainment = (Button) findViewById(R.id.entertainment);
        entertainment.setOnClickListener(this);
        general = (Button) findViewById(R.id.general);
        general.setOnClickListener(this);
        health = (Button) findViewById(R.id.health);
        health.setOnClickListener(this);
        science = (Button) findViewById(R.id.science);
        science.setOnClickListener(this);
        sport = (Button) findViewById(R.id.sport);
        sport.setOnClickListener(this);
        technology = (Button) findViewById(R.id.technology);
        technology.setOnClickListener(this);

        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadlines(listener, "general", null);
    }
    
    private final OnFetchDataListener<NewsApiResponse> listener = new OnFetchDataListener<NewsApiResponse>() {
        @Override
        public void onFetchData(List<NewsHeadlines> list, String message) {
            if(list.isEmpty()){
                Toast.makeText(MainActivity.this, "No news found!!!", Toast.LENGTH_SHORT).show();
            }else{
                ShowNews(list);
                dialog.dismiss();
            }
        }

        @Override
        public void onError(String message) {
            Toast.makeText(MainActivity.this, "An Error Occured!!", Toast.LENGTH_SHORT).show();
        }
    };

    private void ShowNews(List<NewsHeadlines> list) {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerMain);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        customAdapter = new CustomAdapter(this, list, this);
        recyclerView.setAdapter(customAdapter);
    }

    @Override
    public void OnNewsClick(NewsHeadlines headlines) {
        Intent intent = new Intent(this,DetailsActivity.class);
        intent.putExtra("data",headlines);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        String category = button.getText().toString();
        cat = category.toLowerCase();
        dialog.setTitle("Fetching News Articles of "+category);
        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadlines(listener, category.toLowerCase(), null);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Close News App").setMessage("Do you wanna close News App?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        Toast.makeText(MainActivity.this, "You have close News App", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}