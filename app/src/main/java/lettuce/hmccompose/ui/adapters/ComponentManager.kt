package lettuce.hmccompose.ui.adapters

import androidx.compose.runtime.Composable
import lettuce.hmccompose.data.ActionViewData
import lettuce.hmccompose.data.ComponentViewData
import lettuce.hmccompose.data.complextext.ComplexTextViewData
import lettuce.hmccompose.data.formcontroltextbox.FormControlTextBoxViewData
import lettuce.hmccompose.data.genericbutton.ButtonViewData
import lettuce.hmccompose.data.groupedoptions.GroupedOptionsViewData
import lettuce.hmccompose.data.sectioncontainer.SectionContainerViewData
import lettuce.hmccompose.ui.component.ComplexText
import lettuce.hmccompose.ui.component.FormControlTextBox
import lettuce.hmccompose.ui.component.GroupedOptions
import lettuce.hmccompose.ui.component.SectionContainer
import lettuce.hmccompose.ui.component.button.ButtonComponentManager

class ComponentManager {
    companion object {
        @Composable
        fun ComponentByViewData(
            componentVD: ComponentViewData,
            onClickAction: ((actionVD: ActionViewData?) -> Unit)?
        ) {
            when (componentVD) {
                is ComplexTextViewData -> {
                    ComplexText(
                        viewData = componentVD,
                        null
                    )
                }
                is FormControlTextBoxViewData -> {
                    FormControlTextBox(
                        viewData = componentVD
                    )
                }
                is ButtonViewData -> {
                    ButtonComponentManager(
                        buttonViewData = componentVD,
                        onClickAction = onClickAction
                    )
                }
                is SectionContainerViewData -> {
                    SectionContainer(
                        viewData = componentVD,
                        onClick = onClickAction
                    )
                }
                is GroupedOptionsViewData -> {
                    GroupedOptions(
                        viewData = componentVD
                    )
                }
                else -> {}
            }
        }
    }
}