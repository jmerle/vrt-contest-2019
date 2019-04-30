package com.jaspervanmerle.vrtcontest2019.validator

import com.jaspervanmerle.vrtcontest2019.model.Location
import com.jaspervanmerle.vrtcontest2019.model.Worker
import com.jaspervanmerle.vrtcontest2019.model.action.WorkAction

class CorrectWorkLocationValidator : Validator() {
    override fun validate(locations: List<Location>, workers: List<Worker>) {
        for (worker in workers) {
            for ((actionA, actionB) in worker.actions.zipWithNext()) {
                if (actionB is WorkAction) {
                    val locationA = actionA.location.id
                    val locationB = actionB.location.id

                    if (locationA != locationB) {
                        throw Error("Can't start working at location $locationB while being at location $locationA")
                    }
                }
            }
        }
    }
}
