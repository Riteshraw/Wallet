package com.rr.wallet;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.rr.wallet.dao.Entry;
import com.rr.wallet.db.DbHelper;
import com.rr.wallet.provider.SqliteProvider;

public class MainActivity extends AppCompatActivity {

    private ContentResolver myCR;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        myCR = context.getContentResolver();
        insertEntry();
//        queryEntry("bank");
//        deleteEntry("Bank");
//        updateEntry("ICICI");
    }

    public void insertEntry() {

        ContentValues values = new ContentValues();
        values.put(DbHelper.NOTE, "Salary");
        values.put(DbHelper.AMOUNT, 85000);
        values.put(DbHelper.TYPE, "ICICI");
        values.put(DbHelper.DATE, "07/03/2018 05:27 PM");

        myCR.insert(SqliteProvider.CONTENT_URI, values);
    }

    public Entry queryEntry(String type) {

        String[] projection = {DbHelper.ID, DbHelper.NOTE, DbHelper.AMOUNT, DbHelper.TYPE, DbHelper.DATE};

        String selection = DbHelper.TYPE + " = \"" + type + "\"";

        Cursor cursor = myCR.query(
                SqliteProvider.CONTENT_URI,
                projection,
                selection,
                null,
                null
        );

        Entry entry = new Entry();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            entry.setId(cursor.getString(0));
            entry.setNote(cursor.getString(1));
            entry.setAmount(cursor.getString(2));
            entry.setType(cursor.getString(3));
            entry.setDate(cursor.getString(4));
            cursor.close();
        } else {
            entry = null;
        }

        return entry;
    }

    public void deleteEntry(String type) {

//        boolean result = false;

        String selection = DbHelper.TYPE + " = \"" + type + "\"";

        int rowsDeleted = myCR.delete(SqliteProvider.CONTENT_URI, selection, null);
        Toast.makeText(context, "" + rowsDeleted, Toast.LENGTH_SHORT).show();

//        if (rowsDeleted > 0)
//            result = true;
//
//        return result;
    }

    public void updateEntry(String type) {

        String[] projection = {DbHelper.ID, DbHelper.NOTE, DbHelper.AMOUNT, DbHelper.TYPE, DbHelper.DATE};

        String selection = DbHelper.TYPE + " = \"" + type + "\"";

        ContentValues values = new ContentValues();
        values.put(DbHelper.NOTE, "BALANCE");
//        values.put(DbHelper.AMOUNT, 19200);
        values.put(DbHelper.TYPE, "bank");
        values.put(DbHelper.DATE, "10/03/2018 05:30 PM");

        int  rowsUpdated = myCR.update(
                SqliteProvider.CONTENT_URI,
                values,
                selection,
                null
        );

        Toast.makeText(context, "" + rowsUpdated, Toast.LENGTH_SHORT).show();

    }

}
