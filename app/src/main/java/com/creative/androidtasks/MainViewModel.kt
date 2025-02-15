package com.creative.androidtasks

import android.util.Log
import androidx.lifecycle.ViewModel
import com.creative.androidtasks.repository.TaskRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by dan on 15/2/25
 *
 * Copyright © 2025 1010 Creative. All rights reserved.
 */

@HiltViewModel
class MainViewModel @Inject constructor(
    private val taskRepo: TaskRepo,
) : ViewModel() {
    init {
        Log.d("MainViewModel", "init 1 $taskRepo")
    }
}