package com.example.trackwater


import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import com.example.trackwater.databinding.ActivitySettingBinding


class SettingActivity : AppCompatActivity() {
    lateinit var bc : ActivitySettingBinding
    var pref : SharedPreferences? = null
    var check = false
    var checkGender = false
    var checkAge = false
    var checkActivity = false
    var checkWeight = false
    var norma = 0
    var activity = -1 // 0 - Низкая, 1 - Средняя, 2 - Высокая, -1 - Не заполнен
    var gender = -1 // 0 - Мужчина, 1 - Женщина, -1 - Не заполнен
    var age = -1 // -1 - Не заполнен
    var weight = -1 // -1 - Не заполнен





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bc = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(bc.root)

        Log.d("Testing", "onCreate")

        pref = getSharedPreferences("UserData", MODE_PRIVATE)
        check = pref?.getBoolean("Check", false)!!

        gender = pref?.getInt("Gender", -1)!!
        age = pref?.getInt("Age", -1)!!
        activity = pref?.getInt("Activity", -1)!!
        weight = pref?.getInt("Weight", -1)!!

        if(gender == 0) bc.rbMen.isChecked = true else bc.rbWomen.isChecked = true
        bc.etnAge.text = age.toString().toEditable()
        when(activity){
            0 -> bc.rbLow.isChecked = true
            1 -> bc.rbMiddle.isChecked = true
            2 ->  bc.rbHigh.isChecked = true
        }
        bc.etnWeight.text = weight.toString().toEditable()


        bc.bNext.setOnClickListener {
            val editor = pref?.edit()
            //_______________Проверка на заполнение Пола____________________________________________
            if (bc.rbMen.isChecked || bc.rbWomen.isChecked) {
                bc.rgGender.setBackgroundColor(resources.getColor(R.color.white))
                bc.tvGender.text = "Укажите пол"
                bc.tvGender.setTextColor(resources.getColor(R.color.black))
                checkGender = true
                editor?.putInt("Gender", gender)
                editor?.apply()
            } else {
                bc.rgGender.setBackgroundColor(resources.getColor(R.color.RedFalseBackground))
                bc.tvGender.text = "Пол не выбран"
                bc.tvGender.setTextColor(resources.getColor(R.color.RedFalseFont))
                checkGender = false
            }
            //______________________________________________________________________________________
            //_______________Проверка на заполнение Возраста________________________________________
            var etnAgeIsCheckedString = bc.etnAge.text.toString()
            var etnAgeIsChecked: Int = -1
            if (etnAgeIsCheckedString.isNotEmpty()) {
                etnAgeIsChecked = etnAgeIsCheckedString.toInt()
                if (etnAgeIsChecked > 0 ) {
                    bc.tvAge.setBackgroundColor(resources.getColor(R.color.white))
                    bc.tvAge.text = "Укажите возраст"
                    bc.etnAge.setTextColor(resources.getColor(R.color.black))
                    checkAge = true
                    age = etnAgeIsChecked
                    editor?.putInt("Age", age)
                    editor?.apply()
                } else {
                    bc.tvAge.setBackgroundColor(resources.getColor(R.color.RedFalseBackground))
                    bc.tvAge.text = "Выбран некорректный возраст"
                    bc.etnAge.setTextColor(resources.getColor(R.color.RedFalseFont))
                    checkAge = false
                }
            }
            else{
                bc.tvAge.setBackgroundColor(resources.getColor(R.color.RedFalseBackground))
                bc.tvAge.text = "Возраст не указан"
                bc.etnAge.setTextColor(resources.getColor(R.color.RedFalseFont))
                checkAge = false
            }
            //______________________________________________________________________________________
            //_______________Проверка на заполнение Активности______________________________________
            if (bc.rbLow.isChecked || bc.rbMiddle.isChecked || bc.rbHigh.isChecked) {
                bc.rgActivity.setBackgroundColor(resources.getColor(R.color.white))
                bc.tvActivity.text = "Выберите активность"
                bc.tvActivity.setTextColor(resources.getColor(R.color.black))
                checkActivity = true
                editor?.putInt("Activity", activity)
                editor?.apply()
            } else {
                bc.rgActivity.setBackgroundColor(resources.getColor(R.color.RedFalseBackground))
                bc.tvActivity.text = "Активность не выбрана"
                bc.tvActivity.setTextColor(resources.getColor(R.color.RedFalseFont))
                checkActivity = false
            }
            //______________________________________________________________________________________
            //_______________Проверка на заполнение Веса____________________________________________
            var etnWeightString = bc.etnWeight.text.toString()
            var etnWeightIsChecked: Int = 0
            if (etnWeightString.isNotEmpty()) {
                etnWeightIsChecked = etnWeightString.toInt()
                if (etnWeightIsChecked > 0 ) {
                    checkWeight = true
                    bc.tvWeight.setBackgroundColor(resources.getColor(R.color.white))
                    bc.tvWeight.text = "Ваш вес"
                    bc.etnWeight.setTextColor(resources.getColor(R.color.black))
                    checkWeight = true
                    weight = etnWeightIsChecked
                    editor?.putInt("Weight", weight)
                    editor?.apply()
                } else {
                    bc.tvWeight.setBackgroundColor(resources.getColor(R.color.RedFalseBackground))
                    bc.tvWeight.text = "Указан некорректный вес"
                    bc.etnWeight.setTextColor(resources.getColor(R.color.RedFalseFont))
                    checkWeight = false
                }
            }
            else{
                bc.tvWeight.setBackgroundColor(resources.getColor(R.color.RedFalseBackground))
                bc.tvWeight.text = "Вес не указан"
                bc.etnWeight.setTextColor(resources.getColor(R.color.RedFalseFont))
                checkWeight = false
            }
            //______________________________________________________________________________________

            check = (checkGender && checkAge && checkActivity && checkWeight)

            editor?.putBoolean("Check", check)
            editor?.apply()

            if(check) {
                norma = norm4weight(etnWeightIsChecked)[activity].norm
                editor?.putInt("Norma", norma)
                editor?.apply()
                val main = Intent(this, ActivityMain::class.java)
                startActivity(main)
            }
        }

