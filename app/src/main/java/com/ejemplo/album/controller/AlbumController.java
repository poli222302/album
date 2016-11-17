package com.ejemplo.album.controller;

import android.content.Context;

import com.ejemplo.album.dao.AlbumDAO;
import com.ejemplo.album.model.Albums;

import java.util.List;

import util.HTTPConnectionManager;
import util.ResultListener;

/**
 * Created by polialva on 7/11/16.
 */
public class AlbumController {

    private List<Albums> dameLosAlbumsDeLaBaseDeDatos(Context context){
        AlbumDAO albumDAO = new AlbumDAO(context);
        List<Albums> albumList = albumDAO.getAllAlbumFromDatabase();
        return albumList;
    }

    private void dameLosAlbumsDeLaWeb(final ResultListener<List<Albums>>listenerView, Context context){
        AlbumDAO albumDAO = new AlbumDAO(context);
        albumDAO.getAllAlbumFromWEB(new ResultListener<List<Albums>>() {
            @Override
            public void finish(List<Albums> resultado) {
                listenerView.finish(resultado);
            }
        });


    }

    public void dameLosAlbumsDeAlgunLado(ResultListener<List<Albums>>listenerView,Context context){
        Boolean hayInternet = HTTPConnectionManager.isNetworkingOnline(context);

        if(hayInternet){
            dameLosAlbumsDeLaWeb(listenerView,context);
        }else{
            List<Albums> albumList = dameLosAlbumsDeLaBaseDeDatos(context);
            listenerView.finish(albumList);
        }

    }

}
