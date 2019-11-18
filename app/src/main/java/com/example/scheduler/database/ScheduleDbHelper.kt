package com.example.scheduler.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.scheduler.model.ScheduleTable

class ScheduleDbHelper(context: Context) : SQLiteOpenHelper(
    context, "scheduler.db", null, 1
) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(ScheduleTable.sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}