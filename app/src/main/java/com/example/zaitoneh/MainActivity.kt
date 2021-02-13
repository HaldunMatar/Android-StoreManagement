package com.example.zaitoneh

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.zaitoneh.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_store_tracker.*

class MainActivity: AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {nav_host_fragment
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        drawerLayout = binding.drawerLayout
        val navController = this.findNavController(R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        //appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navController)


        NavigationUI.setupActionBarWithNavController(this, navController)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);

        bottomAppBar.setNavigationOnClickListener {
            val toast =
                Toast.makeText(applicationContext, "setNavigationOnClickListener  ",
                    Toast.LENGTH_LONG
                ).show()
        }


        floatingActionButton.setOnClickListener {


            val toast =
            Toast.makeText(applicationContext, "setNavigationOnClickListener  ",
                Toast.LENGTH_LONG
            ).show() }
        bottomAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {

                R.id.employeeTrackerFragment -> {
                    val toast =
                        Toast.makeText(applicationContext, "employeeTrackerFragment employeeTrackerFragment ",
                            Toast.LENGTH_LONG
                        ).show()
                    true
                }
                R.id.receiptTrackerFragment2 -> {
                    val toast =
                        Toast.makeText(applicationContext, "setOnMenuItemClickListener more ",
                            Toast.LENGTH_LONG
                        ).show()
                    true
                }
                else -> false
            }
        }


        val   bottomAppBar =binding.bottomAppBar
        val   floatingActionButton = binding.floatingActionButton
        navController.addOnDestinationChangedListener { nc: NavController, nd: NavDestination, args: Bundle? ->
            if (nd.id == nc.graph.startDestination) {
                if (bottomAppBar != null) {
                    bottomAppBar.visibility=  View.GONE
                }
                if (floatingActionButton != null) {
                    floatingActionButton.visibility=  View.GONE
                }
              ///*****************
            } else {

                    bottomAppBar.visibility=  View.VISIBLE
                floatingActionButton.visibility=  View.VISIBLE

            }
        }

    }




    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment)
         //  onBackPressed()
        return NavigationUI.navigateUp(navController,drawerLayout )
    }

    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        return super.onCreateView(parent, name, context, attrs)
    }



    // @Override
override fun onBackPressed() {
       val navController = this.findNavController(R.id.nav_host_fragment)
              navController.popBackStack();
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

/*
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        val id = item.getItemId()

        if (id == R.id.store) {
            Toast.makeText(this, "Item One Clicked", Toast.LENGTH_LONG).show()
            return true
        }
        if (id == R.id.receiptTrackerFragment2) {
            Toast.makeText(this, "Item Two Clicked", Toast.LENGTH_LONG).show()
            return true
        }
        if (id == R.id.employeeTrackerFragment) {
            Toast.makeText(this, "Item Three Clicked", Toast.LENGTH_LONG).show()
            return true
        }

        return super.onOptionsItemSelected(item)

    }*/


}
