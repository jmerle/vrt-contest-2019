package com.jaspervanmerle.vrtcontest2019.model.action

import com.jaspervanmerle.vrtcontest2019.model.Location

class ArriveAction(location: Location, val time: Int) : Action(location) {
    override fun toString(): String {
        return "arrive $time ${location.id}"
    }
}
