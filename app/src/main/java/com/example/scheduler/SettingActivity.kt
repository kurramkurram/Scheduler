package com.example.scheduler


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.scheduler.model.ScheduleData
import com.example.scheduler.worker.DbWorker
import java.text.SimpleDateFormat

class SettingActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var dateEntry: EditText
    lateinit var titleEntry: EditText
    lateinit var contentEntry: EditText
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

        findViewById<Button>(R.id.setting_save_btn).setOnClickListener(this)
        findViewById<Button>(R.id.setting_delete_btn).setOnClickListener(this)

        val data = intent.getParcelableExtra<ScheduleData>("schedule_data")
        scheduleId = data!!.id

        if (scheduleId != -1L) {
            Log.d(TAG, "#onCreate id = $scheduleId date = " + data.date)

            dateEntry.setText(data.date)
            titleEntry.setText(data.title)
            contentEntry.setText(data.content)
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
        val sdf = SimpleDateFormat("yyyy/MM/dd")
        val date = sdf.parse(dateEntry.text.toString())
        val dateString = date?.toString() ?: ""


        if (scheduleId == -1L) {
            DbWorker.startInsertDbWorker(
                dateString,
                titleEntry.text.toString(),
                contentEntry.text.toString()
            )
        } else {
            DbWorker.startUpdateDbWorker(
                scheduleId,
                dateString,
                titleEntry.text.toString(),
                contentEntry.text.toString()
            )
        }

        Toast.makeText(applicationContext, "保存しました", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun onDeleteButtonTapped() {
        DbWorker.startDeleteDbWorker(scheduleId)
        Toast.makeText(applicationContext, "削除しました", Toast.LENGTH_SHORT).show()
        finish()
    }
}