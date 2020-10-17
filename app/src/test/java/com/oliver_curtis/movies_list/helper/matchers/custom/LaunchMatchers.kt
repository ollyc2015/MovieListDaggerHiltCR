package com.oliver_curtis.movies_list.helper.matchers.custom

import com.oliver_curtis.movies_list.data.entity.MovieDetailsApiEntity


class LaunchDbEntityListMatcher(expectedFlightNumbers: List<Int>) : AbstractLaunchListMatcher<MovieDetailsApiEntity>(expectedFlightNumbers) {
    override fun extractFlightNumber(item: MovieDetailsApiEntity): Int? = item.id
}

class LaunchApiEntityListMatcher(expectedFlightNumbers: List<Int>) : AbstractLaunchListMatcher<MovieDetailsApiEntity>(expectedFlightNumbers) {
    override fun extractFlightNumber(item: MovieDetailsApiEntity): Int? = item.id
}
