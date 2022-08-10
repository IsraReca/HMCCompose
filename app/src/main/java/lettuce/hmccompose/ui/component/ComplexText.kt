package lettuce.hmccompose.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import lettuce.hmccompose.data.complextext.ComplexTextValueViewData
import lettuce.hmccompose.data.complextext.ComplexTextViewData
import lettuce.hmccompose.ui.adapters.StyleAdapter
import lettuce.hmccompose.ui.theme.PreviewModifier

@Composable
fun ComplexText(viewData: ComplexTextViewData) {
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
fun ComplexTextPreview() {
    Box(
        modifier = PreviewModifier
    ) {
        ComplexText(ComplexTextViewDataPreview)
    }
}

val ComplexTextViewDataPreview = ComplexTextViewData(
    isFormSection = false,
    values = listOf(
        ComplexTextValueViewData(
            value = "To start selling, let's help you price your car!",
            stylingId = "title1Text"
        )
    )
)