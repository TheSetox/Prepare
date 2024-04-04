package com.thesetox.prepare

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MVVMSampleViewModel : ViewModel() {
    private val _state = MutableStateFlow("")
    val state: StateFlow<String> get() = _state

    fun updateString() = launcher {
        delay(1000)
        _state.emit("update string.")
    }

    fun saveString() = launcher {
        delay(2000)
        _state.emit("Saving String..")
    }

    fun errorString() = launcher {
        delay(3000)
        _state.emit("Error")
    }

    private fun launcher(callFunction: suspend () -> Unit) = viewModelScope.launch {
        callFunction()
    }
}