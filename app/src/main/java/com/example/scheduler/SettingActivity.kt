package com.example.scheduler


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.scheduler.model.ScheduleData
import com.example.scheduler.worker.DbWorker

class SettingActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var dateEntry: EditText
    lateinit var titleEntry: EditText
    lateinit var contentEntry: EditText
    lateinit var categoryEntry:EditText
    var scheduleId: Long = -1

    companion object {
        private const val TAG = "SettingActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setting_activity)

        dateEntry = findViewById(R.id.setting_date_edit_text)
        titleEntry = findViewById(R.id.setting_title_edit_text)
        contentEntry = findViewById(R.id.setting_content_edit_text)
        categoryEntry = findViewById(R.id.setting_category_edit_text)

        findViewById<Button>(R.id.setting_save_btn).setOnClickListener(this)
        findViewById<Button>(R.id.setting_delete_btn).setOnClickListener(this)

        val data = intent.getParcelableExtra<ScheduleData>("schedule_data")
        scheduleId = data?.id ?: -1L

        if (data != null) {
            Log.d(TAG, "#onCreate id = $scheduleId date = " + data.date)

            dateEntry.setText(data.date)
            titleEntry.setText(data.title)
            contentEntry.setText(data.content)
            categoryEntry.setText(data.category)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.setting_save_btn -> onSaveButtonTapped()
            R.id.setting_delete_btn -> onDeleteButtonTapped()
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun onSaveButtonTapped() {
        if (scheduleId == -1L) {
            DbWorker.startInsertDbWorker(
                dateEntry.text.toString(),
                titleEntry.text.toString(),
                contentEntry.text.toString(),
                categoryEntry.text.toString()
            )
        } else {
            DbWorker.startUpdateDbWorker(
                scheduleId,
                dateEntry.text.toString(),
                titleEntry.text.toString(),
                contentEntry.text.toString(),
                categoryEntry.text.toString()
            )
        }

        applicationContext.show("保存しました")
        finish()
    }

    private fun onDeleteButtonTapped() {
        DbWorker.startDeleteDbWorker(scheduleId)
        applicationContext.show("削除しました")
        finish()
    }
}