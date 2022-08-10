package lettuce.hmccompose.data.formcontroltextbox

data class FormControlTextBoxValidatorViewData(
    val key: String,
    val validationMessage: String,
    val config: String,
    val regex: String? = null
) {
    companion object {
        const val KEY_REQUIRED = "required"
        const val KEY_REGEX = "regex"
    }
}