package com.creative.androidtasks.ui.home

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.creative.androidtasks.MainEvent
import com.creative.androidtasks.MainViewModel
import com.creative.androidtasks.ui.floataction.AppFloatActionButton
import com.creative.androidtasks.ui.pagertab.PagerTabLayout
import com.creative.androidtasks.ui.topbar.TopBar

/**
 * Created by dan on 9/3/25
 *
 * Copyright © 2025 1010 Creative. All rights reserved.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeLayout(mainViewModel: MainViewModel = hiltViewModel()) {
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
            }
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize(), floatingActionButton = {
        AppFloatActionButton {
            isShowAddNoteBottomSheet = taskDelegate.currentCollectionId() > 0
        }
    }) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopBar()
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