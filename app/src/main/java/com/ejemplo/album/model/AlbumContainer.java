package com.ejemplo.album.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by polialva on 2/11/16.
 */
public class AlbumContainer {

    @SerializedName("albums")
    private List<Albums> albumsList;

    public List<Albums> getAlbumsList() {
        return albumsList;
    }

    public void setAlbumsList(List<Albums> albumsList) {
        this.albumsList = albumsList;
    }
}
