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
internal fun SampleMVIScreenPreview() = PreparePreview { SampleMVIScreen() }

@Composable
fun SampleMVIScreen() {
    var state: State<String> = rememberSaveable { mutableStateOf("") }
    var doAction: (SampleAction) -> Unit = {}

    Prepare(
        preview = {
            state = mutableStateOf("For preview")
        },
        data = {
            val viewModel = MVISampleViewModel()
            state = viewModel.state.collectAsState()
            doAction = { viewModel.doAction(it) }
        },
        screen = {
            doAction(SampleAction.Error)
            doAction(SampleAction.SaveString)
            doAction(SampleAction.UpdateString)
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Greeting(state.value)
            }
        }
    )
}