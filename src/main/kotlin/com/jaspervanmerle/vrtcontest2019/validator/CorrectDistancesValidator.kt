package com.jaspervanmerle.vrtcontest2019.validator

import com.jaspervanmerle.vrtcontest2019.model.Location
import com.jaspervanmerle.vrtcontest2019.model.Worker
import com.jaspervanmerle.vrtcontest2019.model.action.ArriveAction
import com.jaspervanmerle.vrtcontest2019.model.action.StartAction
import com.jaspervanmerle.vrtcontest2019.model.action.WorkAction

class CorrectDistancesValidator : Validator() {
    override fun validate(locations: List<Location>, workers: List<Worker>) {
        for (worker in workers) {
            for ((actionA, actionB) in worker.actions.zipWithNext()) {
                if (actionA.location != actionB.location) {
                    val timeA = when (actionA) {
                        is StartAction -> actionA.time
                        is ArriveAction -> actionA.time
                        is WorkAction -> actionA.endTime
                        else -> 0
                    }

                    val timeB = when (actionB) {
                        is StartAction -> actionB.time
                        is ArriveAction -> actionB.time
                        is WorkAction -> actionB.startTime
                        else -> 0
                    }

                    val distance = actionA.location.distanceTo(actionB.location)
                    val timeDifference = Math.abs(timeA - timeB)

                    if (distance > timeDifference) {
                        throw Error("There's not enough time to travel between location ${actionA.location.id} and location ${actionB.location.id} (distance is ${distance}, time difference between actions is $timeDifference)")
                    }
                }
            }
        }
    }
}
