package lettuce.hmccompose.ui.adapters

import androidx.compose.runtime.Composable
import lettuce.hmccompose.GenericButton
import lettuce.hmccompose.data.ActionViewData
import lettuce.hmccompose.data.ComponentViewData
import lettuce.hmccompose.data.complextext.ComplexTextViewData
import lettuce.hmccompose.data.formcontroltextbox.FormControlTextBoxViewData
import lettuce.hmccompose.data.genericbutton.GenericButtonViewData
import lettuce.hmccompose.data.sectioncontainer.SectionContainerViewData
import lettuce.hmccompose.ui.component.ComplexText
import lettuce.hmccompose.ui.component.FormControlTextBox
import lettuce.hmccompose.ui.component.SectionContainer

class ComponentAdapter {
    companion object {
        @Composable
        fun getComponentByViewData(
            componentVD: ComponentViewData,
            onClickAction: (actionVD: ActionViewData?) -> Unit
        ) {
            when (componentVD) {
                is ComplexTextViewData -> {
                    ComplexText().Component(
                        viewData = componentVD,
                    )
                }
                is FormControlTextBoxViewData -> {
                    FormControlTextBox().Component(
                        viewData = componentVD,
                    )
                }
                is GenericButtonViewData -> {
                    GenericButton().Component(
                        viewData = componentVD,
                        onClick = onClickAction
                    )
                }
                is SectionContainerViewData -> {
                    SectionContainer().Component(
                        viewData = componentVD,
                        onClick = onClickAction
                    )
                }
                else -> {}
            }
        }
    }
}