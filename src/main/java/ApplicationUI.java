import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class ApplicationUI extends BorderPane{
    
    private ComboBox<String> dropdown;
    private TextField textField;
    private Label label1;
    private Label label2;
    private Button button;
    private TableView<Movie> tableView;
    private ObservableList<Movie> data;
    
    public ApplicationUI(){
        initializeControls();
        layoutControls();
    }

    private void layoutControls() {
        HBox topContainer = new HBox(10);
        topContainer.setPadding(new Insets(10));
        topContainer.getChildren().addAll(dropdown, textField, label1, label2, button);
        this.setTop(topContainer);
        
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.setCenter(tableView);
    }    

    private void initializeControls() {
        dropdown = new ComboBox<>();
        dropdown.setPromptText("Title");
        dropdown.setItems(FXCollections.observableArrayList("Option 1", "Option 2", "Option 3"));

        textField = new TextField();

        button = new Button("Load");

        data = FXCollections.observableArrayList();

        tableView = new TableView<Movie>(data);

        label1 = new Label("Angezeigte Datensätze: ");
        label2 = new Label();
        label2.setText(String.valueOf(data.size()));

        button.setOnAction(event -> {
            loadDataFromServer();
        });

        TableColumn<Movie, String> titleColumn = new TableColumn<Movie, String>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<Movie, String>("title"));
        tableView.getColumns().add(titleColumn);

        TableColumn<Movie, String> directorColumn = new TableColumn<Movie, String>("Director");
        directorColumn.setCellValueFactory(new PropertyValueFactory<Movie, String>("director"));
        tableView.getColumns().add(directorColumn);

        TableColumn<Movie, String> castColumn = new TableColumn<Movie, String>("Cast");
        castColumn.setCellValueFactory(new PropertyValueFactory<Movie, String>("cast"));
        tableView.getColumns().add(castColumn);

        TableColumn<Movie, Integer> yearColumn = new TableColumn<Movie, Integer>("Year");
        yearColumn.setCellValueFactory(new PropertyValueFactory<Movie, Integer>("year"));
        tableView.getColumns().add(yearColumn);

        TableColumn<Movie, String> homepageColumn = new TableColumn<Movie, String>("Homepage");
        homepageColumn.setCellValueFactory(new PropertyValueFactory<Movie, String>("homepage"));
        tableView.getColumns().add(homepageColumn);

        TableColumn<Movie, Double> averageViewColumn = new TableColumn<Movie, Double>("Average view");
        averageViewColumn.setCellValueFactory(new PropertyValueFactory<Movie, Double>("averageView"));
        tableView.getColumns().add(averageViewColumn);

        tableView.setItems(data);
    }

    private void loadDataFromServer() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://softwarelab.ch/api/public/v2/movies"))
            .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .thenApply(HttpResponse::body)
            .thenAccept(jsonData -> {
                parseJSONData(jsonData);
                label2.setText(String.valueOf(data.size())); // Update the label text after loading the data
            })
            .exceptionally(e -> {
                e.printStackTrace();
                return null;
            });
    }

    private void parseJSONData(String jsonData) {
        System.out.println(jsonData);
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
                label2.setText(String.valueOf(data.size()));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

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