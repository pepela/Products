package com.peranidze.products.factory

import java.util.concurrent.ThreadLocalRandom

class DataFactory {

    companion object Factory {

        fun randomUuid(): String = java.util.UUID.randomUUID().toString()

        fun randomInt(): Int = ThreadLocalRandom.current().nextInt(0, 1000 + 1)

        fun randomDouble(): Double = ThreadLocalRandom.current().nextDouble(0.0, 1000 + 1.0)

        fun randomLong(): Long = randomInt().toLong()
    }

}
