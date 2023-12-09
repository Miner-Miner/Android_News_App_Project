package com.miner.newsappproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.miner.newsappproject.Models.NewsHeadlines;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Context context;
    private List<NewsHeadlines> headlines;
    private SelectListener listener;

    public CustomAdapter(Context context, List<NewsHeadlines> headlines,SelectListener listener) {
        this.context = context;
        this.headlines = headlines;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.headline_list_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textTitle.setText(headlines.get(position).getTitle());
        holder.textSource.setText(headlines.get(position).getSource().getName());

        if(headlines.get(position).getUrlToImage()!=null){
            Picasso.get().load(headlines.get(position).getUrlToImage()).into(holder.imageHeadline);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnNewsClick(headlines.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return headlines.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle,textSource;
        ImageView imageHeadline;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textTitle = (TextView) itemView.findViewById(R.id.textTitle);
            textSource = (TextView) itemView.findViewById(R.id.textSource);
            imageHeadline = (ImageView) itemView.findViewById(R.id.imageHeadline);
            cardView = (CardView) itemView.findViewById(R.id.mainContainer);
        }
    }
}
