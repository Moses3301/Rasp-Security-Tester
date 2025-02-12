package com.example.raspsecuritytester.ui.apps

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.example.raspsecuritytester.ui.components.AppsScreen
import com.example.raspsecuritytester.ui.theme.RASPSecurityTesterTheme
import com.example.raspsecuritytester.viewmodel.AppsViewModel

class AppsActivity : ComponentActivity() {
    private val viewModel: AppsViewModel by viewModels()
    private val PERMISSION_REQUEST_CODE = 123

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, AppsActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (checkPermission()) {
            showApps()
        } else {
            requestPermission()
        }
    }

    private fun checkPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.QUERY_ALL_PACKAGES
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.QUERY_ALL_PACKAGES),
            PERMISSION_REQUEST_CODE
        )
    }

    private fun showApps() {
        setContent {
            RASPSecurityTesterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Observe apps from ViewModel
                    val apps by viewModel.apps.collectAsState()
                    val isLoading by viewModel.isLoading.collectAsState()
                    AppsScreen(apps = apps, isLoading = isLoading)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showApps()
                } else {
                    // Permission denied - you might want to show a message to the user
                    finish()
                }
            }
        }
    }
}