package com.example.trackwater.domain

data class WaterNorma(
    val norma: Int,
    var drink: Int,
    var id: Int = UNDEFINED_ID
    ) {

    companion object{
        const val UNDEFINED_ID = -1
    }
}
