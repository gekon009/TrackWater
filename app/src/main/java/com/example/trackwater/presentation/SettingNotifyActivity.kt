package com.example.trackwater.presentation

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import com.example.trackwater.AlarmReceiver
import com.example.trackwater.databinding.ActivitySettingNotifyBinding
import java.text.SimpleDateFormat
import java.util.*

class SettingNotifyActivity : AppCompatActivity() {
    lateinit var bc : ActivitySettingNotifyBinding
    private lateinit var pendingIntent: PendingIntent
    private lateinit var calStart: Calendar
    private lateinit var calEnd: Calendar
    var pref : SharedPreferences? = null
    var alarmManagerArray = arrayListOf<AlarmManager>()

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bc = ActivitySettingNotifyBinding.inflate(layoutInflater)
        setContentView(bc.root)


        pref = getSharedPreferences("UserNotify", MODE_PRIVATE)
        var dateStart = pref?.getString("Start", "09 : 00")!!
        var dateEnd = pref?.getString("End", "22 : 00")!!
        var period = pref?.getInt("Period", 1)!!
        var enable = pref?.getBoolean("Enable", false)!!

        bc.tvTimeStart.text = dateStart
        bc.tvTimeEnd.text = dateEnd
        bc.etnPer.text = period.toString().toEditable()
        bc.switch1.isChecked = enable

        var startHour = dateStart.substring(0..1).toInt()
        var startMinute = dateStart.substring(5..6).toInt()

        calStart = Calendar.getInstance()
        calStart.set(Calendar.HOUR_OF_DAY, startHour)
        calStart.set(Calendar.MINUTE, startMinute)

        var endHour = dateEnd.substring(0..1).toInt()
        var endMinute = dateEnd.substring(5..6).toInt()
        calEnd = Calendar.getInstance()
        calEnd.set(Calendar.HOUR_OF_DAY, endHour)
        calEnd.set(Calendar.MINUTE, endMinute)

