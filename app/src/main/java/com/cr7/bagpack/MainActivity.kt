package com.cr7.bagpack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.cr7.bagpack.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.host) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
navController.addOnDestinationChangedListener{_, destination, _ ->
    when(destination.id){
        R.id.homeFragment-> binding.tittleToolbar.setText("Bagpack")
        R.id.packingListFragment -> binding.tittleToolbar.setText("Packing List")
        R.id.tripPlanningFragment -> binding.tittleToolbar.setText("Trip Planning")
        R.id.calendarFragment-> binding.tittleToolbar.setText("Calendar")
        R.id.expenseTrackerFragment-> binding.tittleToolbar.setText("Expense Tracker")
    }
}

}
    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp() || navController.popBackStack()
    }
}