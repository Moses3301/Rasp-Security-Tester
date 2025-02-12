package com.example.raspsecuritytester.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.raspsecuritytester.model.AppInfo
import com.example.raspsecuritytester.model.PermissionInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppItem(appInfo: AppInfo) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            AppHeader(appInfo)
            if (appInfo.permissions.isNotEmpty()) {
                PermissionsList(appInfo.permissions)
            }
        }
    }
}

@Composable
private fun AppHeader(appInfo: AppInfo) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (appInfo.iconBitmap != null) {
            Image(
                bitmap = appInfo.iconBitmap,
                contentDescription = "App icon",
                modifier = Modifier.size(48.dp)
            )
        } else {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = appInfo.appName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = appInfo.packageName,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun PermissionsList(permissions: List<PermissionInfo>) {
    Spacer(modifier = Modifier.height(8.dp))
    Divider()
    Spacer(modifier = Modifier.height(8.dp))

    Text(
        text = "Permissions (${permissions.size}):",
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.Medium
    )
    
    Spacer(modifier = Modifier.height(4.dp))

    permissions.forEach { permission ->
        Text(
            text = "• ${permission.name}",
            style = MaterialTheme.typography.bodySmall,
            color = if (permission.isGranted) 
                MaterialTheme.colorScheme.primary 
            else 
                MaterialTheme.colorScheme.error,
            modifier = Modifier.padding(start = 8.dp, bottom = 2.dp)
        )
    }
}
