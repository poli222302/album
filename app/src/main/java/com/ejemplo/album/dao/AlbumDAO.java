package com.ejemplo.album.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;

import com.ejemplo.album.model.AlbumContainer;
import com.ejemplo.album.model.Albums;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import util.HTTPConnectionManager;
import util.ResultListener;

/**
 * Created by polialva on 2/11/16.
 */
public class AlbumDAO extends SQLiteOpenHelper {

    //CONSTANTES PARA LOS NOMBRES DE LA BD Y LOS CAMPOS
    private static final String DATABASENAME = "Albums";
    private static final Integer DATABASEVERSION = 1;

    //TABLA PERSONA CON SUS CAMPOS
    private static final String TABLEALBUM = "Albums";
    private static final String ALBUMID = "albumID";
    private static final String ID = "ID";
    private static final String TITLE = "title";
    private static final String URL = "url";
    private static final String TUMBNAIURL = "thumbnailUrl";

    //El contexto lo necesitamos para poder crear una BD.
    private Context context;

    //Constructor que permite crear la BD
    public AlbumDAO(Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Creo la tabla que contendrá mi base de datos
        String createTable = "CREATE TABLE " + TABLEALBUM + "("
                + ID + " INTEGER PRIMARY KEY, "
                + ALBUMID + " INTEGER, "
                + TITLE + " TEXT, "
                + URL + " TEXT, "
                + TUMBNAIURL + " TEXT " + ")";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    //METODO QUE ME PERMITE CHEQUEAR SI EXISTIA UN ALBUM EN MI BASE DE DATOS
    private Boolean checkIfExist(String unId) {

        SQLiteDatabase database = getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLEALBUM
                + " WHERE " + ID + "==" + unId;

        Cursor result = database.rawQuery(selectQuery, null);
        Integer count = result.getCount();

        Log.v("AlbumDAO", "Albums " + unId + " ya esta en la base");

        database.close();

        return count > 0;

    }
    private void addAllAlbums(List<Albums> albumsList) {
        for (Albums unAlbum : albumsList) {
            if (!checkIfExist(unAlbum.getID())) {
                addAlbum(unAlbum);
                Log.v("Add", unAlbum.getAlbumID() + "ya esta");
            }
        }
    }

    //METODO QUE ME PERMITE AGREGAR UN Album A MI BD
    public void addAlbum(Albums albums) {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues row = new ContentValues();

        //Obtengo los datos y los cargo en el row
        row.put(ID, albums.getID());
        row.put(TITLE, albums.getTitle());
        row.put(ALBUMID, albums.getAlbumID());
        row.put(URL, albums.getUrl());
        row.put(TUMBNAIURL, albums.getThumbnailUrl());


        database.insert(TABLEALBUM, null, row);

        database.close();
    }

    public List<Albums> getAllAlbumFromDatabase() {

        SQLiteDatabase database = getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLEALBUM;
        Cursor cursor = database.rawQuery(selectQuery, null);

        List<Albums> albumsList = new ArrayList<>();
        while(cursor.moveToNext()){

            //TOMO LOS DATOS DE CADA Album
            Albums albums = new Albums();

            albums.setID(cursor.getString(cursor.getColumnIndex(ID)));
            albums.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
            albums.setAlbumID(cursor.getString(cursor.getColumnIndex(ALBUMID)));
            albums.setUrl(cursor.getString(cursor.getColumnIndex(URL)));
            albums.setThumbnailUrl(cursor.getString(cursor.getColumnIndex(TUMBNAIURL)));

            //AGREGO UN Album A LA LISTA
            albumsList.add(albums);
        }

        return albumsList;
    }


    //ESTE METODO CHEQUEA SI TIENE CONEXION DE INTERNET, EN CASO AFIRMATIVO GENERAMOS EL ASYNC TASK Y PEDIMOS EL LISTADO A LA
    //URL, EN CASO NEGATIVO PEDIMOS EL CONTENIDO A LA BASE DE DATOS.
    public void getAllAlbumFromWEB(final ResultListener<List<Albums>> listener) {

        RetrieveAlbumTask retrieveAlbumTask = new RetrieveAlbumTask(listener);
        retrieveAlbumTask.execute();
    }




    //ESTA CLASE ES UNA CLASE QUE ME PERMITE GENERAR UNA TAREA ASINCRONICA. ES DECIR, ESTA TAREA SE EJECUTARA
// INDEPENDIENTEMENTE DE LO QUE ESTE HACIENDO COMO ACTIVIDAD PRINCIPAL
    class RetrieveAlbumTask extends AsyncTask<String, Void, List<Albums>> {

        private ResultListener<List<Albums>> listener;

        public RetrieveAlbumTask(ResultListener<List<Albums>> listener) {
            this.listener = listener;
        }

        //Esto método se ejecuta mientras sigue corriendo la tarea principal. Aqui lo que haremos es conectarnos
        // al servicio y descargar la lista.
        @Override
        protected List<Albums> doInBackground(String... params) {

            HTTPConnectionManager connectionManager = new HTTPConnectionManager();
            String input = null;

            try {
                input = connectionManager.getRequestString("https://api.myjson.com/bins/25hip");
            } catch (Exception e) {
                e.printStackTrace();
            }

            Gson gson = new Gson();
          AlbumContainer albumContainer = gson.fromJson(input, AlbumContainer.class);



            return albumContainer.getAlbumsList();
        }

        //Una vez terminado el procesamiento, le avisamos al listener que ya tiene la lista disponible.
        @Override
        protected void onPostExecute(List<Albums> albumsList) {
            addAllAlbums(albumsList);
            this.listener.finish(albumsList);
        }
    }


}

