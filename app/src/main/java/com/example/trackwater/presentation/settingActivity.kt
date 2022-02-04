package com.example.trackwater


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import androidx.lifecycle.ViewModelProvider
import com.example.trackwater.databinding.ActivitySettingBinding
import com.example.trackwater.presentation.SettingViewModel
import java.lang.RuntimeException


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

    private lateinit var vM: SettingViewModel

    private var screenMode = MODE_UNKNOWN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bc = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(bc.root)
        parseIntent()
        vM = ViewModelProvider(this)[SettingViewModel::class.java]
        vM.logging(screenMode)
        when (screenMode){
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }

        vM.errorInputAge.observe(this) {
            val message = if(it){
                getString(R.string.error_input_age)
            } else {
                null
            }
            bc.tilAge.error = message
        }

        vM.shouldCloseScreen.observe(this){
            finish()
        }



    }

    private fun launchAddMode() {
        bc.bNext.setOnClickListener {
            vM.initSetting(bc.rgGender, bc.etnAge.text.toString(),
                bc.rgActivity, bc.etnWeight.text.toString())
        }
    }

    private fun launchEditMode() {
        vM.getSetting()
        vM.setting.observe(this) {
            var rb = bc.rgGender.findViewById<RadioButton>(vM.getGender(it))
            rb.isChecked = true
            bc.etnAge.setText(it.age.toString())
            rb = bc.rgActivity.findViewById(vM.getActivity(it))
            rb.isChecked = true
            bc.etnWeight.setText(it.weight.toString())
        }
        bc.bNext.setOnClickListener {
            vM.initSetting(bc.rgGender, bc.etnAge.text.toString(),
                bc.rgActivity, bc.etnWeight.text.toString())
        }
    }

    private fun parseIntent() {
        if(!intent.hasExtra(EXTRA_SCREEN_MODE)){
            throw RuntimeException("Параметр не задан")
        }
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if(mode != MODE_EDIT && mode != MODE_ADD){
            throw RuntimeException("Неизвестное значение режима работы $mode")
        }
        screenMode = mode
    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""


        fun newIntentAdd(context: Context): Intent{
            val intent = Intent(context, SettingActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEdit(context: Context): Intent{
            val intent = Intent(context, SettingActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            return intent
        }


    }

}