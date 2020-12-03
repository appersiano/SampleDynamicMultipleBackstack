package com.example.spikesolutiondynamicbottomnav

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.createGraph
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.fragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.spikesolutiondynamicbottomnav.ui.BaseFragment
import com.example.spikesolutiondynamicbottomnav.ui.dashboard.DashboardFragment
import com.example.spikesolutiondynamicbottomnav.ui.home.HomeFragment
import com.example.spikesolutiondynamicbottomnav.ui.home.HomeFragmentThree
import com.example.spikesolutiondynamicbottomnav.ui.home.HomeFragmentTwo
import com.example.spikesolutiondynamicbottomnav.ui.notifications.NotificationsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    object Nav {
        const val id = 1
        object Dest {
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
                    "livelist"
            ),
            Pair(
                    "Catalogo",
                    ""
            ),
            Pair(
                    "Infinity",
                    "homepage"
            ),
            Pair(
                    "User",
                    "userlist"
            ),
    )

    lateinit var currentNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.nav_view)

        //Creiamo il nostro menu dinamicamente
        bottomNavigationView.menu.clear()

        listMenu.forEachIndexed { index, menu ->
            bottomNavigationView.menu.add(Menu.NONE, menu.first.toDestinationId(), index, menu.first)
        }

        //Creiamo i navControllers con annesso navgraph
        val listNavController = mutableListOf<NavController>()
        for (menu in listMenu) {
            val navController = NavController(applicationContext)
            navController.apply {
                navigatorProvider.addNavigator(FragmentNavigator(applicationContext, supportFragmentManager, R.id.nav_host_fragment))
                graph = createGraph(menu.first.toDestinationId(), menu.second.toStartDestinationId()) {
                    fragment<HomeFragment>(Nav.Dest.home) {
                        argument(BaseFragment.ARG_REFERENCE_ID) {
                            defaultValue = "homeId"
                            type = NavType.StringType
                        }
                        argument(BaseFragment.ARG_REFERENCE_TYPE) {
                            defaultValue = "homepage"
                            type = NavType.StringType
                        }
                    }
                    fragment<DashboardFragment>(Nav.Dest.dashboard) {
                        argument(BaseFragment.ARG_REFERENCE_ID) {
                            defaultValue = "dashId"
                            type = NavType.StringType
                        }
                        argument(BaseFragment.ARG_REFERENCE_TYPE) {
                            defaultValue = "dashboard"
                            type = NavType.StringType
                        }
                    }
                    fragment<NotificationsFragment>(Nav.Dest.notification) {
                        argument(BaseFragment.ARG_REFERENCE_ID) {
                            defaultValue = "notificationsId"
                            type = NavType.StringType
                        }
                        argument(BaseFragment.ARG_REFERENCE_TYPE) {
                            defaultValue = "notification"
                            type = NavType.StringType
                        }
                    }
                    fragment<HomeFragmentTwo>(Nav.Dest.homeTwo) {

                    }
                    fragment<HomeFragmentThree>(Nav.Dest.homeThree) {

                    }
                }
            }

            listNavController.add(navController)
        }

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
        setupActionBarWithNavController(navController, appBarConfiguration)
        //navView.setupWithNavController(navController)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            val i = it.order
            currentNavController = listNavController[i]
            currentNavController.navigate(currentNavController.graph.startDestination)
            true
        }
    }

    private fun String.toStartDestinationId(): Int {
        return when (this) {
            "homepage" -> Nav.Dest.home
            "dashboard" -> Nav.Dest.dashboard
            "notification" -> Nav.Dest.notification
            else -> Nav.Dest.home
        }
    }

    private fun String.toDestinationId(): Int {
        return this.take(3).toCharArray().map { it.toInt().toString() }.reduce { finalString, element -> finalString + element }.toInt()
    }
}