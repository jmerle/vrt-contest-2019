package com.jaspervanmerle.vrtcontest2019.strategy

import com.jaspervanmerle.vrtcontest2019.model.Location
import com.jaspervanmerle.vrtcontest2019.model.Worker
import com.jaspervanmerle.vrtcontest2019.model.action.ArriveAction
import com.jaspervanmerle.vrtcontest2019.model.action.WorkAction

class Strategy(val base: Location, val jobs: List<Location>) {
    val workers = mutableListOf<Worker>()

    fun execute(): List<Worker> {
        val sortedJobs = jobs.sortedBy { it.endTime }

        for (job in sortedJobs) {
            val availableWorkers = mutableListOf<Pair<Worker, Int>>()

            for (worker in workers) {
                val bestArrivalTime = worker.getBestArrivalTime(job)

                if (bestArrivalTime <= job.latestPossibleArrival) {
                    availableWorkers += worker to bestArrivalTime
                }
            }

            val existingWorkers = availableWorkers.sortedBy { it.second }.take(job.requiredWorkers)
            val newWorkers = job.requiredWorkers - existingWorkers.size

            val startTime = getJobStartTime(job, existingWorkers)

            for (worker in existingWorkers) {
                worker.first.addAction(ArriveAction(job, startTime))
                worker.first.addAction(WorkAction(job, startTime, startTime + job.duration))
                worker.first.workingUntil = startTime + job.duration
            }

            for (i in 0 until newWorkers) {
                val worker = Worker(base, startTime - base.distanceTo(job))
                worker.addAction(ArriveAction(job, startTime))
                worker.addAction(WorkAction(job, startTime, startTime + job.duration))
                worker.workingUntil = startTime + job.duration
                workers += worker
            }
        }

        for (worker in workers) {
            val latestWorkAction = worker.actions.last() as WorkAction
            worker.addAction(ArriveAction(base, latestWorkAction.endTime + worker.distanceTo(base)))
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
}
