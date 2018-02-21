# PopularMovies
A movie guide app, optimized for tablets, to help users discover popular and highly rated movies on the web. It displays a scrolling grid of movie trailers, launches a details screen whenever a particular movie is selected, allows users to save favorites, play trailers, and read user reviews. This app utilizes core Android user interface components and fetches movie information using themoviedb.org web API.
## Feature
* Fetch data from the Internet with theMovieDB API.
* Favorite data are stored in a native SQLite database and are exposed via a ContentProvider. This ContentProvider is updated whenever the user favorites or unfavorites a movie.
* Use custom recyclerview to auto load movie list data.
* Share and launch Youtube when click trailer.

## Screen
<img src="../master/read_me_pictures/main.gif" alt="alt text" width="348" height="611">
<img src="../master/read_me_pictures/detail.gif" alt="alt text" width="348" height="611">

## Libraries
* Glide
* Butterknife
* Gson

