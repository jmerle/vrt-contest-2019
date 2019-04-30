package com.jaspervanmerle.vrtcontest2019.model.action

import com.jaspervanmerle.vrtcontest2019.model.Location

abstract class Action(val location: Location) {
    abstract override fun toString(): String
}
