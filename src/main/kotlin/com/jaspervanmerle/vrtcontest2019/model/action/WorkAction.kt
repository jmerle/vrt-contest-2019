package com.jaspervanmerle.vrtcontest2019.model.action

import com.jaspervanmerle.vrtcontest2019.model.Location

class WorkAction(location: Location, val startTime: Int, val endTime: Int) : Action(location) {
    override fun toString(): String {
        return "work $startTime $endTime ${location.id}"
    }
}
