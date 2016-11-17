package com.ejemplo.album.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ejemplo.album.R;
import com.ejemplo.album.model.Albums;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by polialva on 3/11/16.
 */
public class AdapterAlbum extends  RecyclerView.Adapter implements View.OnClickListener {

    private List<Albums> albumstList;
    private Context context;
    private View.OnClickListener listener;

    public AdapterAlbum(Context context) {
        this.albumstList = new ArrayList<>();
        this.context = context;

    }

    public List<Albums> getAlbumstList() {
        return albumstList;
    }

    public void setAlbumstList(List<Albums> albumstList) {
        this.albumstList = albumstList;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_detalle, parent, false);
        AlbumViewHolder unTitleViewHolder = new AlbumViewHolder(itemView);
        itemView.setOnClickListener(listener);
        return unTitleViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Albums unAlbum = albumstList.get(position);
        AlbumViewHolder unTitleViewHolder = (AlbumViewHolder) holder;
        unTitleViewHolder.bindAlbum(unAlbum, context);

    }

    @Override
    public int getItemCount() {
        return albumstList.size();
    }

    @Override
    public void onClick(View view) {
        listener.onClick(view);
    }


    private static class AlbumViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewTitulo;
        private ImageView imageViewThumbnail;

        public AlbumViewHolder(View itemView) {
            super(itemView);
            textViewTitulo = (TextView) itemView.findViewById(R.id.textViewTitle);
            imageViewThumbnail = (ImageView) itemView.findViewById(R.id.imageViewThumbnail);
        }

        public void bindAlbum(Albums unAlbum, Context context){
            textViewTitulo.setText(unAlbum.getTitle());
            Glide.with(context).load(unAlbum.getThumbnailUrl()).into(imageViewThumbnail);
        }
    }
}
