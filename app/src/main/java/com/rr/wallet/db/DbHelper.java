package com.rr.wallet.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ritesh on 26-02-2018.
 */

public class DbHelper extends SQLiteOpenHelper {

    static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "MyDiary.db";

    public static final String TABLE_EXPENSE = "expense";

    public static final String ID = "ID";
    public static final String NOTE = "note";
    public static final String AMOUNT = "amount";
    public static final String TYPE = "type";
    public static final String DATE = "date";

    private static String CREATE_TABLE_EXPENSE = "Create table " + TABLE_EXPENSE + "( " +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NOTE + " TEXT, " +
            AMOUNT + " FLOAT, " +
            TYPE + " TEXT, " +
            DATE + " TEXT " + ")";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_EXPENSE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSE);
    }
}
