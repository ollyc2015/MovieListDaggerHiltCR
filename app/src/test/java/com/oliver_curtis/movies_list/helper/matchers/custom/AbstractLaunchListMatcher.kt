package com.oliver_curtis.movies_list.helper.matchers.custom

import com.oliver_curtis.movies_list.helper.matchers.KArgumentMatcher

abstract class AbstractLaunchListMatcher<T>(private val expectedFlightNumbers: List<Int>) : KArgumentMatcher<List<T>>() {

    override fun dummyInstance(): List<T> = emptyList()

    override fun matches(argument: List<T>?): Boolean {
        if (argument == null) {
            return false
        }

        val actualFlightNumbers = argument.map { extractFlightNumber(it) }
        if (expectedFlightNumbers.size != actualFlightNumbers.size) {
            return false
        }

        for (i in 0 until expectedFlightNumbers.size) {
            if (expectedFlightNumbers[i] != actualFlightNumbers[i]) {
                return false
            }
        }

        return true
    }

    abstract fun extractFlightNumber(item: T): Int?
}