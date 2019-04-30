package com.jaspervanmerle.vrtcontest2019.validator

import com.jaspervanmerle.vrtcontest2019.model.Location
import com.jaspervanmerle.vrtcontest2019.model.Worker
import com.jaspervanmerle.vrtcontest2019.model.action.WorkAction

class EnoughWorkersValidator : Validator() {
    override fun validate(locations: List<Location>, workers: List<Worker>) {
        val workerCount: MutableMap<Location, Int> = mutableMapOf()

        for (worker in workers) {
            for (action in worker.actions) {
                if (action is WorkAction) {
                    workerCount[action.location] = workerCount.getOrDefault(action.location, 0) + 1
                }
            }
        }

        for ((location, count) in workerCount) {
            if (count != location.requiredWorkers) {
                throw Error("$count workers start working at ${location.id} while it requires ${location.requiredWorkers} workers")
            }
        }
    }
}
