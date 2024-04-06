package com.thesetox.prepare.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.thesetox.prepare.sample.ui.theme.PrepareTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { PrepareTheme { SampleMVVMScreen() } }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

sealed class SampleAction {
    data object UpdateString : SampleAction()
    data object SaveString : SampleAction()
    data object Error : SampleAction()
}
