package com.example.newproject.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.newproject.R
import com.example.newproject.databinding.ActivityMainBinding
import com.example.newproject.utils.hide
import com.example.newproject.utils.visible
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {
    private lateinit var binding: ActivityMainBinding
    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        setUpToolbar()
        setUpNavigation()
        onClickListeners()
        setUpBottomBar()
        bottomNavigationItemClicks()
    }

    private fun setUpBottomBar() {
        navController?.let { binding.bottomNavigationView.setupWithNavController(it) }
    }

    private fun bottomNavigationItemClicks() {
            binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.fragmentA -> {
                        navController?.navigate(R.id.fragmentA)
                        true
                    }
                    R.id.fragmentB-> {
                        navController?.navigate(R.id.fragmentB)
                        true
                    }
                    else -> false
                }
            }
        }


    private fun onClickListeners() {
        with(binding)
        {
            toolbarlayout.backArrow.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    private fun setUpToolbar() {
        setSupportActionBar(binding?.toolbarlayout?.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun setUpNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController = navHostFragment.navController
        navController?.let { it.addOnDestinationChangedListener(this) }
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {

        when (destination.id) {
            R.id.fragmentA -> {
                with(binding) {
                    toolbarlayout.backArrow.hide()
                    toolbarlayout.tvToolbarTitle.text = "Home"
                }
            }

            R.id.fragmentB -> {
                with(binding)
                {
                    toolbarlayout.backArrow.visible()
                    toolbarlayout.tvToolbarTitle.text = "Favorites"
                }

            }

        }
    }

    override fun onBackPressed() {
        if(navController?.currentDestination?.id==R.id.fragmentB) {
            navController?.popBackStack(R.id.fragmentA, false)
        }

        else if(navController?.currentDestination?.id==R.id.fragmentA){
            finish()
            }
        else{
            super.onBackPressed()
        }

    }




}