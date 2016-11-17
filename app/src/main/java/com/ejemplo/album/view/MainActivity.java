package com.ejemplo.album.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.ejemplo.album.R;
import com.ejemplo.album.controller.AlbumController;
import com.ejemplo.album.model.Albums;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.List;

import util.ResultListener;



//1. Descargar y parsear cada track del JSON a través a de la URL.
//2. Guardar el contenido de los tracks en una Base de Datos.
//3. Mostrar el contenido en un Recycler View.
//a. Utilizar un CardView para mostrar cada álbum del listado.
// b. Utilizar Glide para mostrar la imagen (thumbnailUrl).

public class MainActivity extends AppCompatActivity {

    //Declaro todas estas variables como globales ya que las voy a usar a lo largo de la clase.
    private RecyclerView recyclerView;
    private AdapterAlbum adapterAlbum;
    private AlbumController albumController;

    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Seteo el contexto y obtengo el recyclerView y el SwipeRefreshLayout
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        //Seteo el adapter y los elementos del recyclerView
        adapterAlbum = new AdapterAlbum(getApplicationContext());
        recyclerView.setAdapter(adapterAlbum);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
         adapterAlbum.setListener(new ListenerAlbums());

        //Instancio el AlbumController que me va a devolver todos los albums
        albumController = new AlbumController();

        //Este metodo va a pedirle al albumcontroller que busque los albums y tambien actualizar el adaptador del recyclerView
        update();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    //Aca es donde hacemos el pedido de albums y actualizamos el recyclerView
    private void update() {

        albumController.dameLosAlbumsDeAlgunLado(new ResultListener<List<Albums>>() {
            @Override
            public void finish(List<Albums> resultado) {

                adapterAlbum.setAlbumstList(resultado);

                adapterAlbum.notifyDataSetChanged();


            }
        }, MainActivity.this);


    }

    private class ListenerAlbums implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            Integer position = recyclerView.getChildAdapterPosition(view);
            List<Albums> albumsList = adapterAlbum.getAlbumstList();
            Albums albums = albumsList.get(position);

            //Crear un intent
            Intent unIntent = new Intent(MainActivity.this, ActivityDetalle.class);
            //Crear un bundle
            Bundle unBundle = new Bundle();
            //Poner en el bundle la informacion del album
            unBundle.putString("titulo",albums.getTitle());
            unBundle.putString("imagen",albums.getThumbnailUrl());

            //ir a la otra activity
            unIntent.putExtras(unBundle);
            startActivity(unIntent);

        }
    }

}









