package com.jaspervanmerle.vrtcontest2019.model

import com.jaspervanmerle.vrtcontest2019.model.action.Action
import com.jaspervanmerle.vrtcontest2019.model.action.StartAction

class Worker(startLocation: Location, startTime: Int) : Point(startLocation) {
    val actions = mutableListOf<Action>(StartAction(startLocation, startTime))

    var workingUntil = startTime

    fun getBestArrivalTime(job: Location): Int {
        return workingUntil + distanceTo(job)
    }

    fun addAction(action: Action) {
        x = action.location.x
        y = action.location.y
        actions += action
    }
}
