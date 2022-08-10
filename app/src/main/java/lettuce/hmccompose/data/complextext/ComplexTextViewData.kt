package lettuce.hmccompose.data.complextext

import lettuce.hmccompose.data.ComponentViewData

data class ComplexTextViewData(
    val isFormSection: Boolean,
    val values: List<ComplexTextValueViewData>
) : ComponentViewData()
