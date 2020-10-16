## Movie DB List

Simple Android application that displays Movie Data using the [The Movie Database API](https://developers.themoviedb.org/3/getting-started/introduction).

## Useful Info
This is exactly the same project as [my other Movie List](https://github.com/ollyc2015/MovieList), however, this project uses Kotlin Coroutines in replace of RXJava and Dagger Hilt in replace of Koin.

### Description

The application consists of a single screen. The screen displays the various movies in a list. For each movie in the list the following parts are visible:

- Movie image
- Movie name
- Voting average
- Release date

### Design

The design adopted follows the MVVM with clean architecture design patterns. It is too complex for the simple functionality required, but it was chosen to illustrate
the design patterns and best practices followed in larger scale and more demanding applications.
  
### Configuration

There are 2 external configuration parameters contained in the top-level `build.gradle` file:

1. `posterBaseUrl`: The base URL needed to request the movie poster (http://image.tmdb.org/t/p/w185)
2. `apiBaseUrl`: The base URL of the [The Movie Database API](https://api.themoviedb.org/) endpoints (https://api.themoviedb.org/).

There is 1 external configuration parameter contained in the `local.properties` file, that will need to be configured by the user:

1. `API_KEY = yourapikey` : The api key needed to interact with The Movie Database.

### Issues

1. The layout of the items in the list can be improved to function better with different screen sizes
2. A local DB should be added to avoid constant calls to the API

More manual and unit testing can always be added as well as automation tests.


### Libraries

The [Retrofit2](https://square.github.io/retrofit/) library was used to call the [The Movie Database API](https://developers.themoviedb.org/3/getting-started/introduction) endpoints whereas [Kotlin Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) was employed to orchestrate the remote server calls. The 
[Glide](https://github.com/bumptech/glide) library helped with image display. For dependency injection, the [Dagger Hilt](https://developer.android.com/training/dependency-injection/hilt-android) framework was used. To aid unit testing, the [Mockito](https://site.mockito.org/) mocking framework and [junit](https://junit.org/junit4/) annotations were used to setup and aid with configuration of the tests.

### Contact

Please e-mail <olly_915@hotmail.com> for any questions or requests.
