package lettuce.hmccompose.ui.component.generics

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import lettuce.hmccompose.data.ComponentViewData
import lettuce.hmccompose.ui.theme.PreviewModifier

interface ComposableComponent<T> where T: ComponentViewData{
    var previewViewData: T?

    @Composable
    fun Component(viewData: T)

    @Composable
    fun ComponentPreview() {
        Box(
            modifier = PreviewModifier
        ) {
            previewViewData?.let { Component(it) }
        }
    }
}