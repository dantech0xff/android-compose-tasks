package com.creative.androidtasks

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.creative.androidtasks.ui.pagertab.PagerTabLayout
import com.creative.androidtasks.ui.floataction.AppFloatActionButton
import com.creative.androidtasks.ui.theme.AndroidTasksTheme
import com.creative.androidtasks.ui.topbar.TopBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

//    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidTasksTheme {
                Scaffold(modifier = Modifier.fillMaxSize(), floatingActionButton = {
                    AppFloatActionButton {
                        Log.d("MainActivity", "FloatingActionButton Clicked")
                    }
                }) { innerPadding ->
                    Column(
                        modifier = Modifier.padding(innerPadding).fillMaxSize(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TopBar()
                        PagerTabLayout()
                    }
                }
            }
        }
    }
}
