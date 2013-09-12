package com.example.dbtest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class Database {

    public static final String KEY_ACC= "Account";
    public static final String KEY_AMT = "Amount";
    public static final String KEY_ROWID = "_id";

    private static final String TAG = "Database";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;    

    private static final String DATABASE_NAME = "WhatIGot";
    private static final String DATABASE_TABLE = "Account";
    private static final int DATABASE_VERSION = 2;
    
    private static final String DATABASE_CREATE =
    		"CREATE TABLE " + DATABASE_TABLE + " ( " + KEY_ROWID + " integer PRIMARY KEY autoincrement, " + KEY_ACC + " STRING, " + KEY_AMT + " DOUBLE );";

    private final Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS notes");
            onCreate(db);
        }
    }
  
    public Database(Context ctx) {
        this.mCtx = ctx;
    }

    public Database open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    public long createNote(Accounts accounts) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_ACC, accounts.getText());
        initialValues.put(KEY_AMT, accounts.getAmount());
        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }
    
    //use this to delete an Account
    public boolean deleteNote(long rowId) {
        return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }
    
    public Cursor fetchAllNotes() {
        return mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_ACC,
                KEY_AMT}, null, null, null, null, null);
    }
    
    public Cursor getAllAmount(){    	
    	return mDb.query(DATABASE_TABLE, new String[] {
                KEY_AMT}, null, null, null, null, null);  	
    	
    }

    /**
     * Return a Cursor positioned at the note that matches the given rowId
     * 
     * @param rowId id of note to retrieve
     * @return Cursor positioned to matching note, if found
     * @throws SQLException if note could not be found/retrieved
     */
    public Cursor fetchNote(long rowId) throws SQLException {

        Cursor mCursor =
            mDb.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
            		KEY_ACC, KEY_AMT}, KEY_ROWID + "=" + rowId, null,
                    null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

    /**
     * Update the note using the details provided. The note to be updated is
     * specified using the rowId, and it is altered to use the title and body
     * values passed in
     * 
     * @param rowId id of note to update
     * @param title value to set note title to
     * @param body value to set note body to
     * @return true if the note was successfully updated, false otherwise
     */
    public boolean updateNote(long rowId, String title, String body) {
        ContentValues args = new ContentValues();
        args.put(KEY_ACC, title);
        args.put(KEY_AMT, body);

        return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
}
