package lettuce.hmccompose.ui.component.text

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import lettuce.hmccompose.data.ActionViewData
import lettuce.hmccompose.data.complextext.ComplexTextValueViewData
import lettuce.hmccompose.data.complextext.ComplexTextViewData

@Composable
fun ComplexText(
    viewData: ComplexTextViewData,
    onClick: ((actionVD: ActionViewData?) -> Unit)?
) {
    Column {
        for (value in viewData.values) {
            Text(
                text = value.value ?: "",
                style = TextStyleManager.getStyleById(
                    value.stylingId,
                    value.type
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    val previewViewData = ComplexTextViewData(
        isFormSection = false,
        values = listOf(
            ComplexTextValueViewData(
                value = "To start selling, let's help you price your car!",
                stylingId = "title1Text"
            )
        )
    )
    ComplexText(previewViewData, null)
}