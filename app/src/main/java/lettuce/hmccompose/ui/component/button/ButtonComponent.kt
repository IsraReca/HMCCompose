package lettuce.hmccompose.ui.component.button

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import lettuce.hmccompose.data.ActionViewData
import lettuce.hmccompose.data.genericbutton.ButtonViewData
import lettuce.hmccompose.ui.component.generics.ComposableComponent

class ButtonComponent : ComposableComponent<ButtonViewData> {
    @Composable
    override fun Component(
        viewData: ButtonViewData,
        onClick: ((actionVD: ActionViewData?) -> Unit)?
    ) {
        ButtonComponentAdapter.GetButtonComponent(
            buttonViewData = viewData,
            onClickAction = onClick
        )
    }

    @Preview(showBackground = true)
    @Composable
    override fun Preview() {
        val outlinedRoundedButtonViewData = ButtonViewData(
            title = "Search by make or model",
            stylingId = "primaryOutlineRoundedButton",
            actionViewData = ActionViewData("test")
        )
        Component(outlinedRoundedButtonViewData, null)
    }
}