package com.example.spikesolutiondynamicbottomnav.ui.navigation

object nav_graph {
    object dest {
        const val home = 2
        const val dashboard = 3
        const val notification = 4
        const val homeTwo = 5
        const val homeThree = 6
    }
}

val listMenu = listOf(
    Pair(
        "MediasetPlay",
        "homepage"
    ),
    Pair(
        "Dirette",
        "dashboard"
    ),
    Pair(
        "Catalogo",
        "notification"
    ),
    Pair(
        "Infinity",
        "homepage"
    ),
    Pair(
        "User",
        "dashboard"
    ),
)