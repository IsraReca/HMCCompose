package lettuce.hmccompose.data.genericbutton

import lettuce.hmccompose.data.ActionViewData
import lettuce.hmccompose.data.ComponentViewData

data class ButtonViewData(
    val title: String,
    val separatorType: String = "none",
    val paddingType: String = "small",
    val stylingId: String?,
    val actionViewData: ActionViewData? = null
): ComponentViewData()

enum class StylingButtonID(val id: String) {
    PRIMARY_OUTLINED_ROUNDED("primaryOutlineRoundedButton");

    companion object {
        fun from(id: String?): StylingButtonID? =
            when (id) {
                PRIMARY_OUTLINED_ROUNDED.id -> PRIMARY_OUTLINED_ROUNDED
                else -> null
            }
    }
}