        bc.rbLow.setOnClickListener { activity = 0 }
        bc.rbMiddle.setOnClickListener { activity = 1 }
        bc.rbHigh.setOnClickListener { activity = 2 }
        bc.rbMen.setOnClickListener { gender = 0 }
        bc.rbWomen.setOnClickListener { gender = 1 }

    }




    fun norm4weight(weight: Int): Array<normWater> {
        var w = weight / 10
        var water = arrayOf(normWater(0, 1250),
            normWater(1, 1700),
            normWater(2, 2000))
        when(w){
            5 -> {
                water = arrayOf(normWater(0, 1550),
                    normWater(1, 2000),
                    normWater(2, 2300))
            }
            6 -> {
                water = arrayOf(normWater(0, 1850),
                    normWater(1, 2300),
                    normWater(2, 2650))
            }
            7 -> {
                water = arrayOf(normWater(0, 2200),
                    normWater(1, 2550),
                    normWater(2, 3000))
            }
            8 -> {
                water = arrayOf(normWater(0, 2500),
                    normWater(1, 2950),
                    normWater(2, 3300))
            }
            9 -> {
                water = arrayOf(normWater(0, 2800),
                    normWater(1, 3300),
                    normWater(2, 3600))
            }
            10 -> {
                water = arrayOf(normWater(0, 3100),
                    normWater(1, 3600),
                    normWater(2, 3900))
            }
            11 -> {
                water = arrayOf(normWater(0, 3400),
                    normWater(1, 3900),
                    normWater(2, 4100))
            }
            in 12..100 -> {
                water = arrayOf(normWater(0, 3700),
                    normWater(1, 4200),
                    normWater(2, 4400))
            }
        }
        return water
    }


    fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
    class normWater(val activity: Int, val norm: Int)

}