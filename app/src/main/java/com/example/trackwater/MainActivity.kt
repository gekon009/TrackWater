package com.example.trackwater

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.trackwater.databinding.ActivityMainBinding
import com.example.trackwater.databinding.ActivitySettingBinding
import com.example.trackwater.databinding.ActivitySettingNotifyBinding
import android.os.Build as Build

class ActivityMain : AppCompatActivity() {
    lateinit var bc : ActivityMainBinding
    var pref : SharedPreferences? = null
    var norma = 0
    var prog = 0
    var check = false

    val CHANNEL_ID = "channelName"
    val CHANNEL_NAME = "channelID"
    val NOTIFICATION_ID = 0
    var vibrate = longArrayOf(1000, 1000, 1000, 1000, 1000)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bc = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bc.root)

        pref = getSharedPreferences("UserData", MODE_PRIVATE)
        check = pref?.getBoolean("Check", false)!!

        if(!check){
            val stage2 = Intent(this, SettingActivity::class.java)
            startActivity(stage2)
        }

        val editor = pref?.edit()

        norma = pref?.getInt("Norma", 0)!!
        prog = pref?.getInt("Progress", 0)!!

        bc.pbWater.max = norma
        bc.pbWater.progress = prog

        bc.tvProgress.text = "$prog/$norma"

        bc.bAdd.setOnClickListener {
            prog += 200
            bc.pbWater.progress = prog

            bc.tvProgress.text = "$prog/$norma"

            editor?.remove("Progress")
            editor?.putInt("Progress", prog)
            editor?.apply()
        }

        bc.bRemove.setOnClickListener {
            pref = getSharedPreferences("UserData", MODE_PRIVATE)

            prog -= 200
            bc.pbWater.progress = prog

            bc.tvProgress.text = "$prog/$norma"

            editor?.remove("Progress")
            editor?.putInt("Progress", prog)
            editor?.apply()
        }

        createNotificationChannel()

        val intent = Intent(this, ActivityMain::class.java)
        val pendingIntent = TaskStackBuilder.create(this).run{
            addNextIntentWithParentStack(intent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Title Notification")
            .setContentText("This notification text")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setVibrate(vibrate)
            .build()

        val notificationManager = NotificationManagerCompat.from(this)

        bc.bSetting.setOnClickListener {
            val setting = Intent(this, SettingActivity::class.java)
            startActivity(setting)
        }

        bc.bSettingNotify.setOnClickListener {
            val s = Intent(this, SettingNotifyActivity::class.java)
            startActivity(s)
        }



    }

    private fun createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            val name : CharSequence = "WaterDrinkReminderChannel"
            val description = "Канал для отправки напомнинаний выпить воду"
            val impotance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("AlarmWater", name, impotance)
            channel.description = description
            val notificationManager = getSystemService(
                NotificationManager::class.java
            )

            notificationManager.createNotificationChannel(channel)

        }
    }

}