package com.example.spikesolutiondynamicbottomnav

import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.NavGraph
import androidx.navigation.NavType
import androidx.navigation.createGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.fragment
import com.example.spikesolutiondynamicbottomnav.ui.BaseFragment.Companion.ARG_REFERENCE_ID
import com.example.spikesolutiondynamicbottomnav.ui.BaseFragment.Companion.ARG_REFERENCE_TYPE
import com.example.spikesolutiondynamicbottomnav.ui.dashboard.DashboardFragment
import com.example.spikesolutiondynamicbottomnav.ui.home.HomeFragment
import com.example.spikesolutiondynamicbottomnav.ui.home.HomeFragmentThree
import com.example.spikesolutiondynamicbottomnav.ui.home.HomeFragmentTwo
import com.example.spikesolutiondynamicbottomnav.ui.navigation.listMenu
import com.example.spikesolutiondynamicbottomnav.ui.navigation.nav_graph
import com.example.spikesolutiondynamicbottomnav.ui.notifications.NotificationFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val bottomNavGraph = mutableListOf<NavGraph>()

    //    private val multipleBackStack = mutableMapOf<Int, MutableList<Pair<Int, Bundle?>>>()
    private val multipleBackStack = mutableSetOf<Triple<Int, Int, Bundle?>>()

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigationView = findViewById(R.id.nav_view)
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        bottomNavigationView.menu.clear()
        listMenu.forEachIndexed { index, menu ->
            bottomNavigationView.menu.add(
                Menu.CATEGORY_SECONDARY,
                menu.first.toDestinationId(),
                index,
                menu.first
            )
        }

        createNavGraph(navHostFragment)

        navHostFragment.navController.graph = bottomNavGraph.first()

        navHostFragment.navController.addOnDestinationChangedListener { _, destination, arguments ->
            Log.d("DEBUG", "destination:{${destination.label}}")
//            val addInBackStack =
//                bottomNavGraph.firstOrNull { it.startDestination == destination.id }?.let {
//                    false
//                } ?: run {
//                    multipleBackStack[navHostFragment.navController.graph.id]?.firstOrNull { value ->
//                        value.first == destination.id
//                    }?.let {
//                        false
//                    } ?: true
//                }
            val addInBackStack =
                multipleBackStack.firstOrNull { triple ->
                    triple.first == navHostFragment.navController.graph.id &&
                            triple.second == destination.id
                }?.let {
                    false
                } ?: true

            if (addInBackStack) {
                val argsId = arguments?.get(ARG_REFERENCE_ID).toString()
                val argsType = arguments?.get(ARG_REFERENCE_TYPE).toString()
                Log.d(
                    "DEBUG",
                    "addInBackStack destination:{${destination.label}} argsId:$argsId argsType:$argsType"
                )
//                multipleBackStack[navHostFragment.navController.graph.id]?.add(
//                    Pair(
//                        destination.id, createBundle(argsId, argsType)
//                    )
//                ) ?: run {
//                    multipleBackStack[navHostFragment.navController.graph.id] = mutableListOf(
//                        Pair(
//                            destination.id, createBundle(argsId, argsType)
//                        )
//                    )
//                }
                multipleBackStack.add(
                    Triple(
                        navHostFragment.navController.graph.id,
                        destination.id,
                        createBundle(argsId, argsType)
                    )
                )
            }
        }

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            navHostFragment.navController.graph = bottomNavGraph.first { it.id == item.itemId }
//            multipleBackStack[navHostFragment.navController.graph.id]?.last()?.let { pair ->
//                val (destination, bundle) = pair
//                navHostFragment.navController.navigate(
//                    destination, bundle
//                )
////                multipleBackStack.remove(navHostFragment.navController.graph.id)
//            }
            multipleBackStack.lastOrNull { it.first == navHostFragment.navController.graph.id && it.second !=
                    navHostFragment.navController.graph.startDestination }
                ?.let { triple ->
                    val (graphId, destination, bundle) = triple
                    navHostFragment.navController.navigate(
                        destination, bundle
                    )
                }
            true
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Log.d(
                    "DEBUG",
                    "onBackPressed() call"
                )
            }
        })

    }

    private fun createNavGraph(navHostFragment: NavHostFragment) {
        for (menu in listMenu) {
            bottomNavGraph.add(
                navHostFragment.navController.createGraph(
                    menu.first.toDestinationId(),
                    menu.second.toStartDestinationId()
                ) {
                    fragment<HomeFragment>(nav_graph.dest.home) {
                        label = "HomeFragment"
                        argument(ARG_REFERENCE_ID) {
                            defaultValue = "homeId"
                            type = NavType.StringType
                        }
                        argument(ARG_REFERENCE_TYPE) {
                            defaultValue = "homepage"
                            type = NavType.StringType
                        }
                    }
                    fragment<DashboardFragment>(nav_graph.dest.dashboard) {
                        label = "DashboardFragment"
                        argument(ARG_REFERENCE_ID) {
                            defaultValue = "dashId"
                            type = NavType.StringType
                        }
                        argument(ARG_REFERENCE_TYPE) {
                            defaultValue = "dashboard"
                            type = NavType.StringType
                        }
                    }
                    fragment<NotificationFragment>(nav_graph.dest.notification) {
                        label = "NotificationFragment"
                        argument(ARG_REFERENCE_ID) {
                            defaultValue = "notificationsId"
                            type = NavType.StringType
                        }
                        argument(ARG_REFERENCE_TYPE) {
                            defaultValue = "notification"
                            type = NavType.StringType
                        }
                    }
                    fragment<HomeFragmentTwo>(nav_graph.dest.homeTwo) {
                        label = "HomeFragmentTwo"
                        argument(ARG_REFERENCE_ID) {
                            defaultValue = "homeId_2"
                            type = NavType.StringType
                        }
                        argument(ARG_REFERENCE_TYPE) {
                            defaultValue = "homepage"
                            type = NavType.StringType
                        }
                    }
                    fragment<HomeFragmentThree>(nav_graph.dest.homeThree) {
                        label = "HomeFragmentThree"
                        argument(ARG_REFERENCE_ID) {
                            defaultValue = "homeId_3"
                            type = NavType.StringType
                        }
                        argument(ARG_REFERENCE_TYPE) {
                            defaultValue = "homepage"
                            type = NavType.StringType
                        }
                    }
                }
            )
        }
    }

    private fun String.toStartDestinationId(): Int {
        return when (this) {
            "homepage" -> nav_graph.dest.home
            "dashboard" -> nav_graph.dest.dashboard
            "notification" -> nav_graph.dest.notification
            else -> nav_graph.dest.home
        }
    }

    private fun String.toDestinationId(): Int {
        return this.take(3).toCharArray().map { it.toInt().toString() }
            .reduce { finalString, element -> finalString + element }.toInt()
    }

    private fun createBundle(argsId: String, argsType: String): Bundle {
        return bundleOf(
            ARG_REFERENCE_ID to argsId,
            ARG_REFERENCE_TYPE to argsType
        )
    }
}