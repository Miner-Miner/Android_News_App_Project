package com.miner.newsappproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.miner.newsappproject.Models.NewsHeadlines;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    NewsHeadlines headlines;
    TextView textTitle,textAuthor,textTime,textDetail,textContent;
    ImageView imageNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        textTitle = (TextView) findViewById(R.id.textDetailTitle);
        textAuthor = (TextView) findViewById(R.id.textDetailAuthor);
        textTime = (TextView) findViewById(R.id.textDetailTime);
        textDetail = (TextView) findViewById(R.id.textDetailDetail);
        textContent = (TextView) findViewById(R.id.textDetailContent);
        imageNews = (ImageView) findViewById(R.id.imageDetailNews);

        headlines = (NewsHeadlines) getIntent().getSerializableExtra("data");

        textTitle.setText(headlines.getTitle());
        textAuthor.setText(headlines.getAuthor());
        textTime.setText(headlines.getPublishedAt());
        textDetail.setText(headlines.getDescription());
        textContent.setText(headlines.getContent());
        Picasso.get().load(headlines.getUrlToImage()).into(imageNews);
    }
}