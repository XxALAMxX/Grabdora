package com.example.grabadora;

public class DataBaseManager {
	public static final String TABLE_NAME = "Grabaciones";
	public static final String CN_ID = "Id";
	public static final String CN_NOMBRE = "Nombre";
	public static final String CN_RUTA = "Ruta";
	
public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
+ CN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
+ CN_NOMBRE + " TEXT NOT NULL,"
+ CN_RUTA + " TEXT;";
	

}
