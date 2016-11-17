package com.ejemplo.album.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by polialva on 2/11/16.
 */
public class Albums {

    @SerializedName("albumId")
    private String albumID;
    @SerializedName("id")
    private String ID;
    private String title;
    private String url;
    private String thumbnailUrl;

    public String getAlbumID() {
        return albumID;
    }

    public void setAlbumID(String albumID) {
        this.albumID = albumID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
