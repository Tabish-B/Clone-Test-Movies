package com.tabish.movieapp0139.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tabish.movieapp0139.R;

public class CastViewHolder extends RecyclerView.ViewHolder {

    TextView name;
    TextView character;
    ImageView profile_image;
    TextView gender;

    public CastViewHolder(@NonNull View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.name_actor);
        name.setSelected(true);
        character = itemView.findViewById(R.id.name_in_movie);
        character.setSelected(true);
        profile_image  = itemView.findViewById(R.id.profile_image);
        gender = itemView.findViewById(R.id.gender_of_character);

    }


}
