package com.example.retrofit;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>
{
    private ArrayList<DataModel> dataSet;
    private Context context;

    public CustomAdapter(ArrayList<DataModel> dataSet, Context c) {
        this.context=c;
        this.dataSet = dataSet;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_layout,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        TextView textViewName=holder.textViewName;
        TextView textViewVersion=holder.textViewVersion;
        ImageView imageView=holder.imageViewIcon;
        CardView cardView=holder.cardView;
        textViewVersion.setText(dataSet.get(position).getDesc());
        imageView.setImageURI(Uri.parse(dataSet.get(position).getUri()));
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        CardView cardView;
        TextView textViewName;
        TextView textViewVersion;
        ImageView imageViewIcon;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            this.cardView=itemView.findViewById(R.id.card_view);
            this.imageViewIcon=itemView.findViewById(R.id.imageview);
            this.textViewName=itemView.findViewById(R.id.textviewName);
            this.textViewVersion=itemView.findViewById(R.id.textviewVersion);
        }
    }
}
