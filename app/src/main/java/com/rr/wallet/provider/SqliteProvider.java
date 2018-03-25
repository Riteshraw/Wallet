package com.rr.wallet.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.rr.wallet.db.DbHelper;

import java.util.HashMap;

public class SqliteProvider extends ContentProvider {

    public static final String PROVIDER_NAME = "com.rr.wallet.provider.SqliteProvider";
    private static final String TABLE_EXPENSE = DbHelper.TABLE_EXPENSE;
    private static final String TABLE_TAB = DbHelper.TABLE_TAB;
    static final String URL_EXPENSE = "content://" + PROVIDER_NAME + "/" + TABLE_EXPENSE;
    static final String URL_TAB = "content://" + PROVIDER_NAME + "/" + TABLE_TAB;

    public static final Uri CONTENT_URI_EXPENSE = Uri.parse(URL_EXPENSE);
    public static final Uri CONTENT_URI_TAB = Uri.parse(URL_TAB);

    public static final int EXPENSE = 1;
    public static final int EXPENSE_ID = 2;
    public static final int TAB = 3;
    public static final int TAB_ID = 4;

    static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(PROVIDER_NAME, TABLE_EXPENSE, EXPENSE);
        uriMatcher.addURI(PROVIDER_NAME, TABLE_EXPENSE + "/#", EXPENSE_ID);

        uriMatcher.addURI(PROVIDER_NAME, TABLE_TAB, TAB);
        uriMatcher.addURI(PROVIDER_NAME, TABLE_TAB + "/#", TAB_ID);
    }

    private static HashMap<String, String> values;
    private DbHelper dbHelper;

    public SqliteProvider() {
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DbHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        Cursor cursor;
        int uriType = uriMatcher.match(uri);
        queryBuilder.setTables(DbHelper.TABLE_EXPENSE);

        switch (uriType) {
            case EXPENSE:
                cursor = queryBuilder.query(dbHelper.getReadableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                break;
            case EXPENSE_ID:
                queryBuilder.appendWhere(DbHelper.DATE + "=" + uri.getLastPathSegment());
                cursor = queryBuilder.query(dbHelper.getReadableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                break;
            case TAB:
                queryBuilder.setTables(DbHelper.TABLE_EXPENSE);
                cursor = queryBuilder.query(dbHelper.getReadableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI");
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        int uriType = uriMatcher.match(uri);

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long id = 0;
        switch (uriType) {
            case EXPENSE:
                id = db.insert(DbHelper.TABLE_EXPENSE, null, contentValues);
                break;
            case TAB:
                id = db.insert(DbHelper.TABLE_TAB, null, contentValues);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(DbHelper.TABLE_EXPENSE + "/" + id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int uriType = uriMatcher.match(uri);
        SQLiteDatabase sqlDB = dbHelper.getWritableDatabase();
        int rowsDeleted = 0;

        switch (uriType) {
            case EXPENSE:
                rowsDeleted = sqlDB.delete(DbHelper.TABLE_EXPENSE, selection, selectionArgs);
                break;

            case EXPENSE_ID:
                String id = uri.getLastPathSegment();
                if (true) {
                    rowsDeleted = sqlDB.delete(DbHelper.TABLE_EXPENSE, DbHelper.ID + "=" + id, null);
                } else {
                    rowsDeleted = sqlDB.delete(DbHelper.TABLE_EXPENSE, DbHelper.ID + "=" + id + " and " + selection, selectionArgs);
                }
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        int uriType = uriMatcher.match(uri);
        SQLiteDatabase sqlDB = dbHelper.getWritableDatabase();
        int rowsUpdated = 0;

        switch (uriType) {
            case EXPENSE:
                rowsUpdated =
                        sqlDB.update(DbHelper.TABLE_EXPENSE,
                                values,
                                selection,
                                selectionArgs);
                break;
            case EXPENSE_ID:
                String id = uri.getLastPathSegment();
                if (true) {
                    rowsUpdated =
                            sqlDB.update(DbHelper.TABLE_EXPENSE,
                                    values,
                                    DbHelper.ID + "=" + id,
                                    null);
                } else {
                    rowsUpdated =
                            sqlDB.update(DbHelper.TABLE_EXPENSE,
                                    values,
                                    DbHelper.ID + "=" + id
                                            + " and "
                                            + selection,
                                    selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " +
                        uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return rowsUpdated;
    }
}
