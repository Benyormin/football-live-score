# Football Live Scores App

This is an Android application that fetches and displays live football match scores using the [Football Data API](https://www.football-data.org/).

## Features

- Displays live football matches, including team names, scores, and match status.
- Supports refreshing match data in real-time.
- Simple and user-friendly interface.

## How It Works

- Fetches match data from the Football Data API using Volley.
- Displays the data in a `RecyclerView` with details such as:
  - Home and away team names.
  - Match status (e.g., "IN_PLAY", "FINISHED").
  - Full-time scores for completed or in-progress matches.

## Setup Instructions

1. Clone this repository:
   ```bash
   git clone <repository-url>
   ```
2. Open the project in Android Studio.
3. Replace the `X-Auth-Token` in the `MainActivity` file with your own API key from [Football Data](https://www.football-data.org/client/register).
4. Run the app on your emulator or physical device.

## Dependencies

The app uses the following dependencies:
- [Volley](https://developer.android.com/training/volley) for network requests.
- RecyclerView for displaying the list of matches.

## Screenshots



## License

This project is licensed under the MIT License. See the LICENSE file for details.

