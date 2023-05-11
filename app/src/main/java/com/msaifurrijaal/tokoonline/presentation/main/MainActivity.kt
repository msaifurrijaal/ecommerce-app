package com.msaifurrijaal.tokoonline.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.msaifurrijaal.tokoonline.BuildConfig
import com.msaifurrijaal.tokoonline.R
import com.msaifurrijaal.tokoonline.databinding.ActivityMainBinding
import com.msaifurrijaal.tokoonline.presentation.home.HomeFragment
import com.msaifurrijaal.tokoonline.presentation.myads.MyAdsFragment
import com.msaifurrijaal.tokoonline.presentation.sell.SellActivity
import com.msaifurrijaal.tokoonline.presentation.user.UserFragment
import com.msaifurrijaal.tokoonline.utils.startActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNavigation()
    }

    override fun onBackPressed() {
        val itemId = binding.btnNavMain.selectedItemId
        if (itemId == R.id.action_home) {
            finish()
        } else {
            openHomeFragment()
        }
    }

    private fun initBottomNavigation() {
        binding.btnNavMain.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.action_home -> {
                    openFragment(HomeFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.action_sell -> {
                    startActivity<SellActivity>()
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