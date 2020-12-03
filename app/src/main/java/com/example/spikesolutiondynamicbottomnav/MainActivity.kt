package com.example.spikesolutiondynamicbottomnav

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.createGraph
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.fragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.spikesolutiondynamicbottomnav.ui.dashboard.DashboardFragment
import com.example.spikesolutiondynamicbottomnav.ui.home.HomeFragment
import com.example.spikesolutiondynamicbottomnav.ui.notifications.NotificationsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    object Nav {
        object Dest {
            const val home = 2
            const val dashboard = 3
            const val notification = 3
        }
    }

    val listMenu = listOf(
            Pair(
                    "MediasetPlay",
                    "homepage"
            ),
            Pair(
                    "5f6370dd29fbc8001afcda0e",
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
                    "5f7b2527a0e8450019448c3a",
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
                graph = createGraph(menu.first.toDestinationId(), getHomeDestination(menu.second)) {
                    fragment<HomeFragment>(Nav.Dest.home) {
                        label = getString(R.string.title_home)
                    }
                    fragment<DashboardFragment>(Nav.Dest.dashboard) {
                        label = getString(R.string.title_home)
                    }
                    fragment<NotificationsFragment>(Nav.Dest.notification) {
                        label = getString(R.string.title_home)
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

    private fun getHomeDestination(second: String): Int {
        return when (second) {
            "homepage" -> Nav.Dest.home
            "livelist" -> Nav.Dest.dashboard
            else -> Nav.Dest.home
        }
    }

    fun String.toDestinationId(): Int {
        return this.take(3).toCharArray().map { it.toInt().toString() }.reduce { finalString, element -> finalString + element }.toInt()
    }
}