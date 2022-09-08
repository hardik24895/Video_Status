package com.ganpatibaapaa.motivational.status.video.dbhelper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                URL_COL + " TEXT," +
                THUMB_COL + " TEXT" + ")")

        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addFavStatus(url: String, thumb_url: String) {
        val values = ContentValues()
        values.put(URL_COL, url)
        values.put(THUMB_COL, thumb_url)
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun checkFavStatus(url: String): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $URL_COL = '$url'", null)
    }

    fun deleteFavStatus(url: String) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$URL_COL=?", arrayOf(url))
    }

    fun getAllFavStatus(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }

    companion object {
        private const val DATABASE_NAME = "a1661pe3_Ganesh_App_1"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "videos"
        const val ID_COL = "id"
        const val URL_COL = "url"
        const val THUMB_COL = "thumb_url"
    }

}