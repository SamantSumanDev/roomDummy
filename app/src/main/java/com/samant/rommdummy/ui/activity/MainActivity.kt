package com.samant.rommdummy.ui.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.samant.rommdummy.R
import com.samant.rommdummy.databinding.ActivityMainBinding
import com.samant.rommdummy.ui.fragments.Historyragment
import com.samant.rommdummy.ui.fragments.HomeFragment


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.bottomNav.lnrHome.setOnClickListener {
            homeFragment()
        }

        binding.bottomNav.imgHome.setOnClickListener {
            homeFragment()
        }
        binding.bottomNav.lnrHistory.setOnClickListener {
            historyFragment()
        }

        binding.bottomNav.imgHistory.setOnClickListener {
            historyFragment()
        }

        binding.bottomNav.view2.setOnClickListener{
            val intent = Intent(this, AddNewTaskActivity::class.java)
            startActivity(intent)
        }
        binding.bottomNav.imgAdd.setOnClickListener {
            val intent = Intent(this, AddNewTaskActivity::class.java)
            startActivity(intent)
        }

        if (savedInstanceState == null) {
            homeFragment()
        }



    }

    private fun homeFragment(){
        loadFragment(HomeFragment())
        val selectedColor = Color.parseColor("#7EBB4F")
        val selectedtxtcolor = Color.parseColor("#FF000000")
        val unselectedColor = Color.parseColor("#8D2D2D30")
        binding.bottomNav.imgHome.setColorFilter(selectedColor)
        binding.bottomNav.tvHome.setTextColor(selectedtxtcolor)
        binding.bottomNav.imgHistory.setColorFilter(unselectedColor)
        binding.bottomNav.tvHistory.setTextColor(unselectedColor)
    }

    private fun historyFragment(){
        loadFragment(Historyragment())
        val selectedColor = Color.parseColor("#7EBB4F")
        val selectedtxtcolor = Color.parseColor("#FF000000")
        val unselectedColor = Color.parseColor("#8D2D2D30")
        binding.bottomNav.imgHome.setColorFilter(unselectedColor)
        binding.bottomNav.tvHome.setTextColor(unselectedColor)
        binding.bottomNav.imgHistory.setColorFilter(selectedColor)
        binding.bottomNav.tvHistory.setTextColor(selectedtxtcolor)
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }


}