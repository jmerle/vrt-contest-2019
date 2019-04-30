package com.jaspervanmerle.vrtcontest2019.validator

import com.jaspervanmerle.vrtcontest2019.model.Location
import com.jaspervanmerle.vrtcontest2019.model.Worker
import com.jaspervanmerle.vrtcontest2019.model.action.WorkAction

class StartWorkAtSameTimeValidator : Validator() {
    override fun validate(locations: List<Location>, workers: List<Worker>) {
        val startTimes: MutableMap<Location, Int> = mutableMapOf()

        for (worker in workers) {
            for (action in worker.actions) {
                if (action is WorkAction) {
                    if (startTimes.containsKey(action.location)) {
                        if (startTimes[action.location] != action.startTime) {
                            throw Error("Work should be done simultaneously, all workers need to start at the same time (attempted to start at ${action.startTime}, while others start at ${startTimes[action.location]}")
                        }
                    } else {
                        startTimes[action.location] = action.startTime
                    }
                }
            }
        }
    }
}
