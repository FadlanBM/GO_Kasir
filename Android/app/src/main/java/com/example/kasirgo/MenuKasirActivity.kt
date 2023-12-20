package com.example.kasirgo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.kasirgo.AdaptorTL.AdapterMenuTL
import com.example.kasirgo.databinding.ActivityMenuKasirBinding
import com.google.android.material.tabs.TabLayout

class MenuKasirActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMenuKasirBinding
    private lateinit var tabLayout:TabLayout
    private lateinit var viewPager: ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMenuKasirBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tabLayout=binding.tlMenu
        viewPager=binding.vpMenu


        val adapter=AdapterMenuTL(supportFragmentManager,tabLayout.tabCount)
        viewPager.adapter=adapter

        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem=tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

        if (intent.getStringExtra("status")=="kasir"){
            val tab= tabLayout.getTabAt(0)
            tabLayout.selectTab(tab)
        }

    }
}