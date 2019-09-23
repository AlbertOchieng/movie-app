package com.example.albertzkiki.movieapp.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.albertzkiki.movieapp.model.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by albertzkiki on 4/4/2018.
 */

public class Database extends com.readystatesoftware.sqliteasset.SQLiteAssetHelper {

    private static final String DB_NAME = "DatabaseNew.db";
    private static final int DB_VER = 1;

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }


    public List<Order> getCarts(){


        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"ID","ProductName","ProductId","Quantity","Discount","Price"};
        String sqlTable = "OrderDetails";

        qb.setTables(sqlTable);

        Cursor c = qb.query(db,sqlSelect,null,null,null,null,null);

        final  List<Order> result = new ArrayList<>();

        if(c.moveToFirst())
        {
            do{
                result.add(new Order(
                                    c.getInt(c.getColumnIndex("ID")),
                                    c.getString(c.getColumnIndex("ProductId")),
                                    c.getString(c.getColumnIndex("ProductName")),
                                    c.getString(c.getColumnIndex("Quantity")),
                                    c.getString(c.getColumnIndex("Discount")),
                                    c.getString(c.getColumnIndex("Price"))
                             ));


            }while(c.moveToNext());

        }

        return  result;
    }


    public void addtoCart(Order order){

        SQLiteDatabase db = getReadableDatabase();
        String querry = String.format("INSERT INTO OrderDetails" +
                        "(ProductId,ProductName,Quantity,Discount,Price)VALUES('%s','%s','%s','%s','%s');",
                order.getProductId(),
                order.getProductName(),
                order.getQuantity(),
                order.getPrice(),
                order.getDiscount()
                        );

        db.execSQL(querry);

    }

    public void cleanCart(){

        SQLiteDatabase db = getReadableDatabase();
        String querry = String.format("DELETE FROM OrderDetails");

        db.execSQL(querry);

    }

    public void addTofavourires(String foodId,String userPhone){

        SQLiteDatabase db = getReadableDatabase();
        String querry = String.format("INSERT INTO Favourites(FoodId,UserPhone)VALUES('%s','%s');",foodId,userPhone);
        db.execSQL(querry);


    }

    public void removeFromfavourires(String foodId){

        SQLiteDatabase db = getReadableDatabase();
        String querry = String.format("DELETE FROM Favourites WHERE FoodId='%s';",foodId);
        db.execSQL(querry);


    }

    public boolean isFavourite(String foodId,String userPhone){

        SQLiteDatabase db = getReadableDatabase();
        String querry = String.format("SELECT * FROM Favourites WHERE FoodId='%s' and UserPhone='%s';",foodId,userPhone);
        Cursor cursor = db.rawQuery(querry,null);

        if(cursor.getCount()<=0){

            cursor.close();
            return false;
        }

        cursor.close();
        return true;
    }


    public void UpdateCart(Order order) {

        SQLiteDatabase db = getReadableDatabase();
        String querry = String.format("UPDATE OrderDetails SET Quantity=%s WHERE ID=%d",order.getQuantity(),order.getID());

        db.execSQL(querry);
    }

    public void removeFromCart(String productId) {

        SQLiteDatabase db = getReadableDatabase();
        String querry = String.format("DELETE FROM OrderDetails WHERE ProductId='%s'",productId);
        db.execSQL(querry);

    }


}
