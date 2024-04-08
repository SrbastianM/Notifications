package com.srbastian.notifications

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.srbastian.notifications.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private val CHANNEL_ID = "1"

    private var increaseNumber = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        super.onCreate(savedInstanceState)
        setContentView(view)

        binding.btnSend.setOnClickListener {
            increaseNumber++
            binding.btnSend.text = increaseNumber.toString()
            startNotification()
        }
    }

    private fun startNotification() {
        val builder = NotificationCompat.Builder(this@MainActivity, CHANNEL_ID)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(CHANNEL_ID, "1", NotificationManager.IMPORTANCE_DEFAULT)
            val manager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            manager.createNotificationChannel(channel)

            builder.setSmallIcon(R.drawable.small_icon)
                .setContentTitle("Title")
                .setContentText("Small Test")

        } else {
            builder.setSmallIcon(R.drawable.small_icon).setContentTitle("Title")
                .setContentText("Small Test")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        }

        val notificationManagerCompat = NotificationManagerCompat.from(this@MainActivity)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        notificationManagerCompat.notify(1, builder.build())
    }
}