package com.jaspervanmerle.vrtcontest2019.runner

import com.jaspervanmerle.vrtcontest2019.io.InputReader

class ProductionRunner(args: Array<String>) : Runner(args) {
    override fun run() {
        val ir = InputReader(System.`in`)
        val locations = readLocations(ir)
        val workers = executeStrategy(locations)
        printWorkers(workers)
    }
}
