package lettuce.hmccompose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lettuce.hmccompose.data.ActionViewData
import lettuce.hmccompose.data.ComponentViewData
import lettuce.hmccompose.data.complextext.ComplexTextValueViewData
import lettuce.hmccompose.data.complextext.ComplexTextViewData
import lettuce.hmccompose.data.formcontroltextbox.FormControlTextBoxValidatorViewData
import lettuce.hmccompose.data.formcontroltextbox.FormControlTextBoxViewData
import lettuce.hmccompose.data.genericbutton.GenericButtonViewData
import lettuce.hmccompose.data.sectioncontainer.SectionContainerViewData
import lettuce.hmccompose.ui.adapters.ComponentAdapter
import lettuce.hmccompose.ui.theme.Gray
import lettuce.hmccompose.ui.theme.HMCComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DefaultPreview()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val context = LocalContext.current
    HMCComposeTheme {
        Box(modifier = Modifier.background(color = Gray)) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                for (component in previewData) {
                    ComponentAdapter.getComponentByViewData(component) { actionVD ->
                        actionVD?.let {
                            Toast.makeText(context, it.actionType, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}

val previewData = listOf<ComponentViewData>(
    ComplexTextViewData(
        isFormSection = false,
        values = listOf(
            ComplexTextValueViewData(
                value = "To start selling, let's help you price your car!",
                stylingId = "title1Text"
            )
        )
    ),
    FormControlTextBoxViewData(
        label = "What's your car's registration?",
        placeholder = "e.g. AAA123",
        validators = listOf(
            FormControlTextBoxValidatorViewData(
                key = "required",
                validationMessage = "Registration is required.",
                config = ".+"
            ),
            FormControlTextBoxValidatorViewData(
                key = "regex",
                validationMessage = "Please enter a valid registration",
                config = "^\$|^[a-zA-Z0-9- ]{1,9}\$",
                regex = "^\$|^[a-zA-Z0-9- ]{1,9}\$"
            )
        )
    ),
    SectionContainerViewData(
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
            GenericButtonViewData(
                title = "Test A Button",
                stylingId = "primaryOutlineRoundedButton",
                actionViewData = ActionViewData("testA")
            ),
            GenericButtonViewData(
                title = "Test B Button",
                stylingId = "primaryOutlineRoundedButton",
                actionViewData = ActionViewData("testB")
            )

        )
    )
)