package com.jaspervanmerle.vrtcontest2019.io

import java.io.OutputStream

class TeeOutputStream(private val outA: OutputStream, private val outB: OutputStream) : OutputStream() {
    override fun write(b: Int) {
        outA.write(b)
        outB.write(b)
    }

    override fun write(b: ByteArray?) {
        outA.write(b)
        outB.write(b)
    }

    override fun write(b: ByteArray?, off: Int, len: Int) {
        outA.write(b, off, len)
        outB.write(b, off, len)
    }

    override fun flush() {
        outA.flush()
        outB.flush()
    }

    override fun close() {
        outA.close()
        outB.close()
    }
}
