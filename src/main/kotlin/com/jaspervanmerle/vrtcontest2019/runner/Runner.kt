package com.jaspervanmerle.vrtcontest2019.runner

import com.jaspervanmerle.vrtcontest2019.io.InputReader
import com.jaspervanmerle.vrtcontest2019.model.Location
import com.jaspervanmerle.vrtcontest2019.model.Worker
import com.jaspervanmerle.vrtcontest2019.strategy.Strategy

abstract class Runner(val args: Array<String>) {
    abstract fun run()

    protected fun executeStrategy(locations: List<Location>): List<Worker> {
        val base = locations[0]
        val jobs = locations.subList(1, locations.size)
        return Strategy(base, jobs).execute()
    }

    protected fun readLocations(ir: InputReader): List<Location> {
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

    protected fun printWorkers(workers: List<Worker>) {
        for (worker in workers) {
            for (action in worker.actions) {
                println(action.toString())
            }

            println("end")
        }
    }
}
