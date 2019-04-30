package com.jaspervanmerle.vrtcontest2019.model

data class Location(
    val id: Int,
    val x: Int,
    val y: Int,
    val duration: Int,
    val requiredWorkers: Int,
    val startTime: Int,
    val endTime: Int
)
