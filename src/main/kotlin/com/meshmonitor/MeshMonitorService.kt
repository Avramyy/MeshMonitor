package com.meshmonitor

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat

class MeshMonitorService : Service() {

    companion object {
        private const val TAG = "MeshMonitorService"
        private const val CHANNEL_ID = "mesh_monitor_channel"
        private const val NOTIFICATION_ID = 1
        private const val PACKAGE_NAME = "com.meshcentral.agent"
        private const val CHECK_INTERVAL = 60000L // 1 minute in milliseconds
    }

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable
    private var isRunning = true

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "Service Created")
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "Service Started")
        startForegroundNotification()
        startMonitoring()
        return START_STICKY
    }

    private fun startMonitoring() {
        runnable = object : Runnable {
            override fun run() {
                if (isRunning) {
                    checkAndRestartApp()
                    handler.postDelayed(this, CHECK_INTERVAL)
                }
            }
        }
        handler.post(runnable)
    }

    private fun checkAndRestartApp() {
        try {
            val isAppRunning = isApplicationRunning(PACKAGE_NAME)
            Log.d(TAG, "App Running: $isAppRunning")

            if (!isAppRunning) {
                Log.w(TAG, "MeshCentral Agent stopped! Restarting...")
                startApp()
                minimizeApp()
                showNotification("MeshCentral Agent restarted")
            } else {
                Log.d(TAG, "MeshCentral Agent is running normally")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error checking app status", e)
        }
    }

    private fun isApplicationRunning(packageName: String): Boolean {
        return try {
            val activityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
            val runningProcesses = activityManager.runningAppProcesses ?: return false
            runningProcesses.any { it.processName == packageName }
        } catch (e: Exception) {
            Log.e(TAG, "Error checking if app is running", e)
            false
        }
    }

    private fun startApp() {
        try {
            val launchIntent = packageManager.getLaunchIntentForPackage(PACKAGE_NAME)
            if (launchIntent != null) {
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(launchIntent)
                Log.d(TAG, "App started successfully")
            } else {
                Log.e(TAG, "Could not find launch intent for $PACKAGE_NAME")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error starting app", e)
        }
    }

    private fun minimizeApp() {
        try {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            Log.d(TAG, "App minimized")
        } catch (e: Exception) {
            Log.e(TAG, "Error minimizing app", e)
        }
    }

    private fun startForegroundNotification() {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Mesh Monitor")
            .setContentText("Monitoring MeshCentral Agent...")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true)
            .build()

        startForeground(NOTIFICATION_ID, notification)
    }

    private fun showNotification(message: String) {
        try {
            val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Mesh Monitor")
                .setContentText(message)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build()

            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(System.currentTimeMillis().toInt(), notification)
        } catch (e: Exception) {
            Log.e(TAG, "Error showing notification", e)
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Mesh Monitor",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
        handler.removeCallbacks(runnable)
        Log.d(TAG, "Service Destroyed")
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
