package com.example.trackwater.domain

data class Setting(
    val activity: Int,
    val gender: Int,
    val age: Int,
    val weight: Int,
    var id: Int = UNDEFINED_ID
) {
    companion object{

        const val UNDEFINED_ID = -1
    }

}
