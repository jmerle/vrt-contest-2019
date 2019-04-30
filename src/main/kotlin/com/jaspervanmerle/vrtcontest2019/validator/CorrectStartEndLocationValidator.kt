package com.jaspervanmerle.vrtcontest2019.validator

import com.jaspervanmerle.vrtcontest2019.model.Location
import com.jaspervanmerle.vrtcontest2019.model.Worker

class CorrectStartEndLocationValidator : Validator() {
    override fun validate(locations: List<Location>, workers: List<Worker>) {
        for (worker in workers) {
            val startLocation = worker.actions[0].location.id
            val endLocation = worker.actions.last().location.id

            if (startLocation != 1) {
                throw Error("Workers should start on the base (started on $startLocation)")
            }

            if (startLocation != endLocation) {
                throw Error("Workers should end in the location that they start in (started at $startLocation, ended at $endLocation)")
            }
        }
    }
}
