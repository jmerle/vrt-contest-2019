package com.jaspervanmerle.vrtcontest2019.runner

import com.jaspervanmerle.vrtcontest2019.io.InputReader
import com.jaspervanmerle.vrtcontest2019.model.Location
import com.jaspervanmerle.vrtcontest2019.model.Worker
import com.jaspervanmerle.vrtcontest2019.model.action.ArriveAction
import com.jaspervanmerle.vrtcontest2019.model.action.StartAction
import com.jaspervanmerle.vrtcontest2019.model.action.WorkAction
import com.jaspervanmerle.vrtcontest2019.strategy.Strategy
import com.jaspervanmerle.vrtcontest2019.validator.*
import java.io.File
import java.text.NumberFormat
import java.util.*

class DevelopmentRunner(args: Array<String>) : Runner(args) {
    private val validators: List<Validator> = listOf(
        CorrectActionsValidator(),
        CorrectStartEndLocationValidator(),
        CorrectWorkLocationValidator(),
        EnoughWorkersValidator(),
        StartWorkAtSameTimeValidator(),
        WorkCorrectTimeValidator(),
        CorrectDistancesValidator()
    )

    override fun run() {
        val numberFormat = NumberFormat.getNumberInstance(Locale.US)

        for (testName in args) {
            val ir = InputReader(File("src/main/resources/tests/$testName.txt"))
            val locations = readLocations(ir)
            val workers = Strategy(locations).execute()

            for (validator in validators) {
                validator.validate(locations, workers)
            }

            if (args.size == 1) {
                printWorkers(workers)
                println()
            }

            val score = getScore(locations, workers)
            val jobs = workers.flatMap { it.actions }.filter { it is WorkAction }.map { it.location }.distinct()

            println("Score for test $testName (${jobs.size} jobs, ${workers.size} workers): ${numberFormat.format(score)}")
        }
    }

    private fun getScore(locations: List<Location>, workers: List<Worker>): Double {
        val completedLocations: MutableSet<Location> = mutableSetOf()

        var rewardedCredits = 0
        var workerCost = 0

        for (worker in workers) {
            val startTime = (worker.actions[0] as StartAction).time
            val endTime = (worker.actions.last() as ArriveAction).time

            workerCost += 240 + (endTime - startTime)

            for (action in worker.actions) {
                if (action is WorkAction) {
                    completedLocations += action.location
                }
            }
        }

        for (location in completedLocations) {
            rewardedCredits += location.reward
        }

        return (rewardedCredits - workerCost).toDouble() / 1000.0
    }
}
