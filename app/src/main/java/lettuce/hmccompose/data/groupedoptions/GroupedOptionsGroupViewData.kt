package lettuce.hmccompose.data.groupedoptions

data class GroupedOptionsGroupViewData(
    val group: String,
    val options: List<GroupedOptionsGroupDisplayValueViewData>
)