package com.jaspervanmerle.vrtcontest2019.strategy

import com.jaspervanmerle.vrtcontest2019.model.Location
import com.jaspervanmerle.vrtcontest2019.model.Worker
import com.jaspervanmerle.vrtcontest2019.model.action.ArriveAction
import com.jaspervanmerle.vrtcontest2019.model.action.WorkAction

class Strategy(val locations: List<Location>) {
    val base = locations[0]

    val workers = mutableListOf<Worker>()

    fun execute(): List<Worker> {
        val jobs = locations.subList(1, locations.size).sortedBy { it.startTime }

        for (job in jobs) {
            val availableWorkers = mutableListOf<Pair<Worker, Int>>()

            for (worker in workers) {
                val bestArrivalTime = worker.getBestArrivalTime(job)

                if (bestArrivalTime <= job.latestPossibleArrival) {
                    availableWorkers += worker to bestArrivalTime
                }
            }

            val existingWorkers = availableWorkers.sortedBy { it.second }.take(job.requiredWorkers)
            val requiredNewWorkers = job.requiredWorkers - existingWorkers.size

            val startTime = getJobStartTime(job, existingWorkers)

            for (worker in existingWorkers) {
                worker.first.addAction(ArriveAction(job, startTime))
                worker.first.addAction(WorkAction(job, startTime, startTime + job.duration))
                worker.first.workingUntil = startTime + job.duration
            }

            for (i in 0 until requiredNewWorkers) {
                val worker = Worker(base, startTime - base.distanceTo(job))
                worker.addAction(ArriveAction(job, startTime))
                worker.addAction(WorkAction(job, startTime, startTime + job.duration))
                worker.workingUntil = startTime + job.duration
                workers += worker
            }
        }

        for (worker in workers) {
            val latestWorkAction = worker.actions.last() as WorkAction
            worker.addAction(ArriveAction(base, latestWorkAction.endTime + worker.location.distanceTo(base)))
        }

        return workers
    }

    private fun getJobStartTime(job: Location, existingAvailableWorkers: List<Pair<Worker, Int>>): Int {
        val distanceFromBase = base.distanceTo(job)
        val requiredNewWorkers = job.requiredWorkers - existingAvailableWorkers.size

        var earliestStart = if (existingAvailableWorkers.isEmpty()) {
            job.startTime
        } else {
            existingAvailableWorkers.maxBy { it.second }!!.second
        }

        if (requiredNewWorkers > 0) {
            if (earliestStart < distanceFromBase) {
                earliestStart = distanceFromBase
            }
        }

        if (earliestStart < job.startTime) {
            earliestStart = job.startTime
        }

        return earliestStart
    }

    private fun isProfitable(job: Location): Boolean {
        val distance = base.distanceTo(job)

        val transportationCosts = job.requiredWorkers * distance * 2
        val workingCosts = job.requiredWorkers * job.duration
        val startingCosts = job.requiredWorkers * 240

        return transportationCosts + workingCosts + startingCosts < job.reward
    }

    private fun isPossible(job: Location): Boolean {
        val distance = base.distanceTo(job)
        return job.endTime - job.duration >= distance
    }
}
