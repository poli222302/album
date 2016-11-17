package com.ejemplo.album.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ejemplo.album.R;

public class ActivityDetalle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle_album);

        //Recibir intent y bundle
        Intent unIntent = getIntent();

        //Recibir informacion del album
        Bundle unBundle = unIntent.getExtras();

        //Buscar en el layout los componentes donde vamos a cargar la informacion del album que vino en el bundle
        ImageView posterImageView = (ImageView)findViewById(R.id.imageViewThumbnail);
        TextView titleTextView = (TextView)findViewById(R.id.textViewAlbum);

        //Setearle a esos componentes la informacion del bundle
       titleTextView.setText(unBundle.getString("titulo"));
        Glide.with(this).load(unBundle.getString("imagen")).into(posterImageView);


    }
}
