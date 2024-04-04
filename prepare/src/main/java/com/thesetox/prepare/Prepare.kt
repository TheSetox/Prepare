package com.thesetox.prepare

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf

/**
 * Used for checking if the compose is on preview or not.
 */
private val LocalPreviewMode: ProvidableCompositionLocal<Boolean> = compositionLocalOf { false }

/**
 * Used for Screen level composable.
 */
@Composable
fun PrepareScreen(
    onPreview: () -> Unit = {},
    prepareData: @Composable () -> Unit = {},
    onDialog: @Composable () -> Unit = {},
    loadScreen: @Composable () -> Unit,
) {
    if (LocalPreviewMode.current) onPreview() else prepareData()

    loadScreen()
    onDialog()
}

/**
 * Used for App level composable.
 */
@Composable
fun PrepareApp(
    onPreview: () -> Unit = {},
    prepareData: @Composable () -> Unit = {},
    onDialog: @Composable () -> Unit = {},
    loadScreen: @Composable () -> Unit,
) {
    if (LocalPreviewMode.current) onPreview() else prepareData()

    loadScreen()
    onDialog()
}

/**
 * Used for composable level composable.
 */
@Composable
fun PrepareComposable(
    onPreview: () -> Unit = {},
    prepareData: @Composable () -> Unit = {},
    loadScreen: @Composable () -> Unit,
) {
    if (LocalPreviewMode.current) onPreview() else prepareData()

    loadScreen()
}

/**
 * Used for composable preview.
 */
@Composable
fun PreparePreview(composablePreview: @Composable () -> Unit) {
    CompositionLocalProvider(LocalPreviewMode provides true) {
        composablePreview()
    }
}