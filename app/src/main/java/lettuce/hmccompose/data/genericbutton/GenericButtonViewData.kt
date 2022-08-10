package lettuce.hmccompose.data.genericbutton

import lettuce.hmccompose.data.ActionViewData
import lettuce.hmccompose.data.ComponentViewData

data class GenericButtonViewData(
    val title: String,
    val separatorType: String = "none",
    val paddingType: String = "small",
    val stylingId: String,
    val actionViewData: ActionViewData? = null
): ComponentViewData()
