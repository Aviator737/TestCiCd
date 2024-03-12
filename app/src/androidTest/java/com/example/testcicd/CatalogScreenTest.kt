package com.example.testcicd

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.testcicd.ui.screen.home.catalog.CatalogScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CatalogScreenTest {

    @get:Rule
    val rule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun testLabel() {
        rule.setContent { CatalogScreen() }
        val label = "example test fail"
        rule.onNodeWithText(label).assertExists()
    }
}
