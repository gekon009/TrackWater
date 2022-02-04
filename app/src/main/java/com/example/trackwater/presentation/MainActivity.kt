package com.example.trackwater.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.trackwater.SettingActivity
import com.example.trackwater.databinding.ActivityMainBinding

class ActivityMain : AppCompatActivity() {
    lateinit var bc: ActivityMainBinding
    var check = false

    private lateinit var vM: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bc = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bc.root)
        vM = ViewModelProvider(this)[MainViewModel::class.java]

        check = true
        if (!check) {
            val intent = SettingActivity.newIntentAdd(this)
            startActivity(intent)
        }

        vM.getWaterNorma()
        vM.waterNorma.observe(this){
            val drink = it.drink
            val norma = it.norma
            bc.tvProgress.text = "$drink / $norma"
            bc.pbWater.max = norma
            bc.pbWater.progress = drink
        }


        bc.bAdd.setOnClickListener {
            val drink = 200
            vM.drinkWaterNorma(drink)
        }

        bc.bRemove.setOnClickListener {
            val drink = -200
            vM.drinkWaterNorma(drink)
        }


        bc.bSetting.setOnClickListener {
            val intent = SettingActivity.newIntentEdit(this)
            startActivity(intent)
        }

        bc.bSettingNotify.setOnClickListener {
            val intent = SettingActivity.newIntentAdd(this)
            startActivity(intent)
        }


    }

}