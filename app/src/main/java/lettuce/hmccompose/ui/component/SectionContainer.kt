package lettuce.hmccompose.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lettuce.hmccompose.data.ActionViewData
import lettuce.hmccompose.data.complextext.ComplexTextValueViewData
import lettuce.hmccompose.data.complextext.ComplexTextViewData
import lettuce.hmccompose.data.genericbutton.ButtonViewData
import lettuce.hmccompose.data.sectioncontainer.SectionContainerViewData
import lettuce.hmccompose.ui.adapters.ComponentManager

@Composable
fun SectionContainer(
    viewData: SectionContainerViewData,
    onClick: ((actionVD: ActionViewData?) -> Unit)?
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        for (componentVD in viewData.items) {
            ComponentManager.ComponentByViewData(componentVD, onClick)
        }
    }
}

class SectionContainerExtras {
    companion object {
        @Preview(showBackground = true)
        @Composable
        fun Preview() {
            val previewViewData = SectionContainerViewData(
                stylingId = "transparentSection",
                items = listOf(
                    ComplexTextViewData(
                        isFormSection = false,
                        values = listOf(
                            ComplexTextValueViewData(
                                value = "Not sure of your car's registration?",
                                stylingId = "cellHeaderText"
                            )
                        )
                    ),
                    ButtonViewData(
                        title = "Search by make or model",
                        stylingId = "primaryOutlineRoundedButton",
                    )
                )
            )
            SectionContainer(previewViewData, null)
        }
    }
}