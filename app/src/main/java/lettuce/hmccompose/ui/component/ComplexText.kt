package lettuce.hmccompose.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import lettuce.hmccompose.data.complextext.ComplexTextValueViewData
import lettuce.hmccompose.data.complextext.ComplexTextViewData
import lettuce.hmccompose.ui.adapters.StyleAdapter
import lettuce.hmccompose.ui.component.generics.ComposableComponent

class ComplexText : ComposableComponent<ComplexTextViewData> {
    @Composable
    override fun Component(
        viewData: ComplexTextViewData
    ) {
        Column {
            for (value in viewData.values) {
                Text(
                    text = value.value,
                    style = StyleAdapter.getStyleById(value.stylingId),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    override fun ComponentPreview() {
        super.ComponentPreview()
    }

    override var previewViewData: ComplexTextViewData? = ComplexTextViewData(
            isFormSection = false,
            values = listOf(
                ComplexTextValueViewData(
                    value = "To start selling, let's help you price your car!",
                    stylingId = "title1Text"
                )
            )
        )
}