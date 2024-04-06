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
 * A utility composable for preparing and loading composable.
 *
 * This composable facilitates the organization of logic for preview execution, data preparation,
 * and loading of a specific composable.
 *
 *
 * ### Example Usage:
 * ```
 *     Prepare(
 *         preview = {
 *             state = mutableStateOf("For preview")
 *         },
 *         data = {
 *             val viewModel = MVISampleViewModel()
 *             state = viewModel.state.collectAsState()
 *             doAction = { viewModel.doAction(it) }
 *         },
 *         screen = {
 *             doAction(SampleAction.Error)
 *             doAction(SampleAction.SaveString)
 *             doAction(SampleAction.UpdateString)
 *             Surface(
 *                 modifier = Modifier.fillMaxSize(),
 *                 color = MaterialTheme.colorScheme.background
 *             ) {
 *                 Greeting(state.value)
 *             }
 *         }
 *     )
 * ```
 *
 * @param preview A lambda to execute preview-specific logic. It's triggered only when the
 * PreparePreview composable is used. **(Optional)**
 * @param data A lambda to execute data preparation logic. It's triggered when
 * PreparePreview is not called in the composable. **(Optional)**
 * @param dialog A lambda to separate composable components related to dialog handling.
 * It's expected to contain composable components. **(Optional)**
 * @param screen The main composable for the app level.
 *
 */
@Composable
fun Prepare(
    preview: () -> Unit = {},
    data: @Composable () -> Unit = {},
    dialog: @Composable () -> Unit = {},
    screen: @Composable () -> Unit,
) {
    if (LocalPreviewMode.current) preview() else data()

    screen()
    dialog()
}

/**
 * A utility composable for previewing other composable within Compose preview environment.
 *
 * This composable sets up the environment for preview mode and executes the provided composablePreview.
 *
 * ### Example Usage:
 * ```
 * PreparePreview {
 *     SampleComposable()
 * }
 * ```
 *
 * @param composablePreview The composable to be previewed.
 *
 */
@Composable
fun PreparePreview(composablePreview: @Composable () -> Unit) {
    CompositionLocalProvider(LocalPreviewMode provides true) {
        composablePreview()
    }
}