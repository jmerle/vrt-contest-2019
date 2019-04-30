package com.jaspervanmerle.vrtcontest2019.strategy

import com.jaspervanmerle.vrtcontest2019.model.Location
import com.jaspervanmerle.vrtcontest2019.model.Worker
import com.jaspervanmerle.vrtcontest2019.model.action.ArriveAction
import com.jaspervanmerle.vrtcontest2019.model.action.WorkAction

class Strategy(val locations: List<Location>) {
    val base = locations[0]
    val jobs = locations.subList(1, locations.size)

    val workers = mutableListOf<Worker>()

    fun execute(): List<Worker> {
        for (job in jobs) {
            if (isProfitable(job) && isPossible(job)) {
                val distance = base.getDistance(job)

                for (i in 0 until job.requiredWorkers) {
                    var arrivalTime = job.startTime - distance

                    if (arrivalTime < 0) {
                        arrivalTime = 0
                    }

                    val arrivalAtJob = arrivalTime + distance
                    val workEndTime = arrivalAtJob + job.duration
                    val arrivalBackAtBase = workEndTime + distance

                    val worker = Worker(base, arrivalTime)
                    worker.actions += ArriveAction(job, arrivalAtJob)
                    worker.actions += WorkAction(job, arrivalAtJob, workEndTime)
                    worker.actions += ArriveAction(base, arrivalBackAtBase)
                    workers += worker
                }
            }
        }

        return workers
    }

    private fun isProfitable(job: Location): Boolean {
        val distance = base.getDistance(job)

        val transportationCosts = job.requiredWorkers * distance * 2
        val workingCosts = job.requiredWorkers * job.duration
        val startingCosts = job.requiredWorkers * 240

        return transportationCosts + workingCosts + startingCosts < job.reward
    }

    private fun isPossible(job: Location): Boolean {
        val distance = base.getDistance(job)
        return job.endTime - job.duration >= distance
    }
}
