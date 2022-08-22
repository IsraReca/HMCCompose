package lettuce.hmccompose.ui.component.generics

import androidx.compose.runtime.Composable
import lettuce.hmccompose.data.ActionViewData

interface ComposableComponent<T> {

    @Composable
    fun Component(
        viewData: T,
        onClick: ((actionVD: ActionViewData?) -> Unit)?
    )

    @Composable
    fun Preview()
}