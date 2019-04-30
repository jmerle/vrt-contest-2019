package com.jaspervanmerle.vrtcontest2019.validator

import com.jaspervanmerle.vrtcontest2019.model.Location
import com.jaspervanmerle.vrtcontest2019.model.Worker
import com.jaspervanmerle.vrtcontest2019.model.action.WorkAction

class WorkCorrectTimeValidator : Validator() {
    override fun validate(locations: List<Location>, workers: List<Worker>) {
        for (worker in workers) {
            for (action in worker.actions) {
                if (action is WorkAction) {
                    val actionStart = action.startTime
                    val actionEnd = action.endTime
                    val actionDuration = actionEnd - actionStart

                    val locationStart = action.location.startTime
                    val locationEnd = action.location.endTime
                    val locationDuration = action.location.duration

                    val locationId = action.location.id

                    if (actionEnd < actionStart) {
                        throw Error("Workers should end worker at a time greater than their start time (start time is $actionStart, end time is $actionEnd)")
                    }

                    if (actionStart < locationStart) {
                        throw Error("Cannot start working on location $locationId before $locationStart, attempted to start at $actionStart")
                    }

                    if (actionEnd > locationEnd) {
                        throw Error("Cannot be working on location $locationId after the end time (tried to work until $actionEnd, the location ends at $locationEnd)")
                    }

                    if (actionDuration < locationDuration) {
                        throw Error("Location $locationId has a duration of $locationDuration, worker only works for $actionDuration")
                    }
                }
            }
        }
    }
}
