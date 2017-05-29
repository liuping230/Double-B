package com.lp.double_b.view.provider;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.lp.double_b.view.database.DataBaseHelper;

import java.util.ArrayList;

public class BookDataProvider extends ContentProvider {

    private DataBaseHelper mDataBaseHelper;
    private SQLiteDatabase mDatabase;
    public static Uri CONTENT_URI = Uri.parse("content://com.lp.double_b/double_b");

    static UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int CODE_MANY = 0;

    private static final int CODE_ONE = 1;

    static {
        mUriMatcher.addURI("com.lp.double_b", "double_b", CODE_MANY);
        mUriMatcher.addURI("com.lp.double_b", "double_b/#", CODE_ONE);
    }

    public BookDataProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        //throw new UnsupportedOperationException("Not yet implemented");
        mDatabase = mDataBaseHelper.getWritableDatabase();
        int count;
        long rowId = 0;
        switch (mUriMatcher.match(uri)) {
            case CODE_MANY:
                count = mDatabase.delete(DataBaseHelper.TABLE_NAME, selection, selectionArgs);
                break;
            case CODE_ONE:
                String segment = uri.getPathSegments().get(1);
                rowId = Long.parseLong(segment);
                if (TextUtils.isEmpty(selection)) {
                    selection = "_id=" + segment;
                } else {
                    selection = "_id=" + segment + " AND (" + selection + ")";
                }
                count = mDatabase.delete(DataBaseHelper.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Cannot delete from URL: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        int match = mUriMatcher.match(uri);
        switch (match) {
            case CODE_MANY:
                return "vnd.android.cursor.dir/double_b";
            case CODE_ONE:
                return "vnd.android.cursor.item/double_b";
            default:
                throw new IllegalArgumentException("Unknown URL");
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        if (mUriMatcher.match(uri) != CODE_MANY) {
            throw new IllegalArgumentException("Cannot insert into URL: " + uri);
        }

        ContentValues newValues;
        if (values != null) {
            newValues = new ContentValues(values);
        } else {
            newValues = new ContentValues();
        }

        if (!newValues.containsKey("name"))
            newValues.put("name", "");

        mDatabase = mDataBaseHelper.getWritableDatabase();
        long rowId = mDatabase.insert(DataBaseHelper.TABLE_NAME, null, values);
        if (rowId < 0) {
            throw new SQLException("Failed to insert row into " + uri);
        }
        Uri newUrl = ContentUris.withAppendedId(CONTENT_URI, rowId);
        getContext().getContentResolver().notifyChange(newUrl, null);
        return newUrl;
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        mDataBaseHelper = new DataBaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

       /* SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        int match = mUriMatcher.match(uri);
        switch (match) {
            case CODE_MANY:
                qb.setTables(DataBaseHelper.TABLE_NAME);
                break;
            case CODE_ONE:
                qb.setTables(DataBaseHelper.TABLE_NAME);
                qb.appendWhere("_id=");
                qb.appendWhere(uri.getPathSegments().get(1));
                break;
            default:
                throw new IllegalArgumentException("Unknown URL " + uri);
        }

        mDatabase = mDataBaseHelper.getReadableDatabase();
        //Cursor cursor = mDatabase.query(DataBaseHelper.TABLE_NAME, null, selection, selectionArgs, null, null, sortOrder);
        Cursor cursor = qb.query(mDatabase, projection, selection, selectionArgs,null, null, sortOrder);
        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);

        }*/

        mDatabase = mDataBaseHelper.getReadableDatabase();
        Cursor cursor = null;
        int match = mUriMatcher.match(uri);
        switch (match) {
            case CODE_MANY:
                cursor = mDatabase.query(DataBaseHelper.TABLE_NAME, null, selection, selectionArgs, null, null, sortOrder);
                break;
            case CODE_ONE:
                String segment = uri.getPathSegments().get(1);
                //rowId = Long.parseLong(segment);
                if (TextUtils.isEmpty(selection)) {
                    selection = "_id=" + segment;
                } else {
                    selection = "_id=" + segment + " AND (" + selection + ")";
                }
                cursor = mDatabase.query(DataBaseHelper.TABLE_NAME, null, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Unknown URL " + uri);
        }

        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);

        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        int count;
        long rowId = 0;
        int match = mUriMatcher.match(uri);
       mDatabase = mDataBaseHelper.getWritableDatabase();
        switch (match) {
            case CODE_MANY:
                count = mDatabase.update(DataBaseHelper.TABLE_NAME, values,selection,selectionArgs);
                break;
            case CODE_ONE: {
                String segment = uri.getPathSegments().get(1);
                rowId = Long.parseLong(segment);
                count = mDatabase.update(DataBaseHelper.TABLE_NAME, values, "_id=" + rowId, selectionArgs);
                break;
            }
            default: {
                throw new UnsupportedOperationException(
                        "Cannot update URL: " + uri);
            }
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @NonNull
    @Override
    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations) throws OperationApplicationException {
        return super.applyBatch(operations);
    }
}
