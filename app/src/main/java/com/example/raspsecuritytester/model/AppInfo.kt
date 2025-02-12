package com.example.raspsecuritytester.model

data class AppInfo(
    val packageName: String,
    val appName: String,
    val iconBitmap: androidx.compose.ui.graphics.ImageBitmap?,
    val permissions: List<PermissionInfo>
)

data class PermissionInfo(
    val name: String,
    val isGranted: Boolean
)
