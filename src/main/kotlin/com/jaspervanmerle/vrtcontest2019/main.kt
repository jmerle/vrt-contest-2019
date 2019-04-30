package com.jaspervanmerle.vrtcontest2019

import com.jaspervanmerle.vrtcontest2019.runner.DevelopmentRunner
import com.jaspervanmerle.vrtcontest2019.runner.ProductionRunner

fun main(args: Array<String>) {
    if (System.getProperty("ONLINE_JUDGE") != null) {
        ProductionRunner(args).run()
    } else {
        DevelopmentRunner(args).run()
    }
}
