package lettuce.hmccompose.ui.component.button

import androidx.compose.runtime.Composable
import lettuce.hmccompose.data.ActionViewData
import lettuce.hmccompose.data.genericbutton.ButtonViewData
import lettuce.hmccompose.data.genericbutton.StylingButtonID

class ButtonComponentAdapter {
    companion object {
        @Composable
        fun GetButtonComponent(
            buttonViewData: ButtonViewData?,
            onClickAction: ((actionVD: ActionViewData?) -> Unit)?
        ) {
            buttonViewData?.stylingId?.run {
                when (StylingButtonID.from(this)) {
                    StylingButtonID.PRIMARY_OUTLINED_ROUNDED ->
                        OutlinedRoundedButtonComponent().Component(
                            buttonViewData,
                            onClickAction
                        )
                }
            }
        }
    }
}