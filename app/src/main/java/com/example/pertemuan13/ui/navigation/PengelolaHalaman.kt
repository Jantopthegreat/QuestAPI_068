package com.example.pertemuan13.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pertemuan13.ui.views.DestinasiDetail
import com.example.pertemuan13.ui.views.DestinasiEdit
import com.example.pertemuan13.ui.views.DestinasiEntry
import com.example.pertemuan13.ui.views.DestinasiHome
import com.example.pertemuan13.ui.views.DetailMhsView
import com.example.pertemuan13.ui.views.EditMhsView
import com.example.pertemuan13.ui.views.EntryMhsScreen
import com.example.pertemuan13.ui.views.HomeScreen

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = Modifier
    ) {
        composable(
            route = DestinasiHome.route
        ){
            HomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntry.route) },
                onDetailClick = { nim ->
                    navController.navigate("${DestinasiDetail.route}/$nim")
                    println(nim)
                    },
                onEditClick = { nim ->
                    navController.navigate("${DestinasiEdit.route}/$nim")
                }
            )
        }
        composable(
            route = DestinasiEntry.route
        ){
            EntryMhsScreen(
                navigateBack = {
                    navController.navigate(DestinasiHome.route) {
                        popUpTo(DestinasiHome.route) { inclusive = true }
                    }
                }
            )

        }
        composable(
            route = DestinasiDetail.routeWithArgs,
            arguments = listOf(navArgument(DestinasiDetail.nim){
                type = NavType.StringType
            })
        ){ backStackEntry ->
            val nim = backStackEntry.arguments?.getString(DestinasiDetail.nim)
            nim?.let {
                DetailMhsView(
                    navigateBack = {
                        navController.navigateUp()
                    },
                    onEditClick = {}
                )
            }

        }
        composable(
            route = DestinasiEdit.routeWithArgs,
            arguments = listOf(navArgument(DestinasiEdit.nim){
                type = NavType.StringType
            })
        ){
            EditMhsView(
                navigateBack = {
                    navController.popBackStack()
                },
                onNavigateUp = {
                    navController.navigate(
                        DestinasiEdit.route
                    ){
                        popUpTo(DestinasiHome.route){
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}