package lettuce.hmccompose.data.groupedoptions

import lettuce.hmccompose.data.ComponentViewData

data class GroupedOptionsViewData(
    val groups: List<GroupedOptionsGroupViewData>
) : ComponentViewData()
