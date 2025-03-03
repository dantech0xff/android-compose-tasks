package com.creative.androidtasks

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.creative.androidtasks.ui.pagertab.PagerTabLayout
import com.creative.androidtasks.ui.floataction.AppFloatActionButton
import com.creative.androidtasks.ui.theme.AndroidTasksTheme
import com.creative.androidtasks.ui.topbar.TopBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidTasksTheme {
                val listTabGroup by mainViewModel.listTabGroup.collectAsStateWithLifecycle(emptyList())
                val taskDelegate = remember { mainViewModel }
                var isShowAddNoteBottomSheet by remember { mutableStateOf(false) }
                var isShowAddTaskCollectionBottomSheet by remember { mutableStateOf(false) }

                LaunchedEffect(Unit) {
                    mainViewModel.eventFlow.collect {
                        when (it) {
                            MainEvent.RequestAddNewCollection -> {
                                isShowAddTaskCollectionBottomSheet = true
                            }

                            else -> {}
                        }
                    }
                }

                Scaffold(modifier = Modifier.fillMaxSize(), floatingActionButton = {
                    AppFloatActionButton {
                        isShowAddNoteBottomSheet = true
                    }
                }) { innerPadding ->
                    Column(
                        modifier = Modifier.padding(innerPadding).fillMaxSize(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TopBar(taskDelegate)
                        if (listTabGroup.isNotEmpty()) {
                            PagerTabLayout(listTabGroup, taskDelegate)
                        } else {
                            Text("NO TASKS AVAILABLE!!!")
                        }
                    }

                    if (isShowAddNoteBottomSheet) {
                        var inputTaskContent by remember { mutableStateOf("") }
                        ModalBottomSheet({
                            isShowAddNoteBottomSheet = false
                        }) {
                            Text("Input Task Content", modifier = Modifier.fillMaxWidth())
                            TextField(
                                value = inputTaskContent,
                                onValueChange = { inputTaskContent = it },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Button({
                                if (inputTaskContent.isNotEmpty()) {
                                    taskDelegate.addNewTaskToCurrentCollection(inputTaskContent)
                                    inputTaskContent = ""
                                }
                                isShowAddNoteBottomSheet = false
                            }, modifier = Modifier.fillMaxWidth()) {
                                Text("Add Task")
                            }
                        }
                    }

                    if (isShowAddTaskCollectionBottomSheet) {
                        var inputTaskCollection by remember { mutableStateOf("") }
                        ModalBottomSheet({
                            isShowAddTaskCollectionBottomSheet = false
                        }) {
                            Text("Input Task Collection", modifier = Modifier.fillMaxWidth())
                            TextField(
                                value = inputTaskCollection,
                                onValueChange = { inputTaskCollection = it },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Button({
                                if (inputTaskCollection.isNotEmpty()) {
                                    taskDelegate.addNewCollection(inputTaskCollection)
                                    inputTaskCollection = ""
                                }
                                isShowAddTaskCollectionBottomSheet = false
                            }, modifier = Modifier.fillMaxWidth()) {
                                Text("Add Task Collection")
                            }
                        }
                    }
                }
            }
        }
    }
}
