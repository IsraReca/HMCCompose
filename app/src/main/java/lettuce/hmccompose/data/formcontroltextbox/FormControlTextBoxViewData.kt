package lettuce.hmccompose.data.formcontroltextbox

import lettuce.hmccompose.data.ComponentViewData

data class FormControlTextBoxViewData(
    val label: String?,
    val placeholder: String,
    val validators: List<FormControlTextBoxValidatorViewData>
) : ComponentViewData()
