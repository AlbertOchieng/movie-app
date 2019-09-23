package com.example.albertzkiki.movieapp.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.albertzkiki.movieapp.model.FavouriteMovie;
import com.example.albertzkiki.movieapp.model.Order;
import com.example.albertzkiki.movieapp.model.movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by albertzkiki on 4/29/2018.
 */

public class DatabaseMovie extends com.readystatesoftware.sqliteasset.SQLiteAssetHelper{

    private static final String DB_NAME = "DbMovie.db";
    private static final int DB_VER = 1;

    public DatabaseMovie(Context context)
    {
        super(context, DB_NAME, null, DB_VER);
    }

    public void addTofavourires(FavouriteMovie movie){

            String movId = movie.getId();
            String moviename = movie.getName();
            String movieprice = movie.getPrice();
            String movieplot = movie.getPlotsynopsis();
            String movieimage = movie.getImage();
            String phone = movie.getUserPhone();

            movieplot=movieplot.replaceAll("'","''");


        SQLiteDatabase db = getReadableDatabase();
        String querry = String.format("INSERT INTO Favourites" +
                        "(MovieId,MovieName,MoviePrice,MoviePlot,MovieImage,UserPhone)" +
                        "VALUES('"+movId+"','"+moviename+"','"+movieprice+"','"+movieplot+"','"+movieimage+"','"+phone+"');");


        //db.rawQuery(querry, new String[] { movieplot});
        db.execSQL(querry);


    }

    public void removeFromfavourires(String movieId,String userPhone){

        SQLiteDatabase db = getReadableDatabase();
        String querry = String.format("DELETE FROM Favourites WHERE MovieId='%s' and UserPhone='%s';",movieId,userPhone);
        db.execSQL(querry);


    }

    public boolean isFavourite(String movieId,String userPhone){

        SQLiteDatabase db = getReadableDatabase();
        String querry = String.format("SELECT * FROM Favourites WHERE MovieId='%s' and UserPhone='%s';",movieId,userPhone);
        Cursor cursor = db.rawQuery(querry,null);

        if(cursor.getCount()<=0){

            cursor.close();
            return false;
        }

        cursor.close();
        return true;
    }

    public List<FavouriteMovie> getAllFavourites(String userPhone){


        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"UserPhone","MovieId","MovieName","MoviePrice","MovieImage","MoviePlot"};
        String sqlTable = "Favourites";

        qb.setTables(sqlTable);

        Cursor c = qb.query(db,sqlSelect,"UserPhone=?",new String[]{userPhone},null,null,null);

        final  List<FavouriteMovie> result = new ArrayList<>();

        if(c.moveToFirst())
        {
            do{
                result.add(new FavouriteMovie(
                        c.getString(c.getColumnIndex("UserPhone")),
                        c.getString(c.getColumnIndex("MovieId")),
                        c.getString(c.getColumnIndex("MovieName")),
                        c.getString(c.getColumnIndex("MoviePrice")),
                        c.getString(c.getColumnIndex("MovieImage")),
                        c.getString(c.getColumnIndex("MoviePlot"))
                ));


            }while(c.moveToNext());

        }

        return  result;
    }




}
