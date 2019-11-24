package com.example.scheduler

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.scheduler.model.ScheduleTable
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // タイトルリストを取得
        val categoryList = ScheduleTable.getCategory(applicationContext)

        val pager = findViewById<ViewPager>(R.id.view_pager)
        val adapter = TabAdapter(
            supportFragmentManager,
            FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
            categoryList
        )

        pager.adapter = adapter
        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        tabLayout.setupWithViewPager(pager)

        fab.setOnClickListener {
            onFabButtonTapped()
        }
    }

    override fun onResume() {
        Log.d(TAG, "#onResume")
        super.onResume()
    }

    private fun onFabButtonTapped() {
        val intent = Intent(this, SettingActivity::class.java)
        startActivity(intent)
    }
}
