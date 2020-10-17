package com.oliver_curtis.movies_list.helper

import org.junit.Assert

fun <T> assertSame(expected: List<T>, actual: List<T>, itemComparator: (T, T) -> Boolean) {
    Assert.assertEquals(expected.size, actual.size)

    for (i in 0 until expected.size) {
        Assert.assertTrue(itemComparator.invoke(expected[i], actual[i]))
    }
}