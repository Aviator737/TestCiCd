package com.example.testcicd.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.testcicd.ui.navigation.AppNavigation
import com.example.testcicd.ui.theme.AppTheme
import com.google.firebase.analytics.FirebaseAnalytics

class MainActivity : ComponentActivity() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        setContent {
            val navController = rememberNavController()

            navController.addOnDestinationChangedListener { _, destination, _ ->
                val bundle = Bundle()
                bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, destination.route)
                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
            }

            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = AppTheme.colors.material.background
                ) {
                    AppNavigation(navController = navController)
                }
            }
        }
    }
}
