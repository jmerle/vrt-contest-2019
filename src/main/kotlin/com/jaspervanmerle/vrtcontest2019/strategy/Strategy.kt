package com.jaspervanmerle.vrtcontest2019.strategy

import com.jaspervanmerle.vrtcontest2019.model.Location
import com.jaspervanmerle.vrtcontest2019.model.Worker
import com.jaspervanmerle.vrtcontest2019.model.action.ArriveAction
import com.jaspervanmerle.vrtcontest2019.model.action.WorkAction

class Strategy(private val base: Location, private val jobs: List<Location>) {
    private val workers = mutableListOf<Worker>()

    fun execute(): List<Worker> {
        for (job in jobs) {
            job.distanceToBase = job.distanceTo(base)
        }

        val availableJobs = jobs.toMutableList()

        while (true) {
            if (availableJobs.isEmpty()) {
                break
            }

            var bestJob: Location? = null
            var bestStartTime = Int.MAX_VALUE

            for (job in availableJobs) {
                if (job.startTime < bestStartTime) {
                    job.updateCurrentData(workers)

                    if (job.currentStartTime < bestStartTime) {
                        bestJob = job
                        bestStartTime = job.currentStartTime
                    }
                }
            }

            val currentJob = bestJob!!
            availableJobs.remove(currentJob)

            val startTime = currentJob.currentStartTime
            val endTime = currentJob.currentEndTime

            for (worker in currentJob.currentExistingWorkers) {
                worker.addAction(ArriveAction(currentJob, startTime))
                worker.addAction(WorkAction(currentJob, startTime, endTime))
                worker.workingUntil = endTime
            }

            for (i in 0 until currentJob.currentNewWorkers) {
                val worker = Worker(base, startTime - currentJob.distanceToBase)
                worker.addAction(ArriveAction(currentJob, startTime))
                worker.addAction(WorkAction(currentJob, startTime, endTime))
                worker.workingUntil = endTime
                workers += worker
            }
        }

        for (worker in workers) {
            val latestWorkAction = worker.actions.last() as WorkAction
            worker.addAction(ArriveAction(base, latestWorkAction.endTime + worker.distanceTo(base)))
        }

        return workers
    }
}
