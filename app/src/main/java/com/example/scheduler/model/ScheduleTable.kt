package com.example.scheduler.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.scheduler.database.ScheduleDbHelper

class ScheduleTable {

    companion object {
        private const val TABLE_NAME = "t_schedule"
        private const val COL_ID = "_id"
        private const val COL_DATE = "date"
        private const val COL_TITLE = "title"
        private const val COL_CONTENT = "content"

        private const val TAG = "ScheduleTable"

        const val sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_DATE + " TEXT, " +
                COL_TITLE + " TEXT, " +
                COL_CONTENT + " TEXT" +
                ")"

        fun doInsertSchedule(context: Context, data: ScheduleData) {
            Log.d("ScheduleTable", "#doInsertSchedule")
            val helper = ScheduleDbHelper(context)
            val db = helper.writableDatabase

            insert(db, data)
        }

        private fun insert(db: SQLiteDatabase, data: ScheduleData) {
            val values = ContentValues()

            values.put(COL_DATE, data.date)
            values.put(COL_TITLE, data.title)
            values.put(COL_CONTENT, data.content)

            val count = db.insertOrThrow(TABLE_NAME, null, values)
            Log.d("ScheduleTable", "#insert cnt = $count")
        }

        fun doDeleteSchedule(context: Context, id: Long) {
            val helper = ScheduleDbHelper(context)
            val db = helper.writableDatabase

            db.delete(TABLE_NAME, "_id = ?", arrayOf(id.toString()))
        }

        fun doSelectAll(context: Context): List<ScheduleData> {
            val helper = ScheduleDbHelper(context)
            val db = helper.writableDatabase

            val sql = "SELECT * FROM $TABLE_NAME"
            var cursor: Cursor? = null
            val itemList = mutableListOf<ScheduleData>()

            try {
                cursor = db.rawQuery(sql, null, null)
                if (0 < cursor.count) {
                    var data: ScheduleData
                    while (cursor.moveToNext()) {
                        val id = cursor.getLong(cursor.getColumnIndex(COL_ID))
                        val date = cursor.getString(cursor.getColumnIndex(COL_DATE))
                        val title = cursor.getString(cursor.getColumnIndex(COL_TITLE))
                        val content = cursor.getString(cursor.getColumnIndex(COL_CONTENT))
                        data = ScheduleData(id, date, title, content)
                        itemList.add(data)
                    }
                }

            } catch (e: Exception) {
                Log.e(TAG, "#doSelectAll $e")
            } finally {
                cursor?.close()
                db.close()
            }
            return itemList
        }

        fun update(context: Context, data: ScheduleData) {
            val helper = ScheduleDbHelper(context)
            val db = helper.writableDatabase

            val values = ContentValues()
            values.put(COL_DATE, data.date)
            values.put(COL_TITLE, data.title)
            values.put(COL_CONTENT, data.content)

            val whereClause = "$COL_ID = ?"
            try {
                val count = db.update(TABLE_NAME, values, whereClause, arrayOf(data.id.toString()))
                Log.d(TAG, "#update count -> $count")
            } catch (e: Exception) {
                Log.e(TAG, "#update $e")
            } finally {
                db.close()
            }
        }
    }
}
