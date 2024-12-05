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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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

        setupNavigation()

        if (savedInstanceState == null) {
            homeFragment()
        }
    }

    private fun setupNavigation() {
        binding.bottomNav.apply {
            lnrHome.setOnClickListener { homeFragment() }
            imgHome.setOnClickListener { homeFragment() }
            lnrHistory.setOnClickListener { historyFragment() }
            imgHistory.setOnClickListener { historyFragment() }
            view2.setOnClickListener { startActivity(Intent(this@MainActivity, AddNewTaskActivity::class.java)) }
            imgAdd.setOnClickListener { startActivity(Intent(this@MainActivity, AddNewTaskActivity::class.java)) }
        }
    }

    private fun homeFragment() {
        loadFragment(HomeFragment())
        updateNavigationUI(selectedHome = true)
    }

    private fun historyFragment() {
        loadFragment(Historyragment())
        updateNavigationUI(selectedHome = false)
    }

    private fun updateNavigationUI(selectedHome: Boolean) {
        val selectedColor = Color.parseColor("#7EBB4F")
        val selectedTxtColor = Color.parseColor("#FF000000")
        val unselectedColor = Color.parseColor("#8D2D2D30")

        binding.bottomNav.apply {
            imgHome.setColorFilter(if (selectedHome) selectedColor else unselectedColor)
            tvHome.setTextColor(if (selectedHome) selectedTxtColor else unselectedColor)
            imgHistory.setColorFilter(if (selectedHome) unselectedColor else selectedColor)
            tvHistory.setTextColor(if (selectedHome) unselectedColor else selectedTxtColor)
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
