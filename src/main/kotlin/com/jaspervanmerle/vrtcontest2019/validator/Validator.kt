package com.jaspervanmerle.vrtcontest2019.validator

import com.jaspervanmerle.vrtcontest2019.model.Location
import com.jaspervanmerle.vrtcontest2019.model.Worker

abstract class Validator {
    abstract fun validate(locations: List<Location>, workers: List<Worker>)
}
