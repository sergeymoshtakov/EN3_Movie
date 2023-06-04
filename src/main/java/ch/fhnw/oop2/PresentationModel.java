package ch.fhnw.oop2;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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
    private ComboBox<String> comboBox;
    private TextField textField;
    private Label label2;
    private TableView<Movie> tableView;
    private StringProperty label2Text = new SimpleStringProperty();

    /**
     * Constructs a PresentationModel object with the specified data and tableView.
     *
     * @param data       The ObservableList of Movie objects to store the fetched data.
     * @param tableView  The TableView to display the movie data.
     */
    public PresentationModel(ObservableList<Movie> data, ComboBox<String> comboBox, TextField textField, StringProperty label2Text) {
        this.data = data;
        this.networkCommunication = new NetworkCommunication();
        this.tableView = tableView;
        this.label2Text = label2Text;
    }

    /**
     * Loads the movie data from the server asynchronously and updates the data model.
     * This method should be called to fetch and update the movie data.
     */
    public void loadDataFromServer() {
        networkCommunication.fetchDataFromServer(jsonData -> {
            Platform.runLater(() -> {
                parseJSONData(jsonData);
                updateResultLabel();
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
                String directors = "";
                if (directorsArray.length() > 0) {
                    JSONObject firstDirector = directorsArray.getJSONObject(0);
                    directors = firstDirector.getString("name");
                }
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

    /**
    * Handles the selection event of the ComboBox.
    * Clears the text field and performs a search operation.
    */
    public void handleComboBoxSelection() {
        textField.clear();
        handleSearch();
    }

    /**
    * Performs a search operation based on the selected option and the entered search text.
    * Updates the filtered state of the movie objects in the data model.
    * Updates the result label and refreshes the table view.
    */
    public void handleSearch() {
        String selectedOption = comboBox.getSelectionModel().getSelectedItem();
        String searchText = textField.getText().toLowerCase();

        if (selectedOption == null || searchText.isEmpty()) {
            data.forEach(movie -> movie.setFiltered(true));
            updateResultLabel();
            return;
        }

        data.forEach(movie -> {
            boolean match = false;

            if (selectedOption.equals("Title")) {
                match = movie.getTitle().toLowerCase().contains(searchText);
            } else if (selectedOption.equals("Director")) {
                match = movie.getDirector().toLowerCase().contains(searchText);
            } else if (selectedOption.equals("Cast")) {
                match = movie.getCast().toLowerCase().contains(searchText);
            } else if (selectedOption.equals("Year")) {
                try {
                    int year = Integer.parseInt(searchText);
                    int movieYear = movie.getYear();
                    match = movieYear >= year && movieYear <= (year + 9);
                } catch (NumberFormatException e) {
                }
            }

            movie.setFiltered(!match);
        });

        updateResultLabel();
        tableView.setItems(data);
    }
    /**
    * Updates the result label with the count of visible movies.
    * Sets the label2Text property with the updated count.
    */
    public void updateResultLabel() {
        int visibleCount = (int) data.stream().filter(Movie::isFiltered).count();
        label2Text.set(String.valueOf(visibleCount));
    }
    
    /**
    * Sets the ComboBox control for selecting search options.
    *
    * @param comboBox The ComboBox control to set.
    */
    public void setComboBox(ComboBox<String> comboBox) {
        this.comboBox = comboBox;
    }
    /**
    * Sets the TextField control for entering search text.
    *
    * @param textField The TextField control to set.
    */
    public void setTextField(TextField textField) {
        this.textField = textField;
    }
    /**
    * Sets the Label control for displaying the result count.
    *
    * @param label2 The Label control to set.
    */
    public void setLabel2(Label label2) {
        this.label2 = label2;
    }
    /**
    * Sets the TableView control for displaying the movie data.
    *
    * @param tableView The TableView control to set.
    */
    public void setTableView(TableView<Movie> tableView) {
        this.tableView = tableView;
    }
    /**
    * Sets the StringProperty for updating the result count label text.
    *
    * @param label2Text The StringProperty to set.
    */
    public void setLabel2Text(StringProperty label2Text) {
        this.label2Text = label2Text;
    }
    
}