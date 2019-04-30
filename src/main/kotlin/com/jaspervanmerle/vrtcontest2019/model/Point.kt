package com.jaspervanmerle.vrtcontest2019.model

open class Point(var x: Int, var y: Int) {
    constructor(point: Point) : this(point.x, point.y)

    fun distanceTo(other: Point): Int {
        return Math.abs(x - other.x) + Math.abs(y - other.y)
    }
}
