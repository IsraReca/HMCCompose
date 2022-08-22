package lettuce.hmccompose.ui.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lettuce.hmccompose.data.ActionViewData
import lettuce.hmccompose.data.genericbutton.ButtonViewData
import lettuce.hmccompose.ui.component.generics.ComposableComponent
import lettuce.hmccompose.ui.theme.Blue
import lettuce.hmccompose.ui.theme.GenericButton_Style

class OutlinedRoundedButtonComponent : ComposableComponent<ButtonViewData> {
    @Composable
    override fun Component(
        viewData: ButtonViewData,
        onClick: ((actionVD: ActionViewData?) -> Unit)?
    ) {
        OutlinedButton(
            onClick = {
                onClick?.invoke(viewData.actionViewData)
            },
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minWidth = 1.dp, minHeight = 1.dp),
            border = BorderStroke(1.dp, Blue),
            shape = CircleShape,
            colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Transparent)
        ) {
            Text(
                text = viewData.title,
                color = Blue,
                style = GenericButton_Style
            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    override fun Preview() {
        val previewViewData = ButtonViewData(
            title = "Search by make or model",
            stylingId = "primaryOutlineRoundedButton",
            actionViewData = ActionViewData("test")
        )
        Component(previewViewData, null)
    }
}