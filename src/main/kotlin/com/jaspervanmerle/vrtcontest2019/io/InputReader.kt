package com.jaspervanmerle.vrtcontest2019.io

import java.io.*
import java.util.*

class InputReader(reader: Reader) {
    constructor(inputStream: InputStream) : this(InputStreamReader(inputStream))
    constructor(file: File) : this(FileReader(file))

    private val br = BufferedReader(reader)
    private var st: StringTokenizer? = null

    fun next(): String {
        while (st?.hasMoreTokens() != true) {
            st = StringTokenizer(br.readLine())
        }

        return st!!.nextToken()
    }

    fun nextInt() = next().toInt()
    fun nextLong() = next().toLong()
    fun nextFloat() = next().toFloat()
    fun nextDouble() = next().toDouble()
    fun nextLine(): String = br.readLine()
}
