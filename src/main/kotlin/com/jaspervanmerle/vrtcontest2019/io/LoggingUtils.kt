package com.jaspervanmerle.vrtcontest2019.io

fun log() = System.err.println()

fun log(message: Any?) = System.err.println(message)
fun log(message: Boolean) = System.err.println(message)
fun log(message: Byte) = System.err.println(message)
fun log(message: Char) = System.err.println(message)
fun log(message: CharArray) = System.err.println(message)
fun log(message: Double) = System.err.println(message)
fun log(message: Float) = System.err.println(message)
fun log(message: Int) = System.err.println(message)
fun log(message: Long) = System.err.println(message)
fun log(message: Short) = System.err.println(message)

fun log(vararg messages: Any?) {
    for ((index, message) in messages.withIndex()) {
        System.err.print(message)

        if (index < messages.size - 1) {
            System.err.print(' ')
        }
    }

    System.err.println()
}
