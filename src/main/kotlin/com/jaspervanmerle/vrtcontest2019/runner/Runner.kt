package com.jaspervanmerle.vrtcontest2019.runner

import com.jaspervanmerle.vrtcontest2019.io.InputReader
import com.jaspervanmerle.vrtcontest2019.model.Location
import com.jaspervanmerle.vrtcontest2019.model.Worker

abstract class Runner(val args: Array<String>) {
    abstract fun run()

    fun readLocations(ir: InputReader): List<Location> {
        val locations = mutableListOf<Location>()

        val locationCount = ir.nextInt()
        for (i in 0 until locationCount) {
            locations += Location(
                i + 1,
                ir.nextInt(),
                ir.nextInt(),
                ir.nextInt(),
                ir.nextInt(),
                ir.nextInt(),
                ir.nextInt()
            )
        }

        return locations
    }

    fun printWorkers(workers: List<Worker>) {
        for (worker in workers) {
            for (action in worker.actions) {
                println(action.toString())
            }

            println("end")
        }
    }
}
