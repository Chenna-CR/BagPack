package com.cr7.bagpack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.cr7.bagpack.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.host) as NavHostFragment
        navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.homeFragment,
            R.id.tripPlanningFragment,
            R.id.packingListFragment,
            R.id.expenseTrackerFragment,
            R.id.calendarFragment
        ).build()


}
}