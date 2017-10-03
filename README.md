# PopularMovies
Project Overview
Welcome back to Popular Movies! In this second and final stage, you’ll add additional functionality to the app you built in Stage 1.

You’ll add more information to your movie details view:

You’ll allow users to view and play trailers ( either in the youtube app or a web browser).
You’ll allow users to read reviews of a selected movie.
You’ll also allow users to mark a movie as a favorite in the details view by tapping a button(star).
You'll create a database and content provider to store the names and ids of the user's favorite movies (and optionally, the rest of the information needed to display their favorites collection while offline).
You’ll modify the existing sorting criteria for the main view to include an additional pivot to show their favorites collection.
Recall from Stage 1, you built a UI that presented the user with a grid of movie posters, allowed users to change sort order, and presented a screen with additional information on the movie selected by the user:

Screen showing details from a individual film
What Will I Learn After Stage 2?
You will build a fully featured application that looks and feels natural on the latest Android operating system (Nougat, as of November 2016).
Ready to start building? Click "Next" for instructions!

MEETS SPECIFICATIONS
App is written solely in the Java Programming Language.

App conforms to common standards found in the Android Nanodegree General Project Guidelines.

User Interface - Layout

MEETS SPECIFICATIONS
UI contains an element (e.g., a spinner or settings menu) to toggle the sort order of the movies by: most popular, highest rated.

Movies are displayed in the main layout via a grid of their corresponding movie poster thumbnails.

UI contains a screen for displaying the details for a selected movie.

Movie Details layout contains title, release date, movie poster, vote average, and plot synopsis.

Movie Details layout contains a section for displaying trailer videos and user reviews.

User Interface - Function

MEETS SPECIFICATIONS
When a user changes the sort criteria (most popular, highest rated, and favorites) the main view gets updated correctly.

When a movie poster thumbnail is selected, the movie details screen is launched.

When a trailer is selected, app uses an Intent to launch the trailer.

In the movies detail screen, a user can tap a button(for example, a star) to mark it as a Favorite.

Network API Implementation

MEETS SPECIFICATIONS
In a background thread, app queries the /movie/popular or /movie/top_rated API for the sort criteria specified in the settings menu.

App requests for related videos for a selected movie via the /movie/{id}/videos endpoint in a background thread and displays those details when the user selects a movie.

App requests for user reviews for a selected movie via the /movie/{id}/reviews endpoint in a background thread and displays those details when the user selects a movie.

Data Persistence

MEETS SPECIFICATIONS
The titles and ids of the user's favorite movies are stored in a ContentProvider backed by a SQLite database. This ContentProvider is updated whenever the user favorites or unfavorites a movie.

When the "favorites" setting option is selected, the main view displays the entire favorites collection based on movie ids stored in the ContentProvider.

Suggestions to Make Your Project Stand Out!
Extend the favorites ContentProvider to store the movie poster, synopsis, user rating, and release date, and display them even when offline.
Implement sharing functionality to allow the user to share the first trailer’s YouTube URL from the movie details screen.
