package com.jaspervanmerle.vrtcontest2019.model

data class Location(
    val id: Int,
    val x: Int,
    val y: Int,
    val duration: Int,
    val requiredWorkers: Int,
    val startTime: Int,
    val endTime: Int
) {
    val reward = duration * requiredWorkers * (requiredWorkers + 5)
    val latestPossibleArrival = endTime - duration

    fun distanceTo(other: Location): Int {
        return Math.abs(x - other.x) + Math.abs(y - other.y)
    }
}
