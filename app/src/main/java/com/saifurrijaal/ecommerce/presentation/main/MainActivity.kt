package com.saifurrijaal.ecommerce.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.saifurrijaal.ecommerce.R
import com.saifurrijaal.ecommerce.databinding.ActivityMainBinding
import com.saifurrijaal.ecommerce.presentation.home.HomeFragment
import com.saifurrijaal.ecommerce.presentation.myads.MyAdsFragment
import com.saifurrijaal.ecommerce.presentation.user.UserFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNav()
    }

    override fun onBackPressed() {
        val itemId = binding.btnNavMain.selectedItemId
        if (itemId == R.id.action_home) {
            finish()
        } else {
            openHomeFragment()
        }
    }

    private fun initBottomNav() {
        binding.btnNavMain.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_home -> {
                    openFragment(HomeFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.action_sell -> {

                }
                R.id.action_my_ads -> {
                    openFragment(MyAdsFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.action_user -> {
                    openFragment(UserFragment())
                    return@setOnItemSelectedListener true
                }
            }
            return@setOnItemSelectedListener false
        }
        openHomeFragment()
    }

    private fun openHomeFragment() {
        binding.btnNavMain.selectedItemId = R.id.action_home
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_main, fragment)
            .addToBackStack(null)
            .commit()
    }
}