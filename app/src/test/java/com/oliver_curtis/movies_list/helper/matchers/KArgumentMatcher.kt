package com.oliver_curtis.movies_list.helper.matchers

import org.mockito.ArgumentMatcher

/**
 * Cannot use Mockito argument matchers (e.g. Mockito.any(), Mockito.eq(value) or Mockito.argThat(matcher))
 * with Kotlin because they can return null at some points during the testing process. Whereas Java
 * is OK with such nulls, Kotlin complains if the associated reference is defined as non-null. This
 * Kotlin-friendly argument matcher defines a method which will return a dummy instance to satisfy
 * the Kotlin runtime when the accepting reference is defined as non-null.
 */
abstract class KArgumentMatcher<T> : ArgumentMatcher<T> {

    /**
     * Returns a non-null dummy instance used to keep Kotlin happy when method arguments (used to
     * define mock behaviour) are defined as non-null. It does not matter what value is returned
     * by this method since such value will never be used in any tests. It will be used only to keep
     * the Kotlin runtime happy.
     */
    abstract fun dummyInstance(): T
}