        val editor = pref?.edit()
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)


        bc.tvTimeStart.setOnClickListener {
            calStart = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                calStart.set(Calendar.HOUR_OF_DAY, hour)
                calStart.set(Calendar.MINUTE, minute)
                bc.tvTimeStart.text = SimpleDateFormat("HH : mm").format(calStart.time)
                editor?.remove("Start")
                editor?.putString("Start", SimpleDateFormat("HH : mm").format(calStart.time))
                editor?.apply()
                if(bc.switch1.isChecked) setAlarm()
                else cancelAlarm()
            }
            TimePickerDialog(
                this,
                timeSetListener,
                calStart.get(Calendar.HOUR_OF_DAY),
                calStart.get(Calendar.MINUTE),
                true
            ).show()
        }

        bc.tvTimeEnd.setOnClickListener {
            calEnd = Calendar.getInstance()
            val timeSetListener1 = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                calEnd.set(Calendar.HOUR_OF_DAY, hour)
                calEnd.set(Calendar.MINUTE, minute)
                bc.tvTimeEnd.text = SimpleDateFormat("HH : mm").format(calEnd.time)
                editor?.remove("End")
                editor?.putString("End", SimpleDateFormat("HH : mm").format(calEnd.time))
                editor?.apply()
                if(bc.switch1.isChecked) setAlarm()
                else cancelAlarm()
            }
            TimePickerDialog(
                this,
                timeSetListener1,
                calEnd.get(Calendar.HOUR_OF_DAY),
                calEnd.get(Calendar.MINUTE),
                true
            ).show()
        }

        bc.switch1.setOnClickListener {
            if(bc.switch1.isChecked)
            {
                editor?.remove("Enable")
                editor?.putBoolean("Enable", true)
                editor?.apply()
                setAlarm()
            }
            else{
                editor?.remove("Enable")
                editor?.putBoolean("Enable", false)
                editor?.apply()
                cancelAlarm()
            }
        }

        bc.etnPer.setOnClickListener {
            editor?.remove("Period")
            editor?.putInt("Period", bc.etnPer.text.toString().toInt())
            editor?.apply()
            if(bc.switch1.isChecked) setAlarm()
            else cancelAlarm()
        }

    }

    private fun cancelAlarm() {

        alarmManagerArray.forEach { it.cancel(pendingIntent) }

        //Toast.makeText(this, "Уведомления выключены", Toast.LENGTH_SHORT).show()


    }

    private fun setAlarm() {

        var startHour = bc.tvTimeStart.text.toString().substring(0..1).toInt()
        var startMinute = bc.tvTimeStart.text.toString().substring(5..6).toInt()

        calStart = Calendar.getInstance()
        calStart.set(Calendar.HOUR_OF_DAY, startHour)
        calStart.set(Calendar.MINUTE, startMinute)

        var endHour = bc.tvTimeEnd.text.toString().substring(0..1).toInt()
        var endMinute = bc.tvTimeEnd.text.toString().substring(5..6).toInt()
        calEnd = Calendar.getInstance()
        calEnd.set(Calendar.HOUR_OF_DAY, endHour)
        calEnd.set(Calendar.MINUTE, endMinute)


        val intent = Intent(this, AlarmReceiver::class.java)

        var i = 0
        var start = calStart.get(Calendar.HOUR_OF_DAY)
        var end = calEnd.get(Calendar.HOUR_OF_DAY)
        var step = bc.etnPer.text.toString().toInt()
        calStart.set(Calendar.SECOND, 0)
        calEnd.set(Calendar.SECOND, 0)

        do {
            alarmManagerArray.add(getSystemService(ALARM_SERVICE) as AlarmManager)
            if (start != end) {
                calStart.set(Calendar.HOUR_OF_DAY, start)
                alarmManagerArray[i].setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calStart.timeInMillis,
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent
                )
                Log.d("Notify", "Установлено напоминание на: "
                        + calStart.get(Calendar.HOUR_OF_DAY).toString()
                        + " ч. "
                        + calStart.get(Calendar.MINUTE).toString()
                        + "м. "
                        + calStart.get(Calendar.SECOND).toString()
                        + "с."
                )
            }
            else {
                if (calStart.get(Calendar.MINUTE) < calEnd.get(Calendar.MINUTE)) {
                    calStart.set(Calendar.HOUR_OF_DAY, start)
                    alarmManagerArray[i].setRepeating(
                        AlarmManager.RTC_WAKEUP,
                        calStart.timeInMillis,
                        AlarmManager.INTERVAL_DAY,
                        pendingIntent
                    )
                    Log.d("Notify", "Установлено напоминание на: "
                    + calStart.get(Calendar.HOUR_OF_DAY).toString()
                    + " ч. "
                    + calStart.get(Calendar.MINUTE).toString()
                    + "м. "
                    + calStart.get(Calendar.SECOND).toString()
                    + "с."
                    )
                    alarmManagerArray[i].setRepeating(
                        AlarmManager.RTC_WAKEUP,
                        calEnd.timeInMillis,
                        AlarmManager.INTERVAL_DAY,
                        pendingIntent
                    )
                    Log.d("Notify", "Установлено напоминание на: "
                    + calEnd.get(Calendar.HOUR_OF_DAY).toString()
                    + " ч. "
                    + calEnd.get(Calendar.MINUTE).toString()
                    + "м. "
                    + calEnd.get(Calendar.SECOND).toString()
                    + "с."
                    )
                }
                else{
                    alarmManagerArray[i].setRepeating(
                        AlarmManager.RTC_WAKEUP,
                        calEnd.timeInMillis,
                        AlarmManager.INTERVAL_DAY,
                        pendingIntent
                    )
                    Log.d("Notify", "Установлено напоминание на: "
                            + calEnd.get(Calendar.HOUR_OF_DAY).toString()
                            + " ч. "
                            + calEnd.get(Calendar.MINUTE).toString()
                            + "м. "
                            + calEnd.get(Calendar.SECOND).toString()
                            + "с."
                    )
                }
            }

            Log.d("Notify", "I = $start")
            start += step
            i++
            Log.d("Notify", "I+step = $start")
            Log.d("Notify", "End = $end")

        }while (start != end + 1)

        //Toast.makeText(this, "Уведомления включены", Toast.LENGTH_SHORT).show()

    }
    fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
}