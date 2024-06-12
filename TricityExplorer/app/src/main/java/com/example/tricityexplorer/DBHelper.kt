package com.example.tricityexplorer

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.tricityexplorer.models.Attraction

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "attractions.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_ATTRACTIONS = "attractions"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_DESCRIPTION = "description"
        private const val COLUMN_LATITUDE = "latitude"
        private const val COLUMN_LONGITUDE = "longitude"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_ATTRACTIONS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_DESCRIPTION TEXT,
                $COLUMN_LATITUDE REAL NOT NULL,
                $COLUMN_LONGITUDE REAL NOT NULL
            )
        """
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_ATTRACTIONS")
        onCreate(db)
    }

    fun getAllAttractions(): List<Attraction> {
        val attractions = mutableListOf<Attraction>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_ATTRACTIONS", null)
        cursor.use {
            if (it.moveToFirst()) {
                do {
                    val name = it.getString(it.getColumnIndexOrThrow(COLUMN_NAME))
                    val description = it.getString(it.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
                    val latitude = it.getDouble(it.getColumnIndexOrThrow(COLUMN_LATITUDE))
                    val longitude = it.getDouble(it.getColumnIndexOrThrow(COLUMN_LONGITUDE))
                    attractions.add(Attraction(name, description, latitude, longitude))
                } while (it.moveToNext())
            }
        }
        return attractions
    }

    fun insertAttraction(attraction: Attraction) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, attraction.name)
            put(COLUMN_DESCRIPTION, attraction.description)
            put(COLUMN_LATITUDE, attraction.latitude)
            put(COLUMN_LONGITUDE, attraction.longitude)
        }
        db.insert(TABLE_ATTRACTIONS, null, values)
        db.close()
    }

    fun getAttractions(): List<Attraction> {
        val attractions = mutableListOf<Attraction>()
        val selectQuery = "SELECT * FROM $TABLE_ATTRACTIONS"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                val description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION))
                val latitude = cursor.getDouble(cursor.getColumnIndex(COLUMN_LATITUDE))
                val longitude = cursor.getDouble(cursor.getColumnIndex(COLUMN_LONGITUDE))
                val attraction = Attraction(name, description, latitude, longitude)
                attractions.add(attraction)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return attractions
    }

    fun deleteAttraction(name: String) {
        val db = this.writableDatabase
        db.delete(TABLE_ATTRACTIONS, "$COLUMN_NAME = ?", arrayOf(name))
        db.close()
    }
}
