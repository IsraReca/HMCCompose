package lettuce.hmccompose.ui.component.fastscroll

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import lettuce.hmccompose.R
import lettuce.hmccompose.data.groupedoptions.*
import lettuce.hmccompose.ui.theme.*

@Composable
fun GroupedOptions(
    viewData: GroupedOptionsViewData
) {
    var selectedGroupState: String? by rememberSaveable { mutableStateOf(null) }

    GroupedOptionsStateless(
        viewData = viewData,
        selectedGroupState = selectedGroupState,
        onSelectedGroupChange = {
            selectedGroupState = it
        }
    )
}

@Composable
fun GroupedOptionsStateless(
    viewData: GroupedOptionsViewData,
    selectedGroupState: String?,
    onSelectedGroupChange: (String?) -> Unit
) {
    val dataList = GroupedOptionsExtras.createDataList(viewData)
    val filterList = GroupedOptionsExtras.createFilterList(viewData)
    val listState = rememberLazyListState()
    var filterViewSizeState: IntSize? = null

    val coroutineScope = rememberCoroutineScope()
    fun scrollToItem(position: Int) {
        coroutineScope.launch {
            listState.scrollToItem(index = position)
        }
    }

    fun getRowModifier(color: Color): Modifier {
        return Modifier
            .fillMaxWidth()
            .background(color = color)
            .height(45.dp)
            .padding(
                start = 12.dp,
                end = 12.dp
            )
    }

    Box() {
        LazyColumn(
            state = listState
        ) {
            items(dataList) { data ->
                when (data) {
                    is GroupedOptionsListGroup -> {
                        Row(
                            modifier = getRowModifier(DarkGray),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = data.group,
                                style = GroupedOptions_Group_Style,
                                modifier = Modifier.weight(1.0f)
                            )
                        }
                    }
                    is GroupedOptionsListOptions -> {
                        Row(
                            modifier = getRowModifier(ClearGray),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = data.display,
                                style = GroupedOptions_Value_Style,
                                modifier = Modifier.weight(1.0f)
                            )

                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_arrow_forward_ios_24),
                                contentDescription = null,
                                tint = DarkGray,
                            )
                        }
                    }
                    else -> {
                    }
                }
            }
        }
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .background(DarkGray)
                .align(Alignment.CenterEnd)
                .onGloballyPositioned { coordinates ->
                    filterViewSizeState = coordinates.size
                }
                .pointerInput(Unit) {
                    forEachGesture {
                        awaitPointerEventScope {
                            val firstTouch = awaitFirstDown()
                            //Action Down
                            filterViewSizeState?.let { abcSize ->
                                GroupedOptionsExtras.getGroupIndexFromViewPosition(
                                    size = abcSize,
                                    positionY = firstTouch.position.y,
                                    numberOfGroups = filterList.size
                                )?.let { position ->
                                    filterList[position].let { group ->
                                        onSelectedGroupChange(group.group)
                                        scrollToItem(group.position)
                                    }
                                }
                            }
                            //Action Down End
                            do {
                                val event: PointerEvent = awaitPointerEvent()
                                val eventPointer = event.changes[0].position
                                //Action Move
                                if (GroupedOptionsExtras.checkIfInBounds(
                                        filterViewSizeState,
                                        eventPointer.x,
                                        eventPointer.y
                                    )
                                ) {
                                    GroupedOptionsExtras.getGroupIndexFromViewPosition(
                                        size = filterViewSizeState,
                                        positionY = eventPointer.y,
                                        numberOfGroups = filterList.size
                                    )?.let { position ->
                                        filterList[position].let { group ->
                                            onSelectedGroupChange(group.group)
                                            scrollToItem(group.position)
                                        }
                                    }
                                } else {
                                    onSelectedGroupChange(null)
                                }
                                //Action Move End
                                event.changes.forEach { pointerInputChange: PointerInputChange ->
                                    pointerInputChange.consumePositionChange()
                                }
                            } while (event.changes.any { it.pressed })

                            // Action Up
                            onSelectedGroupChange(null)
                            //Action Up End
                        }
                    }
                }
        ) {
            for (group in filterList) {
                Text(
                    text = group.group,
                    style = GroupedOptions_SideGroup_Style,
                    color = HighlightBlue,
                    modifier = Modifier
                        .padding(3.dp)

                )
            }
        }
        selectedGroupState?.let {
            Text(
                text = it,
                style = GroupedOptions_SelectedGroup_Style,
                color = HighlightBlue,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    GroupedOptions(GroupedOptionsExtras.PREVIEW_VIEWDATA)
}

class GroupedOptionsExtras {
    companion object {
        fun checkIfInBounds(
            size: IntSize?,
            positionX: Float,
            positionY: Float
        ): Boolean {
            size?.let {
                return positionX > 0 && positionX <= size.width && positionY > 0 && positionY <= size.height
            }
            return false
        }

        fun getGroupIndexFromViewPosition(
            size: IntSize?,
            positionY: Float,
            numberOfGroups: Int
        ): Int? {
            size?.let {
                val segmentHeight: Int = size.height / numberOfGroups
                val position = positionY.toInt() / segmentHeight
                if (position in 0..numberOfGroups) {
                    return position
                }
            }
            return null
        }

        fun createFilterList(viewData: GroupedOptionsViewData): List<GroupedOptionsFilterList> {
            var pos = 0
            val filterList = mutableListOf<GroupedOptionsFilterList>()
            for (group in viewData.groups) {
                pos += group.options.size + 1
                filterList.add(
                    GroupedOptionsFilterList(
                        group = group.group,
                        position = pos
                    )
                )
            }
            return filterList
        }

        fun createDataList(viewData: GroupedOptionsViewData): List<GroupedOptionsList> {
            val dataList = mutableListOf<GroupedOptionsList>()
            for (group in viewData.groups) {
                dataList.add(
                    GroupedOptionsListGroup(
                        group = group.group
                    )
                )
                for (option in group.options) {
                    dataList.add(
                        GroupedOptionsListOptions(
                            display = option.display,
                            value = option.value
                        )
                    )
                }
            }
            return dataList
        }

        val PREVIEW_VIEWDATA = GroupedOptionsViewData(
            groups = listOf(
                GroupedOptionsGroupViewData(
                    group = "A", options = listOf(
                        GroupedOptionsGroupDisplayValueViewData(
                            display = "Abarth", value = "make=Abarth"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "AC", value = "make=AC"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "ACE", value = "make=ACE"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Aero", value = "make=Aero"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Alfa Romeo", value = "make=Alfa+Romeo"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Allard", value = "make=Allard"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Alpina", value = "make=Alpina"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Alpine", value = "make=Alpine"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Alpine-Renault", value = "make=Alpine-Renault"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Alvis", value = "make=Alvis"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "AM General", value = "make=AM+General"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "AMC", value = "make=AMC"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Amilcar", value = "make=Amilcar"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Amphicar", value = "make=Amphicar"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Ariel", value = "make=Ariel"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Armstrong Siddeley", value = "make=Armstrong+Siddeley"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Asia Motors", value = "make=Asia+Motors"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Aston Martin", value = "make=Aston+Martin"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Auburn", value = "make=Auburn"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Audi", value = "make=Audi"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Austin", value = "make=Austin"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Austin Healey", value = "make=Austin+Healey"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Australian Classic Car",
                            value = "make=Australian+Classic+Car"
                        )
                    )
                ), GroupedOptionsGroupViewData(
                    group = "B", options = listOf(
                        GroupedOptionsGroupDisplayValueViewData(
                            display = "Bean", value = "make=Bean"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Bedford", value = "make=Bedford"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Bentley", value = "make=Bentley"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Berkeley", value = "make=Berkeley"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Bertone", value = "make=Bertone"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Blade", value = "make=Blade"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "BMC", value = "make=BMC"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "BMW", value = "make=BMW"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Bolwell", value = "make=Bolwell"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "BOMBARDIER", value = "make=BOMBARDIER"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Bond", value = "make=Bond"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Borgward", value = "make=Borgward"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Bristol", value = "make=Bristol"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Bufori", value = "make=Bufori"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Bugatti", value = "make=Bugatti"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Buick", value = "make=Buick"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Bullet", value = "make=Bullet"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "BYD", value = "make=BYD"
                        )
                    )
                ), GroupedOptionsGroupViewData(
                    group = "C", options = listOf(
                        GroupedOptionsGroupDisplayValueViewData(
                            display = "Cadillac", value = "make=Cadillac"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Campagna Motors", value = "make=Campagna+Motors"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Caterham", value = "make=Caterham"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Chandler", value = "make=Chandler"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Checker Motors Corporation",
                            value = "make=Checker+Motors+Corporation"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Chery", value = "make=Chery"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Chevrolet", value = "make=Chevrolet"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Chrysler", value = "make=Chrysler"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Citroen", value = "make=Citroen"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Clenet", value = "make=Clenet"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Commer", value = "make=Commer"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Cord", value = "make=Cord"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Crosley", value = "make=Crosley"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Crossley", value = "make=Crossley"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "CSV", value = "make=CSV"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Custom", value = "make=Custom"
                        )
                    )
                ), GroupedOptionsGroupViewData(
                    group = "D", options = listOf(
                        GroupedOptionsGroupDisplayValueViewData(
                            display = "Dacia", value = "make=Dacia"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Daewoo", value = "make=Daewoo"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Daihatsu", value = "make=Daihatsu"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Daimler", value = "make=Daimler"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Datsun", value = "make=Datsun"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "De Tomaso", value = "make=De+Tomaso"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Delage", value = "make=Delage"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Delahaye", value = "make=Delahaye"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "DeLorean", value = "make=DeLorean"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "DeSoto", value = "make=DeSoto"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Devaux", value = "make=Devaux"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "DKW", value = "make=DKW"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Dodge", value = "make=Dodge"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Dover", value = "make=Dover"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Durant", value = "make=Durant"
                        )
                    )
                ), GroupedOptionsGroupViewData(
                    group = "E", options = listOf(
                        GroupedOptionsGroupDisplayValueViewData(
                            display = "Edsel", value = "make=Edsel"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Elfin", value = "make=Elfin"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Essex", value = "make=Essex"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Eunos", value = "make=Eunos"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Evante", value = "make=Evante"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Excalibur", value = "make=Excalibur"
                        )
                    )
                ), GroupedOptionsGroupViewData(
                    group = "F", options = listOf(
                        GroupedOptionsGroupDisplayValueViewData(
                            display = "Facel", value = "make=Facel"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Ferrari", value = "make=Ferrari"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Fiat", value = "make=Fiat"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Ford", value = "make=Ford"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Ford Performance Vehicles",
                            value = "make=Ford+Performance+Vehicles"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Foton", value = "make=Foton"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "FSM", value = "make=FSM"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Fulda", value = "make=Fulda"
                        )
                    )
                ), GroupedOptionsGroupViewData(
                    group = "G", options = listOf(
                        GroupedOptionsGroupDisplayValueViewData(
                            display = "Galloway", value = "make=Galloway"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Gaz", value = "make=Gaz"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Geely", value = "make=Geely"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Genesis", value = "make=Genesis"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Giocattolo", value = "make=Giocattolo"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "GMC", value = "make=GMC"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Goggomobil", value = "make=Goggomobil"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Goliath", value = "make=Goliath"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Graham", value = "make=Graham"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Graham-Paige", value = "make=Graham-Paige"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Great Wall", value = "make=Great+Wall"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "GWM", value = "make=GWM"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Gwynne", value = "make=Gwynne"
                        )
                    )
                ), GroupedOptionsGroupViewData(
                    group = "H", options = listOf(
                        GroupedOptionsGroupDisplayValueViewData(
                            display = "Haval", value = "make=Haval"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "HDT", value = "make=HDT"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Healey", value = "make=Healey"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Heron", value = "make=Heron"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Hillman", value = "make=Hillman"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Hino", value = "make=Hino"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Holden", value = "make=Holden"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Holden Special Vehicles",
                            value = "make=Holden+Special+Vehicles"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Honda", value = "make=Honda"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Hudson", value = "make=Hudson"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Humber", value = "make=Humber"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Hummer", value = "make=Hummer"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Hupmobile", value = "make=Hupmobile"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Hyundai", value = "make=Hyundai"
                        )
                    )
                ), GroupedOptionsGroupViewData(
                    group = "I", options = listOf(
                        GroupedOptionsGroupDisplayValueViewData(
                            display = "INFINITI", value = "make=INFINITI"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "International", value = "make=International"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Invicta", value = "make=Invicta"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "ISO", value = "make=ISO"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Isuzu", value = "make=Isuzu"
                        )
                    )
                ), GroupedOptionsGroupViewData(
                    group = "J", options = listOf(
                        GroupedOptionsGroupDisplayValueViewData(
                            display = "Jaguar", value = "make=Jaguar"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "JBA", value = "make=JBA"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Jeep", value = "make=Jeep"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Jensen", value = "make=Jensen"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Jewett", value = "make=Jewett"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "JMC", value = "make=JMC"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Jowett", value = "make=Jowett"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "JWF", value = "make=JWF"
                        )
                    )
                ), GroupedOptionsGroupViewData(
                    group = "K", options = listOf(
                        GroupedOptionsGroupDisplayValueViewData(
                            display = "Kia", value = "make=Kia"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Koenigsegg", value = "make=Koenigsegg"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "KTM", value = "make=KTM"
                        )
                    )
                ), GroupedOptionsGroupViewData(
                    group = "L", options = listOf(
                        GroupedOptionsGroupDisplayValueViewData(
                            display = "Lada", value = "make=Lada"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Lagonda", value = "make=Lagonda"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Lamborghini", value = "make=Lamborghini"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Lancia", value = "make=Lancia"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Land Rover", value = "make=Land+Rover"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "LDV", value = "make=LDV"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Lexus", value = "make=Lexus"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Leyland", value = "make=Leyland"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Lightburn", value = "make=Lightburn"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Lincoln", value = "make=Lincoln"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "London Taxi Company", value = "make=London+Taxi+Company"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Lotus", value = "make=Lotus"
                        )
                    )
                ), GroupedOptionsGroupViewData(
                    group = "M", options = listOf(
                        GroupedOptionsGroupDisplayValueViewData(
                            display = "Mahindra", value = "make=Mahindra"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Manta", value = "make=Manta"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Marathon", value = "make=Marathon"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Marcos", value = "make=Marcos"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Maserati", value = "make=Maserati"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "MATRA", value = "make=MATRA"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Maxwell", value = "make=Maxwell"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Maybach", value = "make=Maybach"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Mazda", value = "make=Mazda"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "McLaren", value = "make=McLaren"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Mercedes-Benz", value = "make=Mercedes-Benz"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Mercury", value = "make=Mercury"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Messerschmitt", value = "make=Messerschmitt"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "MG", value = "make=MG"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Mini", value = "make=Mini"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Mitsubishi", value = "make=Mitsubishi"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Morgan", value = "make=Morgan"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Morris", value = "make=Morris"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Moskvich", value = "make=Moskvich"
                        )
                    )
                ), GroupedOptionsGroupViewData(
                    group = "N", options = listOf(
                        GroupedOptionsGroupDisplayValueViewData(
                            display = "Napier", value = "make=Napier"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Nash", value = "make=Nash"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Nissan", value = "make=Nissan"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Noble", value = "make=Noble"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "NSU", value = "make=NSU"
                        )
                    )
                ), GroupedOptionsGroupViewData(
                    group = "O", options = listOf(
                        GroupedOptionsGroupDisplayValueViewData(
                            display = "Oakland", value = "make=Oakland"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Oldsmobile", value = "make=Oldsmobile"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Oltcit", value = "make=Oltcit"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Opel", value = "make=Opel"
                        )
                    )
                ), GroupedOptionsGroupViewData(
                    group = "P", options = listOf(
                        GroupedOptionsGroupDisplayValueViewData(
                            display = "Packard", value = "make=Packard"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Pagani", value = "make=Pagani"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Panther", value = "make=Panther"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Perentti", value = "make=Perentti"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Peugeot", value = "make=Peugeot"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Pierce-Arrow", value = "make=Pierce-Arrow"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Plymouth", value = "make=Plymouth"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Polestar", value = "make=Polestar"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Polski", value = "make=Polski"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Pontiac", value = "make=Pontiac"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Porsche", value = "make=Porsche"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Prince", value = "make=Prince"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Proton", value = "make=Proton"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Puma", value = "make=Puma"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Purvis", value = "make=Purvis"
                        )
                    )
                ), GroupedOptionsGroupViewData(
                    group = "R", options = listOf(
                        GroupedOptionsGroupDisplayValueViewData(
                            display = "Ram", value = "make=Ram"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Rambler", value = "make=Rambler"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Reliant", value = "make=Reliant"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Renault", value = "make=Renault"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "REO", value = "make=REO"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Riley", value = "make=Riley"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Robnell", value = "make=Robnell"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Rockne", value = "make=Rockne"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Rolls-Royce", value = "make=Rolls-Royce"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Rover", value = "make=Rover"
                        )
                    )
                ), GroupedOptionsGroupViewData(
                    group = "S", options = listOf(
                        GroupedOptionsGroupDisplayValueViewData(
                            display = "Saab", value = "make=Saab"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Seat", value = "make=Seat"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Simca", value = "make=Simca"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Singer", value = "make=Singer"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Skoda", value = "make=Skoda"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Smart", value = "make=Smart"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "SS", value = "make=SS"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Ssangyong", value = "make=Ssangyong"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Standard", value = "make=Standard"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "STEWART", value = "make=STEWART"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Steyr-Puch", value = "make=Steyr-Puch"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Studebaker", value = "make=Studebaker"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Stutz", value = "make=Stutz"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Subaru", value = "make=Subaru"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Sunbeam", value = "make=Sunbeam"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Suzuki", value = "make=Suzuki"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Swallow", value = "make=Swallow"
                        )
                    )
                ), GroupedOptionsGroupViewData(
                    group = "T", options = listOf(
                        GroupedOptionsGroupDisplayValueViewData(
                            display = "Talbot", value = "make=Talbot"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Tata", value = "make=Tata"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Tatra", value = "make=Tatra"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "TD 2000", value = "make=TD+2000"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Tesla", value = "make=Tesla"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Toyota", value = "make=Toyota"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "TRD", value = "make=TRD"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Triumph", value = "make=Triumph"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Turner", value = "make=Turner"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "TVR", value = "make=TVR"
                        )
                    )
                ), GroupedOptionsGroupViewData(
                    group = "U", options = listOf(
                        GroupedOptionsGroupDisplayValueViewData(
                            display = "Ultima", value = "make=Ultima"
                        )
                    )
                ), GroupedOptionsGroupViewData(
                    group = "V", options = listOf(
                        GroupedOptionsGroupDisplayValueViewData(
                            display = "Vanden Plas", value = "make=Vanden+Plas"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Vauxhall", value = "make=Vauxhall"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Volkswagen", value = "make=Volkswagen"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Volvo", value = "make=Volvo"
                        )
                    )
                ), GroupedOptionsGroupViewData(
                    group = "W", options = listOf(
                        GroupedOptionsGroupDisplayValueViewData(
                            display = "Westfield", value = "make=Westfield"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Wiesmann", value = "make=Wiesmann"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Willys", value = "make=Willys"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Wolseley", value = "make=Wolseley"
                        )
                    )
                ), GroupedOptionsGroupViewData(
                    group = "Y", options = listOf(
                        GroupedOptionsGroupDisplayValueViewData(
                            display = "Yugo", value = "make=Yugo"
                        )
                    )
                ), GroupedOptionsGroupViewData(
                    group = "Z", options = listOf(
                        GroupedOptionsGroupDisplayValueViewData(
                            display = "Zastava", value = "make=Zastava"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "Zimmer", value = "make=Zimmer"
                        ), GroupedOptionsGroupDisplayValueViewData(
                            display = "ZX Auto", value = "make=ZX+Auto"
                        )
                    )
                )
            )
        )
    }
}
