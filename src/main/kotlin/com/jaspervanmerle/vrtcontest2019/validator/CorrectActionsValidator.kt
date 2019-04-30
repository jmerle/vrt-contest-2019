package com.jaspervanmerle.vrtcontest2019.validator

import com.jaspervanmerle.vrtcontest2019.model.Location
import com.jaspervanmerle.vrtcontest2019.model.Worker
import com.jaspervanmerle.vrtcontest2019.model.action.ArriveAction
import com.jaspervanmerle.vrtcontest2019.model.action.StartAction
import com.jaspervanmerle.vrtcontest2019.model.action.WorkAction

class CorrectActionsValidator : Validator() {
    override fun validate(locations: List<Location>, workers: List<Worker>) {
        for (worker in workers) {
            if (worker.actions[0] !is StartAction) {
                throw Error("All workers need to have a start action as first action")
            }

            if (worker.actions.last() !is ArriveAction) {
                throw Error("All workers need to have an arrive action as last action")
            }

            if (worker.actions.any { it is WorkAction }) {
                throw Error("All workers need at least one work action")
            }

            if (worker.actions.count { it is StartAction } > 1) {
                throw Error("Workers can only have one start action")
            }
        }
    }
}
