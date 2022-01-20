package com.example.amazigh_app;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.io.IOException;

public class ItemAdapter extends FirebaseRecyclerAdapter<
    Woord, ItemAdapter.ItemViewholder> {

    public ItemAdapter(@NonNull FirebaseRecyclerOptions<Woord> options) {
            super(options);
        }


        private void speelAudio(String url, Context c) {
            MediaPlayer mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(url);
                mediaPlayer.prepare();
                mediaPlayer.start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onBindViewHolder(@NonNull ItemAdapter.ItemViewholder holder, int position, @NonNull Woord model) {
            // zie Stap 9: Plaatsen data in layout van de viewholder
            holder.tvWoordned.setText(model.getWoordned());
            holder.tvWoordamz.setText(model.getWoordamz());
            holder.ivWoord.setClickable(true);
            holder.ivWoord.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Reference naar audiofile maken
                    final FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference audioRef = storage.getReference().child("audio/"+model.getAudiopath());
                    audioRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            final String url = uri.toString();
                            Context c = holder.itemView.getContext();
                            speelAudio(url,c);
                        }
                    });
                }
            });

            // Stap 11: storagereference naar plaatje maken
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference stImg = storage.getReference();
            StorageReference imageRef =
                    stImg.child("images/" + model.getImagepath());
            imageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    // Stap 13: plaatje plaatsen
                    String profileimageurl =task.getResult().toString();
                    holder.ivWoord.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    Glide.with(holder.ivWoord.getContext())
                            .load(profileimageurl)
                            .apply(new RequestOptions()
                                    .placeholder(R.drawable.ic_launcher_background)
                                    .fitCenter())
                            .into(holder.ivWoord);

                }
            });


        }

        @NonNull
        @Override
        public ItemAdapter.ItemViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // zie Stap 7: Maken van de layout in een viewholder
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.woord_layout, parent, false);
            return new ItemAdapter.ItemViewholder(view);

        }

        public class ItemViewholder extends RecyclerView.ViewHolder {
            // zie Stap 8: Maken van de layout in een viewholder
            TextView tvWoordned, tvWoordamz;
            ImageView ivWoord;

            public ItemViewholder(@NonNull View itemView) {
                super(itemView);
                tvWoordned = itemView.findViewById(R.id.tvWoordned2);
                tvWoordamz = itemView.findViewById(R.id.tvWoordamz2);
                ivWoord = itemView.findViewById(R.id.ivWoord2);

            }
        }
}
