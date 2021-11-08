package com.example.bottomapp;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.bottomapp.activities.BeatViewActivity;
import com.example.bottomapp.helpers.Databaser;

import java.util.ArrayList;

public class BeatAdapter extends RecyclerView.Adapter<BeatAdapter.BeatHolder> {

    ArrayList<Beat> beats ;
    String uid;

    Context context;

    Databaser databaser = new Databaser();

    public BeatAdapter(Context ctx, ArrayList<Beat> gr, final String task1){
        this.beats = gr;
        this.context = ctx;
        this.uid = task1;
    }

    @NonNull
    @Override
    public BeatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_beat, parent, false);
        return new BeatHolder(view);
    }

    @Override
    public void onBindViewHolder(final @NonNull BeatHolder holder,final int position) {

        holder.title.setText(beats.get(position).getBeatTitle());

        databaser.getUserData(beats.get(position).getAuthorId(), user -> {
            holder.author.setText(user.getLogin());
        });


        holder.duration.setText(beats.get(position).getDuration());

        //holder.cover.setImageURI(new Uri(beats.get(position).getCoverURL()));

        Glide.with(this.context).load(beats.get(position).getCoverURL()).into(holder.cover);

        holder.block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, BeatViewActivity.class);
                intent.putExtra("beatId", beats.get(position).getBeatId());
                context.startActivity(intent);


            }
        });

    }

    public void setBeats (ArrayList<Beat> arrayList) {
        beats.clear();
        beats.addAll(arrayList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return beats.size();

    }

    public class BeatHolder extends RecyclerView.ViewHolder {

        ImageView cover;
        TextView title;
        TextView author;
        TextView duration;
        LinearLayout block;

        public BeatHolder(@NonNull View itemView) {
            super(itemView);
            cover = itemView.findViewById(R.id.beat_cover);
            title = itemView.findViewById(R.id.beat_title);
            author = itemView.findViewById(R.id.beat_author);
            duration = itemView.findViewById(R.id.beat_duration);
            block = itemView.findViewById(R.id.beat_block);

        }
    }
}
