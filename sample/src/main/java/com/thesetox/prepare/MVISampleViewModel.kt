package com.thesetox.prepare

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MVISampleViewModel : ViewModel() {
    private val _state = MutableStateFlow("")
    val state: StateFlow<String> get() = _state

    fun doAction(sampleAction: SampleAction) = viewModelScope.launch {
        when (sampleAction) {
            SampleAction.Error -> error()
            SampleAction.SaveString -> save()
            SampleAction.UpdateString -> update()
        }
    }

    private suspend fun error() {
        delay(1000)
        _state.emit("Error")
    }

    private suspend fun save() {
        delay(2000)
        _state.emit("Saving String")
    }

    private suspend fun update() {
        delay(3000)
        _state.emit("update string")
    }
}