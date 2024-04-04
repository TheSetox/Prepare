package com.thesetox.prepare

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true)
@Composable
internal fun SampleMVVMScreenPreview() = PreparePreview { SampleMVVMScreen() }

@Composable
fun SampleMVVMScreen() {
    var state: State<String> = rememberSaveable { mutableStateOf("") }

    var updateString: () -> Unit = {}
    var saveString: () -> Unit = {}
    var errorString: () -> Unit = {}

    PrepareScreen(
        onPreview = {
            state = mutableStateOf("For preview")
        },
        prepareData = {
            val viewModel = MVVMSampleViewModel()
            state = viewModel.state.collectAsState()
            updateString = { viewModel.updateString() }
            saveString = { viewModel.saveString() }
            errorString = { viewModel.errorString() }
        },
        loadScreen = {
            updateString()
            saveString()
            errorString()
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Greeting(state.value)
            }
        }
    )
}