package com.example.scheduler.worker

import android.content.Context
import android.util.Log
import androidx.work.*
import com.example.scheduler.model.ScheduleData
import com.example.scheduler.model.ScheduleTable

class DbWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork() = try {

        Log.d(
            "DbWorker",
            "#doWork schedule_type = " + inputData.getInt(WorkerDefine.SCHEDULE_TYPE, -1)
        )

        when (inputData.getInt(WorkerDefine.SCHEDULE_TYPE, -1)) {
            WorkerDefine.TYPE_SCHEDULE_SAVE -> {
                val data = createScheduleData()
                ScheduleTable.doInsertSchedule(applicationContext, data)
            }

            WorkerDefine.TYPE_SCHEDULE_DELETE -> {
                val id = inputData.getLong(WorkerDefine.SCHEDULE_ID, -1)

                if (id != -1L) {
                    ScheduleTable.doDeleteSchedule(applicationContext, id)
                }
            }

            WorkerDefine.TYPE_SCHEDULE_UPDATE -> {
                val data = createScheduleData()
                data.id = inputData.getLong(WorkerDefine.SCHEDULE_ID, -1)

                if (data.id != -1L) {
                    ScheduleTable.update(applicationContext, data)
                }
            }
        }

        Result.success()
    } catch (e: Exception) {
        Result.failure()
    }

    private fun createScheduleData(): ScheduleData {
        Log.d("DbWorker", "#createScheduleData")

        val date = inputData.getString(WorkerDefine.SCHEDULE_DATE)
        val title = inputData.getString(WorkerDefine.SCHEDULE_TITLE)
        val content = inputData.getString(WorkerDefine.SCHEDULE_CONTENT)
        val category = inputData.getString(WorkerDefine.SCHEDULE_CATEGORY)

        return ScheduleData(0, date, title, content, category)
    }

    companion object {

        private const val TAG = "DbWorker"

        fun startInsertDbWorker(date: String, title: String, content: String, category: String) {
            Log.d(TAG, "#startInsertDbWorker")

            val data = Data.Builder().apply {
                putInt(WorkerDefine.SCHEDULE_TYPE, WorkerDefine.TYPE_SCHEDULE_SAVE)
                putString(WorkerDefine.SCHEDULE_DATE, date)
                putString(WorkerDefine.SCHEDULE_TITLE, title)
                putString(WorkerDefine.SCHEDULE_CONTENT, content)
                putString(WorkerDefine.SCHEDULE_CATEGORY, category)
            }.build()

            val worker = OneTimeWorkRequestBuilder<DbWorker>()
                .apply { setInputData(data) }.build()

            WorkManager.getInstance().enqueue(worker)
        }

        fun startUpdateDbWorker(
            id: Long,
            date: String,
            title: String,
            content: String,
            category: String
        ) {
            Log.d(TAG, "#startUpdateDbWorker")

            val data = Data.Builder().apply {
                putInt(WorkerDefine.SCHEDULE_TYPE, WorkerDefine.TYPE_SCHEDULE_UPDATE)
                putLong(WorkerDefine.SCHEDULE_ID, id)
                putString(WorkerDefine.SCHEDULE_DATE, date)
                putString(WorkerDefine.SCHEDULE_TITLE, title)
                putString(WorkerDefine.SCHEDULE_CONTENT, content)
                putString(WorkerDefine.SCHEDULE_CATEGORY, category)
            }.build()

            val worker = OneTimeWorkRequestBuilder<DbWorker>()
                .apply { setInputData(data) }.build()

            WorkManager.getInstance().enqueue(worker)
        }

        fun startDeleteDbWorker(id: Long) {
            Log.d(TAG, "#startDeleteDbWorker")

            val data = Data.Builder().apply {
                putInt(WorkerDefine.SCHEDULE_TYPE, WorkerDefine.TYPE_SCHEDULE_DELETE)
                putLong(WorkerDefine.SCHEDULE_ID, id)
            }.build()

            val worker = OneTimeWorkRequestBuilder<DbWorker>()
                .apply { setInputData(data) }.build()

            WorkManager.getInstance().enqueue(worker)
        }
    }
}