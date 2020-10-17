package com.oliver_curtis.movies_list.helper

import com.oliver_curtis.movies_list.helper.matchers.KArgumentMatcher
import com.oliver_curtis.movies_list.helper.matchers.KAny
import kotlinx.coroutines.runBlocking
import org.mockito.Mockito
import org.mockito.stubbing.OngoingStubbing
import org.mockito.verification.VerificationMode


/**
 * Kotlin-friendly Mockito functions.
 */
class KMockito {

    companion object {

        /**
         * Kotlin-friendly "any" matcher. This matcher will always return a non-null reference that
         * keeps the Kotlin runtime happy.
         *
         * @param dummyInstance a dummy instance used to always return a non-null reference. This
         * instance is never going to be used in any tests so its value does not matter.
         *
         * @return a Kotlin-friendly matcher that matches any argument value and will never return
         * a non-null reference
         */
        fun <T> any(dummyInstance: T) = argThat(KAny(dummyInstance))

        /**
         * Kotlin-friendly matcher. This matcher will always return a non-null reference that
         * keeps the Kotlin runtime happy.
         *
         * @param matcher the matcher that will actually perform the desired argument matching
         *
         * @return a Kotlin-friendly matcher that matches any argument value and will never return
         * a non-null reference
         *
         * @see KArgumentMatcher
         */
        fun <T> argThat(matcher: KArgumentMatcher<T>): T {
            return Mockito.argThat(matcher) ?: matcher.dummyInstance()
        }

        /**
         * Mockito.when function for suspended method calls.
         */
        fun <T> suspendedWhen(methodCall: suspend () -> T): OngoingStubbing<T> {
            return Mockito.`when`(runBlocking { methodCall.invoke() })
        }

        /**
         * Mockito.verify function for suspended method calls.
         */
        fun <T> suspendedVerify(mock: T, method: suspend T.() -> Unit) {
            runBlocking { Mockito.verify(mock).method() }
        }

        /**
         * Mockito.verify function, with verification mode, for suspended method calls.
         */
        fun <T> suspendedVerify(mock: T, mode: VerificationMode, method: suspend T.() -> Unit) {
            runBlocking { Mockito.verify(mock, mode).method() }
        }
    }
}