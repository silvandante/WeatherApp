# Weather App

Demo: https://youtu.be/Q5sv4wcKFuc
<img src="https://raw.githubusercontent.com/silvandante/WeatherApp/master/print1.jpg)" width="200">
<img src="https://raw.githubusercontent.com/silvandante/WeatherApp/master/print2.jpg)" width="200">
<img src="https://raw.githubusercontent.com/silvandante/WeatherApp/master/print3.jpg)" width="200">

App Description:
- App to see weather around the world with 10 previous countries in europe and current location, but you can also search for new places and get to know their weather, current time and some other details!

The application is composed by:
- Splash screen page
- Result weather list page
- Search bar
- Detail weather page


Archicture MVVM:
![Architecture MVVM](https://developer.android.com/static/codelabs/basic-android-kotlin-training-repository-pattern/img/69021c8142d29198.png)


API https://api.openweathermap.org/:

    - Endpoint data/2.5/group:
        > Catch list of weathers by city ids

- Endpoint data/2.5/weather:
  > Catch single weather by city name
  > Or catch single weather by latitude longitude

Tech:
- Android Jetpack;
- Android Architecture Components;
- Material Design Components;
- Kotlin and Coroutines;
- Clean Architecture;
- Room database;
- Retrofit handle response with sealed class;
- RecyclerView and Filter;
- Unit testing;
