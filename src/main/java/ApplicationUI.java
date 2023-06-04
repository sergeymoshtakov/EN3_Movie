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

        label1 = new Label("Angezeigte Datensätze: ");
        label2 = new Label("2,000");

        button = new Button("Load");

        ObservableList<Movie> data = FXCollections.observableArrayList(
            new Movie(" ", " ", " ", 0, " ", 0),
            new Movie(" ", " ", " ", 0, " ", 0),
            new Movie(" ", " ", " ", 0, " ", 0)
        );

        tableView = new TableView<Movie>(data);

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

}