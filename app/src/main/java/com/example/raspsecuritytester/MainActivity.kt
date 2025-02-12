package com.example.raspsecuritytester

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.raspsecuritytester.model.MenuItem
import com.example.raspsecuritytester.ui.apps.AppsActivity
import com.example.raspsecuritytester.ui.theme.RASPSecurityTesterTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RASPSecurityTesterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainMenu(getMenuItems())
                }
            }
        }
    }

    private fun getMenuItems(): List<MenuItem> = listOf(
        MenuItem(
            title = "Apps & Permissions",
            description = "View installed apps and their permissions",
            onClick = { AppsActivity.start(this) }
        ),
        MenuItem(
            title = "Security Tests",
            description = "Run basic security tests",
            onClick = { /* TODO */ }
        ),
        MenuItem(
            title = "Device Info",
            description = "View device security information",
            onClick = { /* TODO */ }
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainMenu(menuItems: List<MenuItem>) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("RASP Security Tester") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(menuItems.size) { index ->
                MenuItem(menuItems[index])
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuItem(item: MenuItem) {
    Card(
        onClick = item.onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = item.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
