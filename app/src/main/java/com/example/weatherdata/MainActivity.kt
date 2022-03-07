package com.example.weatherdata

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.weatherdata.ui.DataListFragment
import com.example.weatherdata.ui.DetailFragment
import com.example.weatherdata.ui.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(R.layout.activity_main), ShowFragmentListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, SearchFragment())
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()
        bottom_navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.page_1 -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, SearchFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack("Search").commit()
                    true
                }
                R.id.page_2 -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, DataListFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack("Datalist").commit()
                    true
                }
                else -> false
            }
        }
    }

    override fun showDetailFragment(name: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, DetailFragment.newInstance(name))
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .addToBackStack("Detail").commit()
    }

    override fun removeDetailFragment() {
        supportFragmentManager.popBackStack()
    }
}