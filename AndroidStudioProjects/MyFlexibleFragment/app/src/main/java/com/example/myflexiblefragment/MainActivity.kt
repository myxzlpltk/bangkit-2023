package com.example.myflexiblefragment

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mHomeFragment = HomeFragment()
        val fragment = supportFragmentManager.findFragmentByTag(HomeFragment::class.java.simpleName)

        if (fragment !is HomeFragment) {
            Log.d("MyFlexibleFragment", "Fragment Name: " + HomeFragment::class.java.simpleName)
            supportFragmentManager.commit {
                add(R.id.frame_container, mHomeFragment, HomeFragment::class.java.simpleName)
            }
        }
    }
}