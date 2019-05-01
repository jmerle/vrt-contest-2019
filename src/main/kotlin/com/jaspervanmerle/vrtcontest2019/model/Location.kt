package com.jaspervanmerle.vrtcontest2019.model

class Location(
    val id: Int,
    x: Int,
    y: Int,
    val duration: Int,
    val requiredWorkers: Int,
    val startTime: Int,
    val endTime: Int
) : Point(x, y) {
    val reward = duration * requiredWorkers * (requiredWorkers + 5)
    val latestPossibleArrival = endTime - duration

    var distanceToBase = 0

    var currentStartTime = 0
    var currentEndTime = 0
    var currentNewWorkers = 0
    var currentExistingWorkers = listOf<Worker>()
    val currentAvailableWorkers = mutableListOf<Pair<Worker, Int>>()

    fun updateCurrentData(workers: List<Worker>) {
        currentAvailableWorkers.clear()

        var currentWorst = 0

        for (worker in workers) {
            val bestArrivalTime = worker.getBestArrivalTime(this)

            if (bestArrivalTime <= latestPossibleArrival && (currentWorst == 0 || currentAvailableWorkers.size < requiredWorkers || bestArrivalTime < currentWorst)) {
                if (currentAvailableWorkers.size < requiredWorkers) {
                    currentAvailableWorkers += worker to bestArrivalTime

                    if (currentWorst == 0 || bestArrivalTime > currentWorst) {
                        currentWorst = bestArrivalTime
                    }

                    if (currentAvailableWorkers.size == requiredWorkers) {
                        currentAvailableWorkers.sortBy { it.second }
                    }
                } else {
                    for (i in 0 until requiredWorkers) {
                        if (currentAvailableWorkers[i].second > bestArrivalTime) {
                            currentAvailableWorkers.add(i, worker to bestArrivalTime)
                            currentAvailableWorkers.removeAt(currentAvailableWorkers.size - 1)

                            if (currentWorst == 0 || bestArrivalTime > currentWorst) {
                                currentWorst = bestArrivalTime
                            }

                            break
                        }
                    }
                }
            }
        }

        currentExistingWorkers = currentAvailableWorkers.map { it.first }.take(requiredWorkers)
        currentNewWorkers = requiredWorkers - currentExistingWorkers.size

        currentStartTime = getJobStartTime(currentAvailableWorkers)
        currentEndTime = currentStartTime + duration
    }

    private fun getJobStartTime(existingWorkers: Collection<Pair<Worker, Int>>): Int {
        var earliestStart = if (existingWorkers.isEmpty()) {
            startTime
        } else {
            existingWorkers.maxBy { it.second }!!.second
        }

        if (currentNewWorkers > 0) {
            if (earliestStart < distanceToBase) {
                earliestStart = distanceToBase
            }
        }

        if (earliestStart < startTime) {
            earliestStart = startTime
        }

        return earliestStart
    }
}
