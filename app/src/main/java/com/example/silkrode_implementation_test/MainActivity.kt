package com.example.silkrode_implementation_test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.silkrode_implementation_test.databinding.ActivityMainBinding
import com.example.silkrode_implementation_test.model.ApiHandler
import com.example.silkrode_implementation_test.ui.user.UserFragment
import com.example.silkrode_implementation_test.ui.user.userViewModel
import com.example.silkrode_implementation_test.ui.mine.MineFragment
import com.example.silkrode_implementation_test.ui.mine.mineViewModel
import com.example.silkrode_implementation_test.ui.userinfo.userInfoViewModel
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startKoin {
            androidLogger()
            androidContext(this@MainActivity)
            modules(listOf(userViewModel, userInfoViewModel, mineViewModel))
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.apply {
            title = "Github"
            //setBackgroundDrawable(ColorDrawable(Color.BLACK))
        }
        binding.viewPager.apply {
            adapter = object : FragmentStateAdapter(this@MainActivity) {
                override fun getItemCount(): Int {
                    return 2
                }

                override fun createFragment(position: Int): Fragment {
                    return when (position) {
                        1 -> MineFragment()
                        else -> UserFragment()
                    }
                }
            }
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    binding.navView.menu.getItem(position).isChecked = true
                }
            })
        }
        binding.navView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_users -> binding.viewPager.currentItem = 0
                R.id.navigation_mine -> binding.viewPager.currentItem = 1
            }
            true
        }
    }

    override fun onStart() {
        super.onStart()
        Logger.addLogAdapter(AndroidLogAdapter())
    }

    override fun onPause() {
        Logger.clearLogAdapters()
        ApiHandler.destroy()
        super.onPause()
    }
}