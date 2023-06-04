import javafx.application.Platform;
import javafx.collections.ObservableList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * The PresentationModel class handles the presentation logic and data management for the Movie application.
 * It communicates with the network to fetch data from the server and updates the data model accordingly.
 */
public class PresentationModel {
    private ObservableList<Movie> data;
    private NetworkCommunication networkCommunication;

    /**
     * Constructs a PresentationModel object with the specified data.
     *
     * @param data The ObservableList of Movie objects to store the fetched data.
     */
    public PresentationModel(ObservableList<Movie> data) {
        this.data = data;
        this.networkCommunication = new NetworkCommunication();
    }

    /**
     * Loads the movie data from the server asynchronously and updates the data model.
     * This method should be called to fetch and update the movie data.
     */
    public void loadDataFromServer() {
        networkCommunication.fetchDataFromServer(jsonData -> {
            Platform.runLater(() -> {
                parseJSONData(jsonData);
            });
        });
    }

    /**
     * Parses the JSON data received from the server and updates the data model.
     *
     * @param jsonData The JSON data to parse and update the data model.
     */
    private void parseJSONData(String jsonData) {
        data.clear();
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonMovie = jsonArray.getJSONObject(i);
                String title = jsonMovie.getString("title");
                JSONArray directorsArray = jsonMovie.getJSONArray("directors");
                StringBuilder directorsBuilder = new StringBuilder();
                for (int j = 0; j < directorsArray.length(); j++) {
                    JSONObject directorObject = directorsArray.getJSONObject(j);
                    String directorName = directorObject.getString("name");
                    directorsBuilder.append(directorName);
                    if (j < directorsArray.length() - 1) {
                        directorsBuilder.append(", ");
                    }
                }
                String directors = directorsBuilder.toString();
                JSONArray castArray = jsonMovie.getJSONArray("cast");
                StringBuilder castBuilder = new StringBuilder();
                for (int j = 0; j < castArray.length(); j++) {
                    JSONObject castObject = castArray.getJSONObject(j);
                    String actorName = castObject.getString("name");
                    castBuilder.append(actorName);
                    if (j < castArray.length() - 1) {
                        castBuilder.append(", ");
                    }
                }
                String cast = castBuilder.toString();
                String releaseDate = jsonMovie.getString("release_date");
                int year = getYearFromDate(releaseDate);
                String homepage = jsonMovie.optString("homepage", "");
                Double averageView = jsonMovie.optDouble("vote_average", 0.0);
                Movie movie = new Movie(title, directors, cast, year, homepage, averageView);
                data.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the year from the given date string in the format "yyyy-MM-dd".
     *
     * @param date The date string to extract the year from.
     * @return The year value extracted from the date string.
     */
    private int getYearFromDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date parsedDate = dateFormat.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parsedDate);
            return calendar.get(Calendar.YEAR);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
