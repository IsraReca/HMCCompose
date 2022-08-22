package lettuce.hmccompose.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lettuce.hmccompose.data.ActionViewData
import lettuce.hmccompose.data.formcontroltextbox.FormControlTextBoxValidatorViewData
import lettuce.hmccompose.data.formcontroltextbox.FormControlTextBoxViewData
import lettuce.hmccompose.ui.component.generics.ComposableComponent
import lettuce.hmccompose.ui.theme.*

class FormControlTextBox : ComposableComponent<FormControlTextBoxViewData> {
    @Composable
    override fun Component(
        viewData: FormControlTextBoxViewData,
        onClick: ((actionVD: ActionViewData?) -> Unit)?
    ) {
        var text: String by rememberSaveable { mutableStateOf("") }
        var errorMessage: String? by rememberSaveable { mutableStateOf(null) }

        fun validateText(text: String): String? {
            for (validator in viewData.validators) {
                when (validator.key) {
                    FormControlTextBoxValidatorViewData.KEY_REQUIRED -> {
                        if (text.isEmpty()) {
                            return validator.validationMessage
                        }
                    }
                    FormControlTextBoxValidatorViewData.KEY_REGEX -> {
                        validator.regex?.let {
                            if (!text.matches(Regex(it))) {
                                return validator.validationMessage
                            }
                        }
                    }
                    else -> {
                        return null
                    }
                }
            }
            return null
        }

        StateLessComponent(
            viewData, text, errorMessage
        ) {
            text = it
            errorMessage = validateText(it)
        }
    }

    @Composable
    fun StateLessComponent(
        viewData: FormControlTextBoxViewData,
        textState: String,
        errorMessageState: String?,
        onValueChange: (String) -> Unit,
    ) {
        Column {
            viewData.label?.let {
                Text(
                    text = it,
                    color = if (errorMessageState == null) TextColor else RedError,
                    style = FormControlTextBox_Label_Style,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(6.dp))
            }
            TextField(
                value = textState,
                onValueChange = onValueChange,
                shape = RoundedCornerShape(8.dp),
                singleLine = true,
                placeholder = {
                    Text(
                        text = viewData.placeholder,
                        style = FormControlTextBox_Placeholder_Style,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = White,
                    cursorColor = Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            errorMessageState?.let {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = it,
                    color = RedError,
                    style = FormControlTextBox_Placeholder_Style,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    override fun Preview() {
        val previewViewData = FormControlTextBoxViewData(
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
        )
        Component(previewViewData, null)
    }
}