package lettuce.hmccompose.data.sectioncontainer

import lettuce.hmccompose.data.ComponentViewData

data class SectionContainerViewData(
    val stylingId: String,
    val items: List<ComponentViewData>
) : ComponentViewData()
