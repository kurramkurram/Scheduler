package com.example.scheduler

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.scheduler.model.ScheduleData
import com.example.scheduler.model.ScheduleTable
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), TabFragment.OnListItemClickListener {

    lateinit var pager: ViewPager
    private lateinit var tabLayout: TabLayout

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pager = findViewById(R.id.view_pager)
        tabLayout = findViewById(R.id.tab_layout)

        fab.setOnClickListener {
            onFabButtonTapped()
        }
    }

    override fun onResume() {
        super.onResume()
        // タイトルリストを取得
        val categoryList = ScheduleTable.getCategory(applicationContext)
        val adapter = TabAdapter(
            supportFragmentManager,
            FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
            categoryList
        )
        pager.adapter = adapter

        tabLayout.setupWithViewPager(pager)
    }

    override fun onListItemClicked(data: ScheduleData) {
        onListItemTapped(data)
    }

    private fun onFabButtonTapped() {
        val intent = Intent(this, SettingActivity::class.java)
        startActivity(intent)
    }

    private fun onListItemTapped(data: ScheduleData) {
        val intent = Intent(this, SettingActivity::class.java)
        intent.putExtra("schedule_data", data)
        startActivity(intent)
    }
}
