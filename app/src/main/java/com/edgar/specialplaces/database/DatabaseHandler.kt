package com.edgar.specialplaces.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.edgar.specialplaces.models.SpecialPlaceModel

class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "HappyPlacesDatabase"
        private const val TABLE_SPECIAL_PLACE = "HappyPlacesTable"
        private const val KEY_ID = "_id"
        private const val KEY_TITLE = "title"
        private const val KEY_IMAGE = "image"
        private const val KEY_DESCRIPTION = "description"
        private const val KEY_DATE = "date"
        private const val KEY_LOCATION = "location"
        private const val KEY_LATITUDE = "latitude"
        private const val KEY_LONGITUDE = "longitude"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_SPECIAL_PLACE_TABLE = ("CREATE TABLE " + TABLE_SPECIAL_PLACE + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TITLE + " TEXT,"
                + KEY_IMAGE + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_DATE + " TEXT,"
                + KEY_LOCATION + " TEXT,"
                + KEY_LATITUDE + " TEXT,"
                + KEY_LONGITUDE + " TEXT)")
        db?.execSQL(CREATE_SPECIAL_PLACE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_SPECIAL_PLACE")
        onCreate(db)
    }

    fun addSpecialPlace(specialPlace: SpecialPlaceModel): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_TITLE, specialPlace.title)
        contentValues.put(KEY_IMAGE, specialPlace.image)
        contentValues.put(
            KEY_DESCRIPTION,
            specialPlace.description
        )
        contentValues.put(KEY_DATE, specialPlace.date)
        contentValues.put(KEY_LOCATION, specialPlace.location)
        contentValues.put(KEY_LATITUDE, specialPlace.latitude)
        contentValues.put(KEY_LONGITUDE, specialPlace.longitude)

        val result = db.insert(TABLE_SPECIAL_PLACE, null, contentValues)

        db.close()
        return result
    }

    fun updateSpecialPlace(specialPlace: SpecialPlaceModel): Int {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_TITLE, specialPlace.title)
        contentValues.put(KEY_IMAGE, specialPlace.image)
        contentValues.put(
            KEY_DESCRIPTION,
            specialPlace.description
        )
        contentValues.put(KEY_DATE, specialPlace.date)
        contentValues.put(KEY_LOCATION, specialPlace.location)
        contentValues.put(KEY_LATITUDE, specialPlace.latitude)
        contentValues.put(KEY_LONGITUDE, specialPlace.longitude)

        val success = db.update(TABLE_SPECIAL_PLACE, contentValues, KEY_ID + "=" + specialPlace.id, null)

        db.close()
        return success
    }

    fun deleteSpecialPlace(specialPlace: SpecialPlaceModel) : Int{
        val db = this.writableDatabase
        val success = db.delete(TABLE_SPECIAL_PLACE, KEY_ID + "=" + specialPlace.id, null)
        db.close()
        return success
    }

    fun getSpecialPlacesList(): ArrayList<SpecialPlaceModel> {

        val specialPlaceList: ArrayList<SpecialPlaceModel> = ArrayList()
        val selectQuery = "SELECT  * FROM $TABLE_SPECIAL_PLACE"
        val db = this.readableDatabase

        try {
            val cursor: Cursor = db.rawQuery(selectQuery, null)
            if (cursor.moveToFirst()) {
                do {
                    val place = SpecialPlaceModel(
                        cursor.getInt(cursor.getColumnIndex(KEY_ID)),
                        cursor.getString(cursor.getColumnIndex(KEY_TITLE)),
                        cursor.getString(cursor.getColumnIndex(KEY_IMAGE)),
                        cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(KEY_DATE)),
                        cursor.getString(cursor.getColumnIndex(KEY_LOCATION)),
                        cursor.getDouble(cursor.getColumnIndex(KEY_LATITUDE)),
                        cursor.getDouble(cursor.getColumnIndex(KEY_LONGITUDE))
                    )
                    specialPlaceList.add(place)

                } while (cursor.moveToNext())
            }
            cursor.close()
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        return specialPlaceList
    }
}