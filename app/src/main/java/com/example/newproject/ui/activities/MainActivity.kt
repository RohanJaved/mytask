package com.example.newproject.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import com.example.newproject.R
import com.example.newproject.databinding.ActivityMainBinding
import com.example.newproject.utils.hide
import com.example.newproject.utils.visible

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
                    toolbarlayout.tvToolbarTitle.text = "First"
                }
            }

            R.id.fragmentB -> {
                with(binding)
                {
                    toolbarlayout.backArrow.visible()
                    toolbarlayout.tvToolbarTitle.text = "Second"
                }

            }

        }
    }

}