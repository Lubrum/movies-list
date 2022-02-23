# Movies list

An app to show a list of random movies from [Movies DB API](https://www.themoviedb.org/). It has basically two options:

- show a list of movies; 

- show a list of favorites movies;

To make some movie a new favorite, just click into it in the Home screen. 

Then the selected movies will show in Favorites screen.

## Usage

To run this project, you'll need an API key to use [Movies DB API](https://www.themoviedb.org/).

Create an account, get your API key and put into apikey.properties on the root folder, like the example below.

```sh
MOVIES_DB_API_KEY="MY_MOVIES_DB_API_GOES_HERE"
MOVIES_DB_API="https://api.themoviedb.org/3/"
IMAGE_DB_API="https://image.tmdb.org/t/p/w342"
```

Also, to use Firebase Sdk, you will need your own google-services.json at app module.

You can get instructions here, in [Firebase Website](https://firebase.google.com/).

## Technologies

- Room Database;
- MVVM architecture;
- View Models;
- Retrofit;
- Gson (json parser);
- Glide (image loading and caching);
- Firebase (crashlytics & analytics & perf via Boom)

## Notes

Support for Android 4.4+.

## Contributions
 
Yet to be written.
