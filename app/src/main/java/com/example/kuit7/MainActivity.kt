package com.example.kuit7

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainApp()
                }
            }
        }
    }
}

@Composable
fun MainApp() {
    val navController = rememberNavController()

    val articles = listOf(
        NewsArticle(R.drawable.image1, "Europe", "Ukraine's President Zelensky to BBC: Blood money being paid...", "BBC News", R.drawable.bbc_icon, "14m ago"),
        NewsArticle(R.drawable.image2, "Travel", "Her train broke down. Her phone died. And then she met her..", "CNN", R.drawable.cnn_icon, "1h ago"),
        NewsArticle(R.drawable.image3, "Europe", "Russian warship: Moskva sinks in Black Sea", "BBC News", R.drawable.bbc_icon, "4h ago"),
        NewsArticle(R.drawable.image4, "Money", "Wind power produced more electricity than coal and nucle...", "USA Today", R.drawable.usat_icon, "4h ago"),
        NewsArticle(R.drawable.image5, "Life", "'We keep rising to new challenges:' For churches hit by...", "USA Today", R.drawable.usat_icon, "4h ago")
    )

    val callList = listOf(
        Call(R.drawable.phone1, "박서연", "010-1234-5678"),
        Call(R.drawable.phone2, "김건국", "010-1234-5678"),
        Call(R.drawable.phone3, "홍길동", "010-1234-5678")
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val showBottomBar = currentDestination?.hasRoute<NewsHome>() == true
            || currentDestination?.hasRoute<ContactHome>() == true

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    NavigationBarItem(
                        selected = currentDestination?.hasRoute<NewsHome>() == true,
                        onClick = {
                            navController.navigate(NewsHome) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Text("🏠", fontSize = 20.sp) },
                        label = { Text("홈") }
                    )
                    NavigationBarItem(
                        selected = currentDestination?.hasRoute<ContactHome>() == true,
                        onClick = {
                            navController.navigate(ContactHome) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Text("📞", fontSize = 20.sp) },
                        label = { Text("연락처") }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = NewsHome,
            modifier = Modifier.padding(padding)
        ) {
            composable<NewsHome> {
                NewsScreen(
                    articles = articles,
                    onArticleClick = { article ->
                        navController.navigate(
                            NewsDetailRoute(
                                image = article.image,
                                category = article.category,
                                title = article.title,
                                source = article.source,
                                sourceIcon = article.sourceIcon,
                                time = article.time
                            )
                        )
                    }
                )
            }

            composable<ContactHome> {
                ContactScreen(
                    callList = callList,
                    onContactClick = { call ->
                        navController.navigate(
                            ContactDetailRoute(
                                image = call.image,
                                name = call.name,
                                phone = call.phone
                            )
                        )
                    }
                )
            }

            composable<NewsDetailRoute> { backStackEntry ->
                val route = backStackEntry.toRoute<NewsDetailRoute>()
                NewsDetailScreen(
                    article = NewsArticle(
                        route.image, route.category, route.title,
                        route.source, route.sourceIcon, route.time
                    ),
                    onBack = { navController.popBackStack() }
                )
            }

            composable<ContactDetailRoute> { backStackEntry ->
                val route = backStackEntry.toRoute<ContactDetailRoute>()
                ContactDetailScreen(
                    call = Call(route.image, route.name, route.phone),
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}