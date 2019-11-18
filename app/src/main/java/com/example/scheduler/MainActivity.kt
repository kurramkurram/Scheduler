package com.example.scheduler

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.scheduler.model.ScheduleData
import com.example.scheduler.model.ScheduleTable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            onFabButtonTapped()
        }
    }

    override fun onResume() {
        super.onResume()

        val itemList: List<ScheduleData> = ScheduleTable.doSelectAll(applicationContext)
        val listView = findViewById<ListView>(R.id.list_view)
        listView.setOnItemClickListener { parent, _, position, _ ->
            val data: ScheduleData = parent.getItemAtPosition(position) as ScheduleData
            onListTapped(data)
        }
        listView.adapter = ScheduleAdapter(applicationContext, itemList)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun onFabButtonTapped() {
        val intent = Intent(this, SettingActivity::class.java)
        startActivity(intent)
    }

    private fun onListTapped(data: ScheduleData) {
        val intent = Intent(this, SettingActivity::class.java)
        intent.putExtra("schedule_id", data.id)
        startActivity(intent)
    }
}
