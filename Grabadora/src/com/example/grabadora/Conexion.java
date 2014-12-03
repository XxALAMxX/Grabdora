package com.example.grabadora;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class Conexion extends SQLiteOpenHelper {

    public Conexion(Context context, String nombre, CursorFactory factory, int version) {
        super(context, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Grabacion(Id integer primary key autoincrement, Nombre text, Ruta text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnte, int versionNue) {
        db.execSQL("drop table if exists Grabacion");
        db.execSQL("create table Grabacion(Id integer primary key autoincrement, Nombre text, Ruta text)");        
    }
}
