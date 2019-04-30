package com.jaspervanmerle.vrtcontest2019.model

import com.jaspervanmerle.vrtcontest2019.model.action.Action
import com.jaspervanmerle.vrtcontest2019.model.action.StartAction

class Worker(startLocation: Location, startTime: Int) {
    val actions = mutableListOf<Action>(StartAction(startLocation, startTime))
    var location = startLocation

    var workingUntil = startTime

    fun getBestArrivalTime(job: Location): Int {
        return workingUntil + location.distanceTo(job)
    }

    fun addAction(action: Action) {
        actions += action
        location = action.location
    }
}
