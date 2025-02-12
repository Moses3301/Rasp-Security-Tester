package com.example.raspsecuritytester.viewmodel

import android.app.Application
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.raspsecuritytester.model.AppInfo
import com.example.raspsecuritytester.model.PermissionInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AppsViewModel(application: Application) : AndroidViewModel(application) {
    private val _isLoading = MutableStateFlow(true)  // Start with true
    val isLoading = _isLoading.asStateFlow()

    private val _apps = MutableStateFlow<List<AppInfo>>(emptyList())
    val apps = _apps.asStateFlow()

    init {
        loadApps()  // Load apps immediately when ViewModel is created
    }

    fun loadApps() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val context = getApplication<Application>()
                val pm = context.packageManager

                val packageList = pm.getInstalledPackages(
                    PackageManager.GET_PERMISSIONS or
                            PackageManager.GET_META_DATA or
                            PackageManager.GET_ACTIVITIES
                )

                val appsList = packageList.map { packageInfo ->
                    val appIcon = try {
                        pm.getApplicationIcon(packageInfo.packageName)
                            .toBitmap()
                            .asImageBitmap()
                    } catch (e: Exception) {
                        null
                    }

                    val permissions = packageInfo.requestedPermissions?.mapIndexed { index, permission ->
                        val isGranted = packageInfo.requestedPermissionsFlags?.get(index)
                            ?.and(PackageInfo.REQUESTED_PERMISSION_GRANTED) != 0

                        PermissionInfo(
                            name = permission.split(".").last(),
                            isGranted = isGranted
                        )
                    } ?: emptyList()

                    AppInfo(
                        appName = packageInfo.applicationInfo?.loadLabel(pm)?.toString() ?: "Unknown",
                        packageName = packageInfo.packageName,
                        iconBitmap = appIcon,
                        permissions = permissions
                    )
                }.sortedBy { it.appName }

                _apps.value = appsList
            } finally {
                _isLoading.value = false
            }
        }
    }
}