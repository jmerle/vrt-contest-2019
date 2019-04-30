package com.jaspervanmerle.vrtcontest2019.model

class Location(
    val id: Int,
    x: Int,
    y: Int,
    val duration: Int,
    val requiredWorkers: Int,
    val startTime: Int,
    val endTime: Int
) : Point(x, y) {
    val reward = duration * requiredWorkers * (requiredWorkers + 5)
    val latestPossibleArrival = endTime - duration
}
