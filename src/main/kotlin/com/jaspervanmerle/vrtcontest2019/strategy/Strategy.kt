package com.jaspervanmerle.vrtcontest2019.strategy

import com.jaspervanmerle.vrtcontest2019.model.Location
import com.jaspervanmerle.vrtcontest2019.model.Worker

class Strategy(val locations: List<Location>) {
    val base = locations[0]

    val workers = mutableListOf<Worker>()

    fun execute(): List<Worker> {
        return workers
    }
}
